package com.missio.worship.missioworshipbackend.libs.users;

import com.missio.worship.missioworshipbackend.libs.authentication.MissioValidationResponse;
import com.missio.worship.missioworshipbackend.libs.authentication.errors.InvalidProvidedToken;
import com.missio.worship.missioworshipbackend.libs.authentication.errors.NotAdminException;
import com.missio.worship.missioworshipbackend.libs.common.PaginationInput;
import com.missio.worship.missioworshipbackend.libs.common.RestPaginationResponse;
import com.missio.worship.missioworshipbackend.libs.users.errors.*;
import com.missio.worship.missioworshipbackend.ports.api.common.AuthorizationChecker;
import com.missio.worship.missioworshipbackend.ports.api.users.UserCreate;
import com.missio.worship.missioworshipbackend.ports.datastore.entities.Role;
import com.missio.worship.missioworshipbackend.ports.datastore.entities.User;
import com.missio.worship.missioworshipbackend.ports.datastore.entities.UserRoles;
import com.missio.worship.missioworshipbackend.ports.datastore.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    private final RolesService rolesService;

    private final AuthorizationChecker authorizationChecker;

    public UserFullResponse getUser(final Integer id, final String token) throws UserNotFound, InvalidProvidedToken {
        authorizationChecker.doTokenVerification(token);
        val dbresponse = getUser(id);
        return userFullResponseFromUser(dbresponse);
    }

    public UserFullResponse createUser(final String name, final String email, final List<Integer> roles, final String token) throws RolNotFoundException, InvalidProvidedToken, NotAdminException, InvalidRolException, EmailAlreadyRegisteredException {
        doAdminAuthorization(token);
        val validRoles = rolesService.validateListOfRoles(roles);
        if(emailExists(email)) throw new EmailAlreadyRegisteredException(email);

        var user = new User();
        user.setEmail(email);
        user.setName(name);
        val saved = userRepository.save(user);

        val userRoles = validRoles.stream()
                .map(rolId -> new UserRoles(saved, rolId))
                .toList();
        log.info("Se van a crear las siguientes relaciones rol-usuario: '{}'", userRoles);
        rolesService.putUserRoles(userRoles);

        return userFullResponseFromUser(saved);
    }

    public void deleteUser(final Integer id, final String token) throws InvalidProvidedToken, NotAdminException, UserNotFound, CannotDeleteUserException, EmptyResultDataAccessException {
        val decodedToken = doAdminAuthorization(token);
        val user = userRepository.findById(id).orElseThrow(() -> new UserNotFound(id));
        if(!authorizationChecker.userIsAdminOrHimself(user, decodedToken)) {
            throw new CannotDeleteUserException("El usuario a borrar es administrador. Sólo puede borrarse él mismo.");
        }
        userRepository.deleteById(id);
    }

    public UserFullResponse updateUser(final Integer id, final UserCreate userCreate, final String token) throws InvalidProvidedToken, NotAdminException, UserNotFound, RolNotFoundException, CannotDeleteUserException {
        val decodedToken = doAdminAuthorization(token);
        var user = userRepository.findById(id).orElseThrow(() -> new UserNotFound(id));
        if(!authorizationChecker.userIsAdminOrHimself(user, decodedToken)) {
            throw new CannotDeleteUserException("El usuario a borrar es administrador. Sólo puede borrarse él mismo.");
        }
        if(userCreate.name() != null) user.setName(userCreate.name());
        if(userCreate.email() != null) user.setEmail(userCreate.email());
        if(userCreate.roles() != null) {
            rolesService.setRolesForUser(userCreate.roles(), user);
        }
        val response = userRepository.save(user);
        return userFullResponseFromUser(response);
    }

    public RestPaginationResponse<UserFullResponse> getUserList(final PaginationInput input, String bearerToken)
            throws InvalidProvidedToken {
        log.info("Intentando obtener paginación de usuarios con los datos: {}", input);
        authorizationChecker.doTokenVerification(bearerToken);
        val values = userRepository.findAllByPagination(input.getLimit(), input.getOffset())
                .stream()
                .map(this::userFullResponseFromUser).toList();
        val totalValues = userRepository.count();
        log.info("Encontrados {} valores", values.size());
        RestPaginationResponse<UserFullResponse> response = new RestPaginationResponse<>();
        response.setValues(values);
        response.setLimit(input.getLimit());
        response.setTotalCount(totalValues);
        response.setOffset(input.getOffset());
        response.setNextOffset(input.getNextOffset());
        return response;
    }

    public void validateForUserCreation(final UserCreate userCreate) throws CannotDeleteUserException {
        val anyNull =  userCreate.name() == null || userCreate.email() == null || userCreate.roles() == null;
        if (anyNull) throw new CannotDeleteUserException("Uno o más campos obligatorios son nulos. No se puede crear el usuario");
    }

    private MissioValidationResponse doAdminAuthorization(final String token) throws InvalidProvidedToken, NotAdminException {
        val decoded = authorizationChecker.doTokenVerification(token);
        val isAdmin = authorizationChecker.verifyTokenAndAdmin(decoded);
        if(!isAdmin) throw new NotAdminException();
        return decoded;
    }

    protected User getUser(final Integer id) throws UserNotFound {
        val dbresponse = userRepository.findById(id).orElseThrow(() -> new UserNotFound(id));
        log.info("Encontrado usuario {}", dbresponse);
        return dbresponse;
    }

    private UserFullResponse userFullResponseFromUser(final User user) {
        var roles = rolesService.getRolesForUser(user.getId());
        log.info("Found roles for user {}: '{}'", user.getId(), roles);
        return UserFullResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .roles(roles)
                .build();
    }

    private boolean emailExists(final String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}

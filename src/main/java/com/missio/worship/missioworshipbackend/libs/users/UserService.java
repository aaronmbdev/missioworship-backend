package com.missio.worship.missioworshipbackend.libs.users;

import com.missio.worship.missioworshipbackend.libs.authentication.errors.InvalidProvidedToken;
import com.missio.worship.missioworshipbackend.libs.authentication.errors.NotAdminException;
import com.missio.worship.missioworshipbackend.libs.common.RestPaginationResponse;
import com.missio.worship.missioworshipbackend.libs.users.errors.*;
import com.missio.worship.missioworshipbackend.ports.api.common.AuthorizationChecker;
import com.missio.worship.missioworshipbackend.ports.api.users.UserCreate;
import com.missio.worship.missioworshipbackend.ports.datastore.entities.Role;
import com.missio.worship.missioworshipbackend.ports.datastore.entities.User;
import com.missio.worship.missioworshipbackend.ports.datastore.entities.UserRoles;
import com.missio.worship.missioworshipbackend.ports.datastore.repositories.UserRepository;
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
        authorizationChecker.verifyTokenValidity(token);
        val dbresponse = userRepository.findById(id).orElseThrow(() -> new UserNotFound(id));
        val roleList = rolesService.getRolesForUser(id)
                .stream()
                .map(Role::getName)
                .toList();
        log.info("Encontrado usuario {} con roles {}", dbresponse, roleList);

        return UserFullResponse.builder()
                .id(dbresponse.getId())
                .name(dbresponse.getName())
                .email(dbresponse.getEmail())
                .roles(roleList)
                .build();
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

        return UserFullResponse.builder()
                .id(saved.getId())
                .name(saved.getName())
                .email(saved.getEmail())
                .roles(userRoles.stream()
                        .map(entry -> entry.getRole().getName()).toList())
                .build();
    }

    public void deleteUser(final Integer id, final String token) throws InvalidProvidedToken, NotAdminException, UserNotFound, CannotDeleteUserException, EmptyResultDataAccessException {
        doAdminAuthorization(token);
        val user = userRepository.findById(id).orElseThrow(() -> new UserNotFound(id));
        val roles = rolesService.getRolesForUser(id);
        if(authorizationChecker.userIsAdminOrHimself(roles, user, token)) {
            throw new CannotDeleteUserException("El usuario a borrar es administrador. Sólo puede borrarse él mismo.");
        }
        userRepository.deleteById(id);
    }

    public UserFullResponse updateUser(final Integer id, final UserCreate userCreate, final String token) throws InvalidProvidedToken, NotAdminException, UserNotFound, RolNotFoundException, CannotDeleteUserException {
        doAdminAuthorization(token);
        var user = userRepository.findById(id).orElseThrow(() -> new UserNotFound(id));
        val existingRoles = rolesService.getRolesForUser(id);
        if(authorizationChecker.userIsAdminOrHimself(existingRoles, user, token)) {
            throw new CannotDeleteUserException("El usuario a borrar es administrador. Sólo puede borrarse él mismo.");
        }
        if(userCreate.name() != null) user.setName(userCreate.name());
        if(userCreate.email() != null) user.setEmail(userCreate.email());
        var roles = rolesService.getRolesForUser(id);
        if(userCreate.roles() != null) {
            roles = rolesService.setRolesForUser(userCreate.roles(), user);
        }
        val response = userRepository.save(user);
        return UserFullResponse.builder()
                .id(response.getId())
                .name(response.getName())
                .email(response.getEmail())
                .roles(roles.stream()
                        .map(Role::getName)
                        .toList())
                .build();
    }

    public RestPaginationResponse<User> getUserList(Integer limit, Integer offset, String bearerToken) throws InvalidProvidedToken, LessThanZeroException, WrongOffsetValueException {
        authorizationChecker.verifyTokenValidity(bearerToken);
        if (limit == null) limit = 0;
        if (offset == null) offset = 0;
        if (limit < 0 || offset < 0) throw new LessThanZeroException();
        if (limit != 0 && offset % limit != 0) throw new WrongOffsetValueException();
        val values = userRepository.findAllByPagination(offset, limit);
        RestPaginationResponse<User> response = new RestPaginationResponse<>();
        response.setValues(values);
        response.setLimit(limit);
        response.setOffset(offset);
        response.setNext_offset(limit + offset);
        return response;
    }

    public void validateForUserCreation(final UserCreate userCreate) throws CannotDeleteUserException {
        val anyNull =  userCreate.name() == null || userCreate.email() == null || userCreate.roles() == null;
        if (anyNull) throw new CannotDeleteUserException("Uno o más campos obligatorios son nulos. No se puede crear el usuario");
    }

    private void doAdminAuthorization(final String token) throws InvalidProvidedToken, NotAdminException {
        val isAdmin = authorizationChecker.verifyTokenAndAdmin(token);
        if(!isAdmin) throw new NotAdminException();
    }

    private boolean emailExists(final String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}

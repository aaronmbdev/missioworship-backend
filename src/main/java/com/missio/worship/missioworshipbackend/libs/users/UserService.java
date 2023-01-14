package com.missio.worship.missioworshipbackend.libs.users;

import com.missio.worship.missioworshipbackend.libs.authentication.AuthTokenService;
import com.missio.worship.missioworshipbackend.libs.authentication.errors.InvalidProvidedToken;
import com.missio.worship.missioworshipbackend.libs.authentication.errors.NotAdminException;
import com.missio.worship.missioworshipbackend.libs.users.errors.InvalidRolException;
import com.missio.worship.missioworshipbackend.libs.users.errors.UserNotFound;
import com.missio.worship.missioworshipbackend.ports.api.common.AuthorizationChecker;
import com.missio.worship.missioworshipbackend.ports.datastore.entities.User;
import com.missio.worship.missioworshipbackend.ports.datastore.entities.UserRoles;
import com.missio.worship.missioworshipbackend.ports.datastore.repositories.UserRepository;
import com.missio.worship.missioworshipbackend.ports.datastore.repositories.UserRolesRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    private final UserRolesRepository userRolesRepository;

    private final AuthTokenService authTokenService;

    private final RolesService rolesService;

    private final AuthorizationChecker authorizationChecker;

    public UserFullResponse getUser(final Integer id, final String token) throws UserNotFound, InvalidProvidedToken {
        authorizationChecker.verifyTokenValidity(token);
        val dbresponse = userRepository.findById(id).orElseThrow(() -> new UserNotFound("El usuario con id '" + id + "' no existe"));
        val roleList = userRolesRepository.findUserRolesByUserId(id);
        log.info("Encontrado usuario {} con roles {}", dbresponse, roleList);

        return UserFullResponse.builder()
                .name(dbresponse.getName())
                .email(dbresponse.getEmail())
                .roles(roleList.stream().map(entry -> Pair.of(entry.getId(), entry.getRole().getName())).toList())
                .build();
    }

    public UserFullResponse createUser(final String name, final String email, final List<Integer> roles, final String token) throws InvalidProvidedToken, NotAdminException, InvalidRolException {
        val isAdmin = authorizationChecker.verifyTokenAndAdmin(token);
        if(!isAdmin) throw new NotAdminException("El usuario no tiene permisos para realizar esta acción");
        val validRoles = rolesService.validateListOfRoles(roles);

        var user = new User();
        user.setEmail(email);
        user.setName(name);
        val saved = userRepository.save(user);

        val userRoles = validRoles.stream()
                .map(rolId -> new UserRoles(saved, rolId))
                .toList();

        rolesService.putUserRoles(userRoles);

        return UserFullResponse.builder()
                .name(saved.getName())
                .email(saved.getEmail())
                .build();
    }
}

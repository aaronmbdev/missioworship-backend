package com.missio.worship.missioworshipbackend.libs.users;

import com.missio.worship.missioworshipbackend.libs.authentication.AuthTokenService;
import com.missio.worship.missioworshipbackend.libs.authentication.errors.InvalidProvidedToken;
import com.missio.worship.missioworshipbackend.libs.authentication.errors.NotAdminException;
import com.missio.worship.missioworshipbackend.libs.users.errors.EmailAlreadyRegisteredException;
import com.missio.worship.missioworshipbackend.libs.users.errors.InvalidRolException;
import com.missio.worship.missioworshipbackend.libs.users.errors.RolNotFoundException;
import com.missio.worship.missioworshipbackend.libs.users.errors.UserNotFound;
import com.missio.worship.missioworshipbackend.ports.api.common.AuthorizationChecker;
import com.missio.worship.missioworshipbackend.ports.datastore.entities.User;
import com.missio.worship.missioworshipbackend.ports.datastore.entities.UserRoles;
import com.missio.worship.missioworshipbackend.ports.datastore.repositories.UserRepository;
import com.missio.worship.missioworshipbackend.ports.datastore.repositories.UserRolesRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.checkerframework.checker.units.qual.A;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    private final UserRolesRepository userRolesRepository;

    private final RolesService rolesService;

    private final AuthorizationChecker authorizationChecker;

    public UserFullResponse getUser(final Integer id, final String token) throws UserNotFound, InvalidProvidedToken {
        authorizationChecker.verifyTokenValidity(token);
        val dbresponse = userRepository.findById(id).orElseThrow(() -> new UserNotFound("El usuario con id '" + id + "' no existe"));
        val roleList = userRolesRepository.findUserRolesByUserId(id)
                .stream().map(entry -> entry.getRole().getName()).toList();
        log.info("Encontrado usuario {} con roles {}", dbresponse, roleList);

        return UserFullResponse.builder()
                .id(dbresponse.getId())
                .name(dbresponse.getName())
                .email(dbresponse.getEmail())
                .roles(roleList)
                .build();
    }

    public UserFullResponse createUser(final String name, final String email, final List<Integer> roles, final String token) throws RolNotFoundException, InvalidProvidedToken, NotAdminException, InvalidRolException, EmailAlreadyRegisteredException {
        val isAdmin = authorizationChecker.verifyTokenAndAdmin(token);
        if(!isAdmin) throw new NotAdminException("El usuario no tiene permisos para realizar esta acción");
        val validRoles = rolesService.validateListOfRoles(roles);
        if(emailExists(email)) throw new EmailAlreadyRegisteredException(email);

        var user = new User();
        user.setEmail(email);
        user.setName(name);
        val saved = userRepository.save(user);
        log.info("Se creó el usuario '{}'", user);

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

    private boolean emailExists(final String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}

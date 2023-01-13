package com.missio.worship.missioworshipbackend.libs.users;

import com.missio.worship.missioworshipbackend.libs.authentication.AuthTokenService;
import com.missio.worship.missioworshipbackend.libs.authentication.errors.InvalidProvidedToken;
import com.missio.worship.missioworshipbackend.libs.users.errors.UserNotFound;
import com.missio.worship.missioworshipbackend.ports.datastore.entities.User;
import com.missio.worship.missioworshipbackend.ports.datastore.entities.UserRoles;
import com.missio.worship.missioworshipbackend.ports.datastore.repositories.UserRepository;
import com.missio.worship.missioworshipbackend.ports.datastore.repositories.UserRolesRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    private final UserRolesRepository userRolesRepository;

    private final AuthTokenService authTokenService;

    public UserFullResponse getUser(final Integer id, final String token) throws UserNotFound, InvalidProvidedToken {
        val cleanToken = authTokenService.extractTokenFromHeader(token);
        val tokenVerification = authTokenService.verifyTokenValidity(cleanToken, new Date().toInstant());
        if(!tokenVerification.isValid()) {
            throw new InvalidProvidedToken();
        }
        val dbresponse = userRepository.findById(id).orElseThrow(() -> new UserNotFound("El usuario con id '" + id + "' no existe"));
        val roleList = userRolesRepository.findUserRolesByUserId(id);
        log.info("Encontrado usuario {} con roles {}", dbresponse, roleList);

        return UserFullResponse.builder()
                .name(dbresponse.getName())
                .email(dbresponse.getEmail())
                .roles(roleList.stream().map(entry -> Pair.of(entry.getId(), entry.getRole().getName())).toList())
                .build();
    }
}

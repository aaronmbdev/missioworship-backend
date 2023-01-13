package com.missio.worship.missioworshipbackend.libs.users;

import com.missio.worship.missioworshipbackend.libs.authentication.AuthTokenService;
import com.missio.worship.missioworshipbackend.libs.authentication.errors.InvalidProvidedToken;
import com.missio.worship.missioworshipbackend.libs.users.errors.UserNotFound;
import com.missio.worship.missioworshipbackend.ports.datastore.entities.User;
import com.missio.worship.missioworshipbackend.ports.datastore.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository repository;

    private final AuthTokenService authTokenService;

    public User getUser(final Integer id, final String token) throws UserNotFound, InvalidProvidedToken {
        val cleanToken = authTokenService.extractTokenFromHeader(token);
        val tokenVerification = authTokenService.verifyTokenValidity(cleanToken, new Date().toInstant());
        if(!tokenVerification.isValid()) {
            throw new InvalidProvidedToken();
        }
        return repository.findById(id).orElseThrow(() -> new UserNotFound("El usuario con id '" + id + "' no existe"));
    }
}

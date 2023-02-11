package com.missio.worship.missioworshipbackend.libs.authentication;

import com.missio.worship.missioworshipbackend.libs.authentication.errors.EmailNotFound;
import com.missio.worship.missioworshipbackend.libs.authentication.errors.InvalidProvidedToken;
import com.missio.worship.missioworshipbackend.libs.users.RolesService;
import com.missio.worship.missioworshipbackend.ports.datastore.entities.Role;
import com.missio.worship.missioworshipbackend.ports.datastore.entities.User;
import com.missio.worship.missioworshipbackend.ports.datastore.repositories.UserRepository;
import lombok.val;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class AuthenticationService {
    private final GoogleTokenValidator googleTokenValidator;

    private final AuthTokenService tokenIssuingService;

    private final UserRepository userRepository;

    private final RolesService rolesService;

    public AuthenticationService(GoogleTokenValidator googleTokenValidator, AuthTokenService tokenIssuingService, UserRepository userRepository, RolesService rolesService) {
        this.googleTokenValidator = googleTokenValidator;
        this.tokenIssuingService = tokenIssuingService;
        this.userRepository = userRepository;
        this.rolesService = rolesService;
    }

    public String validateTokenAndLogin(final String inputToken) throws InvalidProvidedToken, EmailNotFound {
        val googleResponse = googleTokenValidator.validateToken(inputToken);
        if(!googleResponse.isValid()) {
            throw new InvalidProvidedToken("El token suministrado no es válido");
        }
        val email = googleResponse.getEmail();
        val profile = googleResponse.getPictureUrl();
        val user = getUserInformation(email);

        val roles = getRoleInformationForUser(user.getId());

        return tokenIssuingService.issueToken(
                user.getId(),
                user.getName(),
                user.getEmail(),
                profile,
                roles.getLeft(),
                roles.getRight()
        );

    }

    public String validateTokenAndRenew(final String ourToken) throws InvalidProvidedToken, EmailNotFound {
        val tokenInformation = tokenIssuingService.verifyTokenValidity(ourToken, new Date().toInstant());
        if(!tokenInformation.isValid()) {
            throw new InvalidProvidedToken("El token ya no es válido. Probablemente haya expirado.");
        }
        val user = getUserInformation(tokenInformation.getEmail());
        val roles = getRoleInformationForUser(user.getId());
        return tokenIssuingService.issueToken(
                user.getId(),
                user.getName(),
                tokenInformation.getEmail(),
                tokenInformation.getProfilePicUrl(),
                roles.getLeft(),
                roles.getRight());
    }

    private User getUserInformation(final String email) throws EmailNotFound {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new EmailNotFound("El correo "+ email +" no está registrado en el sistema. Login no permitido")
        );
    }

    private Pair<List<String>, Integer> getRoleInformationForUser(final Integer id) {
        AtomicReference<Integer> highestClearanceLevel = new AtomicReference<>(0);
        val roles = rolesService.getRolesForUser(id)
                .stream()
                .map(entry -> {
                    if (entry.getClearance() > highestClearanceLevel.get()) highestClearanceLevel.set(entry.getClearance());
                    return entry.getName();
                })
                .toList();
        return Pair.of(roles, highestClearanceLevel.get());
    }


}

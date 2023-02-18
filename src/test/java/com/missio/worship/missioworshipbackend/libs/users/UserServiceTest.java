package com.missio.worship.missioworshipbackend.libs.users;

import com.missio.worship.missioworshipbackend.libs.authentication.MissioValidationResponse;
import com.missio.worship.missioworshipbackend.libs.authentication.errors.InvalidProvidedToken;
import com.missio.worship.missioworshipbackend.libs.users.errors.UserNotFound;
import com.missio.worship.missioworshipbackend.ports.api.common.AuthorizationChecker;
import com.missio.worship.missioworshipbackend.ports.datastore.entities.Role;
import com.missio.worship.missioworshipbackend.ports.datastore.entities.RoleSampler;
import com.missio.worship.missioworshipbackend.ports.datastore.entities.User;
import com.missio.worship.missioworshipbackend.ports.datastore.repositories.UserRepository;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RolesService rolesService;

    @Mock
    private AuthorizationChecker authorizationChecker;

    @InjectMocks
    private UserService service;

    @Test
    void getUserTest() throws InvalidProvidedToken, UserNotFound {
        val expectedResponse = UserFullResponseSampler.sample();
        val token = UUID.randomUUID().toString();
        val user_id = 1;
        var user = new User();
        user.setEmail(expectedResponse.getEmail());
        user.setName(expectedResponse.getName());
        user.setId(expectedResponse.getId());
        when(authorizationChecker.doTokenVerification(token)).thenReturn(MissioValidationResponse.builder()
                .isValid(true)
                .build()
        );
        when(userRepository.findById(user_id)).thenReturn(Optional.of(user));
        when(rolesService.getRolesForUser(user_id)).thenReturn(
                List.of(RoleSampler.sample(), RoleSampler.sample())
        );
        val response = service.getUser(user_id, token);
        assertThat(expectedResponse).isEqualTo(response);
    }

    @Test
    void InvalidTokenWhenGetUser() throws InvalidProvidedToken {
        val token = UUID.randomUUID().toString();
        when(authorizationChecker.doTokenVerification(token)).thenThrow(new InvalidProvidedToken("ERROR!!!"));
        assertThatThrownBy(() -> service.getUser(1, token))
                .isInstanceOf(InvalidProvidedToken.class);
    }
}
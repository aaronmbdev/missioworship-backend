package com.missio.worship.missioworshipbackend.ports.api.users;

import com.missio.worship.missioworshipbackend.libs.common.RestPaginationResponse;
import com.missio.worship.missioworshipbackend.libs.errors.NotFoundResponse;
import com.missio.worship.missioworshipbackend.libs.users.UserService;
import com.missio.worship.missioworshipbackend.libs.users.errors.UserNotFound;
import com.missio.worship.missioworshipbackend.ports.datastore.entities.User;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequestMapping("v1/user/")
@RestController
@AllArgsConstructor
public class UserControllerImpl implements UserController {
    private final UserService service;
    @Override
    public Mono<ResponseEntity<User>> getUser(Integer id) throws UserNotFound {
        val user = service.getUser(id);
        return Mono.just(ResponseEntity.ok(user));
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteUser(Integer id) {
        return null;
    }

    @Override
    public Mono<RestPaginationResponse<User>> getAllUsers(Integer startAt) {
        return null;
    }

    @Override
    public Mono<ResponseEntity<User>> updateUser(Integer id, User user) {
        return null;
    }

    @Override
    public Mono<ResponseEntity<User>> createUser(User user) {
        return null;
    }
}

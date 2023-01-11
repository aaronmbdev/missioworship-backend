package com.missio.worship.missioworshipbackend.ports.api.users;

import com.missio.worship.missioworshipbackend.libs.common.RestPaginationResponse;
import com.missio.worship.missioworshipbackend.ports.datastore.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequestMapping("v1/user/")
@RestController
public class UserControllerImpl implements UserController {
    @Override
    public Mono<ResponseEntity<User>> getUser(Integer id) {
        return null;
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

package com.missio.worship.missioworshipbackend.ports.api.users;

import com.missio.worship.missioworshipbackend.ports.datastore.entities.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Tag(name = "Controlador de Usuario", description = "Endpoints para usuario de missio")
@RestController
@RequestMapping("/api/user")
public interface UserController {

    Mono<ResponseEntity<User>> getUser();
    Mono<ResponseEntity<Void>> createUser();
    Mono<ResponseEntity<Void>> deleteUser();
}

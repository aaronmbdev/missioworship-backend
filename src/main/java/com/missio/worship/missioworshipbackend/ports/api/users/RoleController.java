package com.missio.worship.missioworshipbackend.ports.api.users;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import reactor.core.publisher.Mono;

public interface RoleController {
    Mono<ResponseEntity<Object>> getRole(Integer id, @RequestHeader(value = "Authorization", required = false) String bearerToken);

    Mono<ResponseEntity<Object>> listRoles(@RequestHeader(value = "Authorization", required = false) String bearerToken);

    Mono<ResponseEntity<Object>> createRole(String name, @RequestHeader(value = "Authorization", required = false) String bearerToken);

    Mono<ResponseEntity<Object>> deleteRole(Integer id, @RequestHeader(value = "Authorization", required = false) String bearerToken);
}

package com.missio.worship.missioworshipbackend.ports.api.users;

import com.missio.worship.missioworshipbackend.libs.users.RolesService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequestMapping("v1/role/")
@RestController
@AllArgsConstructor
public class RoleControllerImpl implements RoleController {

    private final RolesService service;

    @Override
    public Mono<ResponseEntity<Object>> listRoles(String bearerToken) {
        return null;
    }

    @Override
    public Mono<ResponseEntity<Object>> createRole(String name, String bearerToken) {
        return null;
    }

    @Override
    public Mono<ResponseEntity<Object>> deleteRole(Integer id, String bearerToken) {
        return null;
    }
}

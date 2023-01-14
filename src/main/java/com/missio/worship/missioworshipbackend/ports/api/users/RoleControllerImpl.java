package com.missio.worship.missioworshipbackend.ports.api.users;

import com.missio.worship.missioworshipbackend.libs.authentication.errors.InvalidProvidedToken;
import com.missio.worship.missioworshipbackend.libs.authentication.errors.NotAdminException;
import com.missio.worship.missioworshipbackend.libs.errors.ForbiddenResponse;
import com.missio.worship.missioworshipbackend.libs.errors.UnauthorizedResponse;
import com.missio.worship.missioworshipbackend.libs.users.RolesService;
import com.missio.worship.missioworshipbackend.ports.api.common.AuthorizationChecker;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
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
        try {
            val role = service.createRole(name, bearerToken);
            return Mono.just(ResponseEntity.ok(role));
        } catch (InvalidProvidedToken e) {
            val exception = new UnauthorizedResponse(e.getMessage());
            return Mono.just(new ResponseEntity<>(exception, HttpStatus.UNAUTHORIZED));
        } catch (NotAdminException e) {
            val exception = new ForbiddenResponse(e.getMessage());
            return Mono.just(new ResponseEntity<>(exception, HttpStatus.FORBIDDEN));
        }
    }

    @Override
    public Mono<ResponseEntity<Object>> deleteRole(Integer id, String bearerToken) {
        return null;
    }
}

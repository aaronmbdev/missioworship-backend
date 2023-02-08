package com.missio.worship.missioworshipbackend.ports.api.users;

import com.missio.worship.missioworshipbackend.libs.authentication.errors.InvalidProvidedToken;
import com.missio.worship.missioworshipbackend.libs.authentication.errors.NotAdminException;
import com.missio.worship.missioworshipbackend.libs.errors.BadRequestResponse;
import com.missio.worship.missioworshipbackend.libs.errors.ForbiddenResponse;
import com.missio.worship.missioworshipbackend.libs.errors.NotFoundResponse;
import com.missio.worship.missioworshipbackend.libs.errors.UnauthorizedResponse;
import com.missio.worship.missioworshipbackend.libs.users.RolesService;
import com.missio.worship.missioworshipbackend.libs.users.errors.RoleAlreadyExistsException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequestMapping("v1/role/")
@RestController
@AllArgsConstructor
@Slf4j
public class RoleControllerImpl implements RoleController {

    private final RolesService service;

    @Override
    public Mono<ResponseEntity<Object>> listRoles(String bearerToken) {
        try {
            val roles = service.getAllRoles(bearerToken);
            return Mono.just(ResponseEntity.ok(roles));
        } catch (InvalidProvidedToken e) {
            val exception = new UnauthorizedResponse(e.getMessage());
            return Mono.just(new ResponseEntity<>(exception, HttpStatus.UNAUTHORIZED));
        } catch (NotAdminException e) {
            val exception = new ForbiddenResponse(e.getMessage());
            return Mono.just(new ResponseEntity<>(exception, HttpStatus.FORBIDDEN));
        }
    }

    @Override
    public Mono<ResponseEntity<Object>> createRole(RoleCreate roleCreate, String bearerToken) {
        try {
            log.info("Controller tries to create a controller with '{}'",roleCreate);
            val role = service.createRole(roleCreate.name(), roleCreate.clearanceLevel(), bearerToken);
            return Mono.just(new ResponseEntity<>(role, HttpStatus.CREATED));
        } catch (InvalidProvidedToken e) {
            val exception = new UnauthorizedResponse(e.getMessage());
            return Mono.just(new ResponseEntity<>(exception, HttpStatus.UNAUTHORIZED));
        } catch (NotAdminException e) {
            val exception = new ForbiddenResponse(e.getMessage());
            return Mono.just(new ResponseEntity<>(exception, HttpStatus.FORBIDDEN));
        } catch (RoleAlreadyExistsException e) {
            val exception = new BadRequestResponse(e.getMessage());
            return Mono.just(new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST));
        }
    }

    @Override
    public Mono<ResponseEntity<Object>> deleteRole(Integer id, String bearerToken) {
        try {
            service.deleteRole(id, bearerToken);
            return Mono.empty();
        } catch (InvalidProvidedToken e) {
            val exception = new UnauthorizedResponse(e.getMessage());
            return Mono.just(new ResponseEntity<>(exception, HttpStatus.UNAUTHORIZED));
        } catch (NotAdminException e) {
            val exception = new ForbiddenResponse(e.getMessage());
            return Mono.just(new ResponseEntity<>(exception, HttpStatus.FORBIDDEN));
        } catch (EmptyResultDataAccessException e) {
            val exception = new NotFoundResponse("No existe un rol con id '" + id + "'");
            return Mono.just(new ResponseEntity<>(exception, HttpStatus.NOT_FOUND));
        }
    }
}

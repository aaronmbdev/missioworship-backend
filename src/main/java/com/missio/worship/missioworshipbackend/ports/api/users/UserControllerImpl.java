package com.missio.worship.missioworshipbackend.ports.api.users;

import com.missio.worship.missioworshipbackend.libs.authentication.errors.InvalidProvidedToken;
import com.missio.worship.missioworshipbackend.libs.authentication.errors.NotAdminException;
import com.missio.worship.missioworshipbackend.libs.common.RestPaginationResponse;
import com.missio.worship.missioworshipbackend.libs.errors.BadRequestResponse;
import com.missio.worship.missioworshipbackend.libs.errors.ForbiddenResponse;
import com.missio.worship.missioworshipbackend.libs.errors.NotFoundResponse;
import com.missio.worship.missioworshipbackend.libs.errors.UnauthorizedResponse;
import com.missio.worship.missioworshipbackend.libs.users.UserService;
import com.missio.worship.missioworshipbackend.libs.users.errors.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequestMapping("v1/user/")
@RestController
@AllArgsConstructor
@Slf4j
public class UserControllerImpl implements UserController {
    private final UserService service;

    @Override
    public Mono<ResponseEntity<Object>> getUser(Integer id, String dirtyToken) {
        try {
            val user = service.getUser(id, dirtyToken);
            log.info("Obtenido usuario {} correctamente", user);
            return Mono.just(ResponseEntity.ok(user));
        } catch (UserNotFound e) {
            val exception = new NotFoundResponse(e.getMessage());
            return Mono.just(new ResponseEntity<>(exception, HttpStatus.NOT_FOUND));
        } catch (InvalidProvidedToken e) {
            val exception = new UnauthorizedResponse(e.getMessage());
            return Mono.just(new ResponseEntity<>(exception, HttpStatus.UNAUTHORIZED));
        }
    }

    @Override
    public Mono<ResponseEntity<Object>> createUser(UserCreate user, String bearerToken) {
        try {
            log.info("Se intenta crear un usuario '{}'", user);
            service.validateForUserCreation(user);
            val result = service.createUser(user.name(), user.email(), user.roles(), bearerToken);
            log.info("Usuario creado correctamente; {}", result);
            return Mono.just(ResponseEntity.ok(result));
        } catch (InvalidProvidedToken e) {
            val exception = new UnauthorizedResponse(e.getMessage());
            return Mono.just(new ResponseEntity<>(exception, HttpStatus.UNAUTHORIZED));
        } catch(NotAdminException e) {
            val exception = new ForbiddenResponse(e.getMessage());
            return Mono.just(new ResponseEntity<>(exception, HttpStatus.FORBIDDEN));
        } catch (CannotDeleteUserException | InvalidRolException | RolNotFoundException | EmailAlreadyRegisteredException e) {
            val exception = new BadRequestResponse(e.getMessage());
            return Mono.just(new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST));
        }
    }


    @Override
    public Mono<ResponseEntity<Object>> deleteUser(Integer id, String bearerToken) {
        try {
            service.deleteUser(id, bearerToken);
            log.info("Borrado el usuario con id '{}'",id);
            return Mono.empty();
        } catch (UserNotFound | CannotDeleteUserException e) {
            val exception = new BadRequestResponse(e.getMessage());
            return Mono.just(new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST));
        } catch (InvalidProvidedToken e) {
            val exception = new UnauthorizedResponse(e.getMessage());
            return Mono.just(new ResponseEntity<>(exception, HttpStatus.UNAUTHORIZED));
        } catch (NotAdminException e) {
            val exception = new ForbiddenResponse(e.getMessage());
            return Mono.just(new ResponseEntity<>(exception, HttpStatus.FORBIDDEN));
        }
    }

    @Override
    public Mono<ResponseEntity<Object>> updateUser(Integer id, UserCreate user, String bearerToken) {
        try {
            val response = service.updateUser(id, user, bearerToken);
            log.info("Usuario '{}' actualizado correctamente con los siguientes datos: '{}'", id, user);
            return Mono.just(ResponseEntity.ok(response));
        } catch (UserNotFound | CannotDeleteUserException e) {
            val exception = new BadRequestResponse(e.getMessage());
            return Mono.just(new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST));
        } catch (InvalidProvidedToken e) {
            val exception = new UnauthorizedResponse(e.getMessage());
            return Mono.just(new ResponseEntity<>(exception, HttpStatus.UNAUTHORIZED));
        } catch (NotAdminException e) {
            val exception = new ForbiddenResponse(e.getMessage());
            return Mono.just(new ResponseEntity<>(exception, HttpStatus.FORBIDDEN));
        }
    }

    @Override
    public Mono<ResponseEntity<Object>> getAllUsers(Integer limit, Integer offset, String bearerToken) {
        try {
            val pagination = service.getUserList(limit, offset, bearerToken);
            return Mono.just(ResponseEntity.ok(pagination));
        } catch (LessThanZeroException | WrongOffsetValueException e) {
            val exception = new BadRequestResponse(e.getMessage());
            return Mono.just(new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST));
        } catch (InvalidProvidedToken e) {
            val exception = new UnauthorizedResponse(e.getMessage());
            return Mono.just(new ResponseEntity<>(exception, HttpStatus.UNAUTHORIZED));
        }
    }

}

package com.missio.worship.missioworshipbackend.ports.api.absences;

import com.missio.worship.missioworshipbackend.libs.authentication.errors.InvalidProvidedToken;
import com.missio.worship.missioworshipbackend.libs.errors.BadRequestResponse;
import com.missio.worship.missioworshipbackend.libs.errors.NotFoundResponse;
import com.missio.worship.missioworshipbackend.libs.errors.UnauthorizedResponse;
import com.missio.worship.missioworshipbackend.libs.users.AbsenceService;
import com.missio.worship.missioworshipbackend.libs.users.errors.CannotDeclareAbsenceException;
import com.missio.worship.missioworshipbackend.libs.users.errors.MissingRequiredException;
import com.missio.worship.missioworshipbackend.libs.users.errors.UserNotFound;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequestMapping("v1/absences/")
@AllArgsConstructor
@RestController
public class AbsencesControllerImpl implements AbsencesController{

    private final AbsenceService service;

    @Override
    public Mono<ResponseEntity<Object>> getAbsencesPerDate(AbsenceQueryInput input, String token) {
        try {
             val response = service.getAbsencesPerUserAndDate(input.userId(), input.begin(), input.end(), token);
             return Mono.just(ResponseEntity.ok(response));
        } catch (InvalidProvidedToken e) {
            val exception = new UnauthorizedResponse(e.getMessage());
            return Mono.just(new ResponseEntity<>(exception, HttpStatus.UNAUTHORIZED));
        } catch (MissingRequiredException e) {
            val exception = new BadRequestResponse(e.getMessage());
            return Mono.just(new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST));
        }
    }

    @Override
    public Mono<ResponseEntity<Object>> willBeAbsent(AbsenceBodyInput input, String token) {
        try {
            service.declareAbsence(input.user_id(), input.absenceDate(), token);
            return Mono.empty();
        } catch (UserNotFound e) {
            val exception = new NotFoundResponse(e.getMessage());
            return Mono.just(new ResponseEntity<>(exception, HttpStatus.NOT_FOUND));
        } catch (CannotDeclareAbsenceException e) {
            val exception = new BadRequestResponse(e.getMessage());
            return Mono.just(new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST));
        } catch (InvalidProvidedToken e) {
            val exception = new UnauthorizedResponse(e.getMessage());
            return Mono.just(new ResponseEntity<>(exception, HttpStatus.UNAUTHORIZED));
        }
    }

    @Override
    public Mono<ResponseEntity<Object>> willNotBeAbsent(AbsenceBodyInput input, String token) {
        try {
            service.rollbackDeclaredAbsence(input.user_id(), input.absenceDate(), token);
            return Mono.empty();
        } catch (CannotDeclareAbsenceException e) {
            val exception = new BadRequestResponse(e.getMessage());
            return Mono.just(new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST));
        } catch (UserNotFound e) {
            val exception = new NotFoundResponse(e.getMessage());
            return Mono.just(new ResponseEntity<>(exception, HttpStatus.NOT_FOUND));
        } catch (InvalidProvidedToken e) {
            val exception = new UnauthorizedResponse(e.getMessage());
            return Mono.just(new ResponseEntity<>(exception, HttpStatus.UNAUTHORIZED));
        }
    }


}

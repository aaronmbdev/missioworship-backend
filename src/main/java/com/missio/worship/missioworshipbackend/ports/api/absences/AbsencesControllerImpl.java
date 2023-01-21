package com.missio.worship.missioworshipbackend.ports.api.absences;

import com.missio.worship.missioworshipbackend.ports.datastore.entities.Absence;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RequestMapping("v1/absences/")
@RestController
public class AbsencesControllerImpl implements AbsencesController{
    @Override
    public Mono<ResponseEntity<List<Absence>>> getAbsencesPerDate(AbsenceQueryInput input) {
        /*
        Si el usuario no existe, devolvemos 404
         */
        return null;
    }

    @Override
    public Mono<ResponseEntity<Void>> willBeAbsent(AbsenceBodyInput input) {
        /*
        Dado un user_id y una fecha, queremos declarar que el usuario no viene en dicha fecha
        Si ya existe un registro, no se hace nada.
        Si el usuario no existe devolvemos 404
        Si el usuario del token no es admin, miramos que el user_id sea igual al del token
         */
        return null;
    }

    @Override
    public Mono<ResponseEntity<Void>> willNotBeAbsent(AbsenceBodyInput input) {
        /*
        Dado un user_id y una fecha, queremos declarar que el usuario si viene en dicha fecha
        Si no existe un registro de ausencia, no se hace nada
        Si ya existe un registro, no se hace nada.
        Si el usuario no existe devolvemos 404
        Si el usuario del token no es admin, miramos que el user_id sea igual al del token
         */
        return null;
    }
}

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
        return null;
    }

    @Override
    public Mono<ResponseEntity<Void>> willBeAbsent(AbsenceBodyInput input) {
        return null;
    }

    @Override
    public Mono<ResponseEntity<Void>> willNotBeAbsent(AbsenceBodyInput input) {
        return null;
    }
}

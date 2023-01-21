package com.missio.worship.missioworshipbackend.ports.datastore.repositories;

import com.missio.worship.missioworshipbackend.ports.datastore.entities.Absence;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Date;


public interface AbsencesRepository extends JpaRepository<Absence, Integer> {

    boolean existsByUserAndAbsenceDate(Integer user, Date date);

    void deleteByUserAndAbsenceDate(Integer user, Date date);
}

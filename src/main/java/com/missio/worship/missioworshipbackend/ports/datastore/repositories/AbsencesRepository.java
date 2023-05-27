package com.missio.worship.missioworshipbackend.ports.datastore.repositories;

import com.missio.worship.missioworshipbackend.ports.datastore.entities.Absence;
import com.missio.worship.missioworshipbackend.ports.datastore.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;


public interface AbsencesRepository extends JpaRepository<Absence, Integer> {

    boolean existsByUserAndAbsenceDate(User user, Date date);

    @Transactional
    void deleteByUserAndAbsenceDate(User user, Date date);

    @Query(value="SELECT * FROM absence a WHERE user_id = ?1 AND absence_date BETWEEN ?2 AND ?3", nativeQuery = true)
    List<Absence> findAllByUserAndAbsenceDate(Integer user, Date begin, Date end);

    List<Absence> findAllByAbsenceDate(Date date);
}

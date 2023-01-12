package com.missio.worship.missioworshipbackend.ports.datastore.repositories;

import com.missio.worship.missioworshipbackend.ports.datastore.entities.Absence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbsencesRepository extends JpaRepository<Absence, Integer> {
}

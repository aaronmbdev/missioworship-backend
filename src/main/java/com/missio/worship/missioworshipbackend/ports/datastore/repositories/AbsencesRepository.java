package com.missio.worship.missioworshipbackend.ports.datastore.repositories;

import com.missio.worship.missioworshipbackend.ports.datastore.entities.Absence;
import org.springframework.data.repository.CrudRepository;

public interface AbsencesRepository extends CrudRepository<Absence, Integer> {
}

package com.missio.worship.missioworshipbackend.ports.datastore.repositories;

import com.missio.worship.missioworshipbackend.ports.datastore.entities.SundaySongs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SundaySongsRepository extends JpaRepository<SundaySongs, Integer> {

}

package com.missio.worship.missioworshipbackend.ports.datastore.repositories;

import com.missio.worship.missioworshipbackend.ports.datastore.entities.SundaySongs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface SundaySongsRepository extends JpaRepository<SundaySongs, Integer> {

    Optional<SundaySongs> findBySunday(Date sunday);
}

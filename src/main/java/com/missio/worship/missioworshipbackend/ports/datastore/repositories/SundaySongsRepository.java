package com.missio.worship.missioworshipbackend.ports.datastore.repositories;

import com.missio.worship.missioworshipbackend.ports.datastore.entities.SundaySongs;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.Optional;

public interface SundaySongsRepository extends JpaRepository<SundaySongs, Integer> {

    Optional<SundaySongs> findBySunday(Date sunday);

    @Modifying
    @Transactional
    @Query(
            value = "DELETE FROM sunday_songs s WHERE s.sunday = ?1",
            nativeQuery = true)
    Integer deleteByDate(Date date);
}

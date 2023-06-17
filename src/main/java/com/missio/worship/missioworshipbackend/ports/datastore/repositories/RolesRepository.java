package com.missio.worship.missioworshipbackend.ports.datastore.repositories;

import com.missio.worship.missioworshipbackend.ports.datastore.entities.Role;
import com.missio.worship.missioworshipbackend.ports.datastore.entities.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RolesRepository extends JpaRepository<Role, Integer> {
    boolean existsByName(String name);

    @Query(value="SELECT * FROM roles r WHERE r.name LIKE ?1 ORDER BY r.name", nativeQuery = true)
    List<Role> findAllByFilter(String search);
}

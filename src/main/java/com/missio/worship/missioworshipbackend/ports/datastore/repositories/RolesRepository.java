package com.missio.worship.missioworshipbackend.ports.datastore.repositories;

import com.missio.worship.missioworshipbackend.ports.datastore.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Role, Integer> {
    boolean existsByName(String name);
}

package com.missio.worship.missioworshipbackend.ports.datastore.repositories;

import com.missio.worship.missioworshipbackend.ports.datastore.entities.UserRoles;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRolesRepository extends JpaRepository<UserRoles, Integer> {

    @Transactional
    Optional<List<UserRoles>> findUserRolesByUserId(Integer id);

    @Modifying
    @Transactional
    @Query(
            value = "DELETE FROM user_roles u WHERE u.user_id = ?1",
            nativeQuery = true)
    Integer deleteAllByUserId(Integer id);
}

package com.missio.worship.missioworshipbackend.ports.datastore.repositories;

import com.missio.worship.missioworshipbackend.ports.datastore.entities.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRolesRepository extends JpaRepository<UserRoles, Integer> {
    @Query(
            value = "SELECT * FROM user_roles u WHERE u.user_id = ?1",
            nativeQuery = true)
    List<UserRoles> findUserRolesByUserId(Integer id);
}

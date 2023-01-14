package com.missio.worship.missioworshipbackend.libs.users;

import com.missio.worship.missioworshipbackend.libs.authentication.errors.InvalidProvidedToken;
import com.missio.worship.missioworshipbackend.libs.authentication.errors.NotAdminException;
import com.missio.worship.missioworshipbackend.libs.users.errors.InvalidRolException;
import com.missio.worship.missioworshipbackend.libs.users.errors.RolNotFoundException;
import com.missio.worship.missioworshipbackend.libs.users.errors.RoleAlreadyExistsException;
import com.missio.worship.missioworshipbackend.ports.api.common.AuthorizationChecker;
import com.missio.worship.missioworshipbackend.ports.datastore.entities.Role;
import com.missio.worship.missioworshipbackend.ports.datastore.entities.UserRoles;
import com.missio.worship.missioworshipbackend.ports.datastore.repositories.RolesRepository;
import com.missio.worship.missioworshipbackend.ports.datastore.repositories.UserRolesRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@AllArgsConstructor
@Slf4j
public class RolesService {
    private final RolesRepository rolesRepository;

    private final UserRolesRepository userRolesRepository;

    private final AuthorizationChecker authorizationChecker;

    public Role createRole(@NonNull final String name, final String token) throws InvalidProvidedToken, NotAdminException, RoleAlreadyExistsException {
        checkAdminAuthOrDie(token);
        log.info("Service tries to create a rol with name '{}'",name);
        if(rolExists(name)) throw new RoleAlreadyExistsException(name);
        var role = new Role();
        role.setName(name);
        return rolesRepository.save(role);
    }

    public void deleteRole(final Integer id, final String token) throws InvalidProvidedToken, NotAdminException, EmptyResultDataAccessException {
        checkAdminAuthOrDie(token);
        rolesRepository.deleteById(id);
    }

    public List<Role> getAllRoles(final String token) throws InvalidProvidedToken, NotAdminException {
        checkAdminAuthOrDie(token);
        return rolesRepository.findAll();
    }

    public List<Role> validateListOfRoles(List<Integer> rolesIds) {
        return rolesIds.stream()
                .map(entry -> rolesRepository.findById(entry).orElseThrow(() -> new RolNotFoundException(entry))).toList();
    }

    public List<UserRoles> putUserRoles(List<UserRoles> userRoles) {
        return userRolesRepository.saveAll(userRoles);
    }

    private void checkAdminAuthOrDie(final String token) throws InvalidProvidedToken, NotAdminException {
        if(!authorizationChecker.verifyTokenAndAdmin(token)) {
            throw new NotAdminException();
        }
    }

    private boolean rolExists(final String name) {
        return rolesRepository.existsByName(name);
    }
}

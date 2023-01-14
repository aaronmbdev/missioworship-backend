package com.missio.worship.missioworshipbackend.libs.users;

import com.missio.worship.missioworshipbackend.libs.authentication.errors.InvalidProvidedToken;
import com.missio.worship.missioworshipbackend.libs.authentication.errors.NotAdminException;
import com.missio.worship.missioworshipbackend.libs.users.errors.InvalidRolException;
import com.missio.worship.missioworshipbackend.ports.api.common.AuthorizationChecker;
import com.missio.worship.missioworshipbackend.ports.datastore.entities.Role;
import com.missio.worship.missioworshipbackend.ports.datastore.entities.UserRoles;
import com.missio.worship.missioworshipbackend.ports.datastore.repositories.RolesRepository;
import com.missio.worship.missioworshipbackend.ports.datastore.repositories.UserRolesRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@AllArgsConstructor
@Slf4j
public class RolesService {
    private final RolesRepository rolesRepository;

    private final UserRolesRepository userRolesRepository;

    private final AuthorizationChecker authorizationChecker;

    public Role createRole(String name, String token) throws InvalidProvidedToken, NotAdminException {
        checkAdminAuthOrDie(token);
        var role = new Role();
        role.setName(name);
        return rolesRepository.save(role);
    }

    public List<Role> validateListOfRoles(List<Integer> rolesIds) {
        return rolesRepository.findAllById(rolesIds);
    }

    public void putUserRoles(List<UserRoles> userRoles) {
        userRolesRepository.saveAll(userRoles);
    }

    private void checkAdminAuthOrDie(final String token) throws InvalidProvidedToken, NotAdminException {
        if(!authorizationChecker.verifyTokenAndAdmin(token)) {
            throw new NotAdminException();
        }
    }
}

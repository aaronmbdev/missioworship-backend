package com.missio.worship.missioworshipbackend.libs.users;

import com.missio.worship.missioworshipbackend.ports.api.common.AuthorizationChecker;
import com.missio.worship.missioworshipbackend.ports.datastore.entities.Absence;
import com.missio.worship.missioworshipbackend.ports.datastore.repositories.AbsencesRepository;
import com.missio.worship.missioworshipbackend.ports.datastore.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class AbsenceService {

    private final UserService userService;

    private final RolesService rolesService;

    private final AuthorizationChecker authorizationChecker;

    private final AbsencesRepository repository;

    public List<Absence> getAbsencesPerUserAndDate() {
        return null;
    }

    public void declareAbsence(final String userId, final Date date) {

    }

    public void rollbackDeclaredAbsence() {

    }
}

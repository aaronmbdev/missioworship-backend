package com.missio.worship.missioworshipbackend.libs.users;

import com.missio.worship.missioworshipbackend.libs.authentication.errors.InvalidProvidedToken;
import com.missio.worship.missioworshipbackend.libs.users.errors.CannotDeclareAbsenceException;
import com.missio.worship.missioworshipbackend.libs.users.errors.UserNotFound;
import com.missio.worship.missioworshipbackend.ports.api.common.AuthorizationChecker;
import com.missio.worship.missioworshipbackend.ports.datastore.entities.Absence;
import com.missio.worship.missioworshipbackend.ports.datastore.repositories.AbsencesRepository;
import com.missio.worship.missioworshipbackend.ports.datastore.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
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

    public List<Absence> getAbsencesPerUserAndDate(final Integer userId, final Date begin, final Date end, final String token) throws InvalidProvidedToken {
        authorizationChecker.doTokenVerification(token);
        return repository.findAllByUserAndAbsenceDate(userId, begin, end);
    }

    public void declareAbsence(final Integer userId, final Date date, final String token) throws InvalidProvidedToken, UserNotFound, CannotDeclareAbsenceException {
        val decodedToken = authorizationChecker.doTokenVerification(token);
        val user = userService.getUser(userId);
        val roles = rolesService.getRolesForUser(userId);
        if(!authorizationChecker.userIsAdminOrHimself(roles, user, decodedToken)) {
            throw new CannotDeclareAbsenceException("Para declarar ausencia de otro miembro debe ser administrador.");
        }
        if(!absenceExists(userId, date)) {
            var absence = new Absence();
            absence.setAbsenceDate(date);
            absence.setUser(user);
            repository.save(absence);
        }
    }

    public void rollbackDeclaredAbsence(final Integer userId, final Date date, final String token) throws InvalidProvidedToken, UserNotFound, CannotDeclareAbsenceException {
        val decodedToken = authorizationChecker.doTokenVerification(token);
        val user = userService.getUser(userId);
        val roles = rolesService.getRolesForUser(userId);
        if(!authorizationChecker.userIsAdminOrHimself(roles, user, decodedToken)) {
            throw new CannotDeclareAbsenceException("Para anular la ausencia de otro miembro debe ser administrador.");
        }
        repository.deleteByUserAndAbsenceDate(userId, date);
    }

    private boolean absenceExists(final Integer userId, final Date date) {
        return repository.existsByUserAndAbsenceDate(userId, date);
    }
}

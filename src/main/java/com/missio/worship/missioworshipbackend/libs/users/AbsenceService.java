package com.missio.worship.missioworshipbackend.libs.users;

import com.missio.worship.missioworshipbackend.libs.authentication.errors.InvalidProvidedToken;
import com.missio.worship.missioworshipbackend.libs.users.errors.CannotDeclareAbsenceException;
import com.missio.worship.missioworshipbackend.libs.users.errors.MissingRequiredException;
import com.missio.worship.missioworshipbackend.libs.users.errors.UserNotFound;
import com.missio.worship.missioworshipbackend.libs.utils.DateUtils;
import com.missio.worship.missioworshipbackend.ports.api.common.AuthorizationChecker;
import com.missio.worship.missioworshipbackend.ports.datastore.entities.Absence;
import com.missio.worship.missioworshipbackend.ports.datastore.entities.User;
import com.missio.worship.missioworshipbackend.ports.datastore.repositories.AbsencesRepository;
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

    private final AuthorizationChecker authorizationChecker;

    private final AbsencesRepository repository;

    public List<String> getAbsencesPerUserAndDate(Integer userId, final Date begin, final Date end, final String token)
            throws InvalidProvidedToken, MissingRequiredException {
        val decodedToken = authorizationChecker.doTokenVerification(token);
        if (userId == null) {
            userId = decodedToken.getId();
        }
        log.info("Intentando ver ausencias de usuario {} entre fechas {} y {}", userId, begin, end);
        if (begin.after(end)) {
            throw new MissingRequiredException("La fecha de finalización tiene que ser posterior a la fecha de inicio");
        }
        return repository.findAllByUserAndAbsenceDate(userId, begin, end)
                .stream()
                .map(absence -> DateUtils.parseFrom(absence.getAbsenceDate()))
                .toList();
    }

    public void declareAbsence(Integer userId, final Date date, final String token)
            throws InvalidProvidedToken, UserNotFound, CannotDeclareAbsenceException {
        val decodedToken = authorizationChecker.doTokenVerification(token);
        if(userId == null) {
            userId = decodedToken.getId();
        }
        val user = userService.getUser(userId);
        if(!authorizationChecker.userIsAdminOrHimself(user, decodedToken)) {
            throw new CannotDeclareAbsenceException("Para declarar ausencia de otro miembro debe ser administrador.");
        }
        if(!absenceExists(user, date)) {
            var absence = new Absence();
            absence.setAbsenceDate(date);
            absence.setUser(user);
            repository.save(absence);
        }
    }

    public void rollbackDeclaredAbsence(Integer userId, final Date date, final String token)
            throws InvalidProvidedToken, UserNotFound, CannotDeclareAbsenceException {
        val decodedToken = authorizationChecker.doTokenVerification(token);
        if(userId == null) {
            userId = decodedToken.getId();
        }
        val user = userService.getUser(userId);
        if(!authorizationChecker.userIsAdminOrHimself(user, decodedToken)) {
            throw new CannotDeclareAbsenceException("Para anular la ausencia de otro miembro debe ser administrador.");
        }
        repository.deleteByUserAndAbsenceDate(user, date);
    }

    public List<User> getUserAbsentInGivenDate(final Date date, final String token) throws InvalidProvidedToken {
        authorizationChecker.doTokenVerification(token);
        val absents = repository.findAllByAbsenceDate(date);
        return absents.stream()
                .map(Absence::getUser)
                .toList();
    }

    private boolean absenceExists(final User userId, final Date date) {
        return repository.existsByUserAndAbsenceDate(userId, date);
    }
}

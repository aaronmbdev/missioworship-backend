package com.missio.worship.missioworshipbackend.ports.api.absences;

import java.util.Date;

public record AbsenceBodyInput(Integer user_id, Date absenceDate) {
}

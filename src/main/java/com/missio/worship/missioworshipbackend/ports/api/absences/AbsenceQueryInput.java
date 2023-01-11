package com.missio.worship.missioworshipbackend.ports.api.absences;

import java.util.Date;

public record AbsenceQueryInput(Integer userId, Date begin, Date end) {
}

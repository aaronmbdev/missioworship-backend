package com.missio.worship.missioworshipbackend.libs.common;

import com.missio.worship.missioworshipbackend.libs.songs.errors.InvalidDateFilterException;
import com.missio.worship.missioworshipbackend.libs.users.errors.LessThanZeroException;
import com.missio.worship.missioworshipbackend.libs.users.errors.WrongOffsetValueException;
import lombok.Getter;

@Getter
public class SongPaginationInput extends PaginationInput {
    private String WHERE_CLAUSE, ORDER_CLAUSE;
    public SongPaginationInput(Integer limit, Integer offset) throws WrongOffsetValueException, LessThanZeroException {
        super(limit, offset);
    }

    public SongPaginationInput(Integer limit, Integer offset, String dateFilter, String activeFilter)
            throws LessThanZeroException, WrongOffsetValueException, InvalidDateFilterException, InvalidActiveFilterException {
        super(limit, offset);
        if (!dateFilter.equals("created") && !dateFilter.equals("played")) {
            throw new InvalidDateFilterException(dateFilter);
        }
        if (!activeFilter.equals("all") && !activeFilter.equals("active") && !activeFilter.equals("unactive")) {
            throw new InvalidActiveFilterException(activeFilter);
        }
        computeOrderClause(dateFilter);
        computeWhereClause(activeFilter);
    }

    private void computeOrderClause(final String filter) {
        String column = "creationDate";
        if (filter.equals("played")) column = "lastSunday";
        this.ORDER_CLAUSE = "ORDER BY " + column + " DESC";
    }

    private void computeWhereClause(final String filter) {
        if (filter.equals("active")) {
            this.WHERE_CLAUSE = "WHERE active";
        }
        if (filter.equals("unactive")) {
            this.WHERE_CLAUSE = "WHERE NOT active";
        }
        if (filter.equals("all")) {
            this.WHERE_CLAUSE = "";
        }
    }
}

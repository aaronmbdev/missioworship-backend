package com.missio.worship.missioworshipbackend.libs.common;

import com.missio.worship.missioworshipbackend.libs.songs.errors.InvalidDateFilterException;
import com.missio.worship.missioworshipbackend.libs.users.errors.LessThanZeroException;
import com.missio.worship.missioworshipbackend.libs.users.errors.WrongOffsetValueException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class SongPaginationInput extends PaginationInput {
    private String whereActive;
    private String orderClause;
    public SongPaginationInput(Integer limit, Integer offset) throws WrongOffsetValueException, LessThanZeroException {
        super(limit, offset);
    }

    public SongPaginationInput(Integer limit, Integer offset, String dateFilter, String activeFilter)
            throws LessThanZeroException, WrongOffsetValueException, InvalidDateFilterException, InvalidActiveFilterException {
        super(limit, offset);
        if(dateFilter == null) {
            dateFilter = "created";
        }
        if(activeFilter == null) {
            activeFilter = "all";
        }
        if (!dateFilter.equals("created") && !dateFilter.equals("played")) {
            throw new InvalidDateFilterException(dateFilter);
        }
        if (!activeFilter.equals("all") && !activeFilter.equals("active") && !activeFilter.equals("unactive")) {
            throw new InvalidActiveFilterException(activeFilter);
        }
        this.whereActive = activeFilter;
        computeOrderClause(dateFilter);
    }

    private void computeOrderClause(final String filter) {
        String column = "creation_date";
        if (filter.equals("played")) column = "lastSunday";
        this.orderClause = column;
    }

    public String toString() {
        return "[PaginationInput limit='" + this.getLimit() +"', offset='" + this.getOffset() +
                "', whereClause='" + this.getWhereActive() + "', orderClause='" + this.getOrderClause() + "']";
    }
}

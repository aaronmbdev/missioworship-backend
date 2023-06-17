package com.missio.worship.missioworshipbackend.libs.common;

import com.missio.worship.missioworshipbackend.libs.users.errors.LessThanZeroException;
import com.missio.worship.missioworshipbackend.libs.users.errors.WrongOffsetValueException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class SongPaginationInput extends PaginationInput {
    private final String activeFilter;
    private String searchQuery;

    public SongPaginationInput(Integer limit, Integer offset, String activeFilter, String searchQuery)
            throws LessThanZeroException, WrongOffsetValueException, InvalidActiveFilterException {
        super(limit, offset);
        if(activeFilter == null) {
            activeFilter = "all";
        }
        if (!activeFilter.equals("all") && !activeFilter.equals("active") && !activeFilter.equals("unactive")) {
            throw new InvalidActiveFilterException(activeFilter);
        }
        this.activeFilter = activeFilter;
        if (!searchQuery.isBlank()) {
            this.searchQuery = "%" + searchQuery + "%";
        } else {
            this.searchQuery = "%";
        }
    }

    @Override
    public String toString() {
        return "SongPaginationInput{" +
                "whereActive='" + activeFilter + '\'' +
                ", searchQuery='" + searchQuery + '\'' +
                '}';
    }
}

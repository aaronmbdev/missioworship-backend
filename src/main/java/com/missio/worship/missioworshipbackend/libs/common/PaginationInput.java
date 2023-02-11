package com.missio.worship.missioworshipbackend.libs.common;


import com.missio.worship.missioworshipbackend.libs.users.errors.LessThanZeroException;
import com.missio.worship.missioworshipbackend.libs.users.errors.WrongOffsetValueException;
import lombok.Getter;

@Getter
public class PaginationInput {
    private final int limit;
    private final int offset;
    private final int nextOffset;

    public PaginationInput(int limit, int offset)
            throws WrongOffsetValueException, LessThanZeroException {
        if (limit < 0 || offset < 0) throw new LessThanZeroException();
        if (limit != 0 && offset % limit != 0) throw new WrongOffsetValueException();
        this.limit = limit;
        this.offset = offset;
        this.nextOffset = limit + offset;
    }
}

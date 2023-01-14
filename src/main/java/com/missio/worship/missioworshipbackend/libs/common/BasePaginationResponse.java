package com.missio.worship.missioworshipbackend.libs.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public abstract class BasePaginationResponse<T> {

    @JsonProperty(value = "offset", index = 2)
    Integer offset;

    @JsonProperty(value = "limit", index = 1)
    Integer limit;

    @JsonProperty(value = "next_offset", index = 3)
    Integer next_offset;

    public abstract List<T> getValues();

    public abstract void setValues(List<T> values);
}
package com.missio.worship.missioworshipbackend.libs.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public abstract class BasePaginationResponse<T> {

    @JsonProperty(value = "max_results", index = 1)
    protected Integer maxResults;

    @JsonProperty(value = "total_records", index = 2)
    Long totalRecords;

    @JsonProperty(value = "start_at", index = 3)
    Integer startAt;

    @JsonProperty(value = "is_last", index = 4)
    Boolean isLast;

    public abstract List<T> getValues();

    public abstract void setValues(List<T> values);
}
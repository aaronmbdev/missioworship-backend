package com.missio.worship.missioworshipbackend.libs.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Jacksonized
public class RestPaginationResponse<T> extends BasePaginationResponse<T> {

    @JsonProperty(index = 10)
    @ToString.Exclude
    List<T> values;
}

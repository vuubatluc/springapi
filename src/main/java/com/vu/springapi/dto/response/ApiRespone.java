package com.vu.springapi.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiRespone<T> {
    private int code = 1000;
    private String message;
    private T result;

}

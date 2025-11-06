package com.vu.springapi.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNCATEGORIZE_EXCEPTION(999, "uncategorize exception!"),
    USERNAME_EXIST(1002, "Username existed!"),
    EMAIL_EXIST(1002, "Email existed!"),
    USER_NOT_FOUND(1004, "User not found!"),
    INVALID_KEY(1001, "Invalid message key!"),
    PASSWORD_INVALID(1003, "password must be at least 8 characters!"),
    NOT_BLANK(1005, "this field not blank!")

    ;


    private int code;
    private String message;
}

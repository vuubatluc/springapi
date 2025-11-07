package com.vu.springapi.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNCATEGORIZE_EXCEPTION(999, "uncategorize exception!", HttpStatus.INTERNAL_SERVER_ERROR),
    USERNAME_EXIST(1002, "Username existed!", HttpStatus.BAD_REQUEST),
    EMAIL_EXIST(1002, "Email existed!", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1004, "User not found!", HttpStatus.NOT_FOUND),
    INVALID_KEY(1001, "Invalid message key!", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1003, "password must be at least 8 characters!", HttpStatus.BAD_REQUEST),
    NOT_BLANK(1005, "this field not blank!", HttpStatus.BAD_REQUEST),
    USERNAME_INCORRECT(1006, "Username or password is incorrect!", HttpStatus.UNAUTHORIZED),
    PASSWORD_INCORRECT(1007, "Password is incorrect!", HttpStatus.UNAUTHORIZED),
    ACCESS_DENIED(1008, "Access denied!", HttpStatus.FORBIDDEN),
    UNAUTHENTICATED(1009, "Unauthenticated!", HttpStatus.UNAUTHORIZED)
    ;

    private int code;
    private String message;
    private HttpStatusCode statusCode;
}

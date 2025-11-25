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
    USERNAME_INCORRECT(1006, "Username or password is incorrect!", HttpStatus.UNAUTHORIZED),
    PASSWORD_INCORRECT(1007, "Password is incorrect!", HttpStatus.UNAUTHORIZED),
    ACCESS_DENIED(1008, "Access denied!", HttpStatus.FORBIDDEN),
    UNAUTHENTICATED(1009, "Unauthenticated!", HttpStatus.UNAUTHORIZED),
    EMAIL_NOT_FOUND(1010, "Email not found!", HttpStatus.NOT_FOUND),
    OTP_INVALID(1011, "OTP is invalid or expired!", HttpStatus.BAD_REQUEST),
    OTP_EXPIRED(1012, "OTP has expired!", HttpStatus.BAD_REQUEST),
    CURRENT_PASSWORD_INCORRECT(1013, "Current password is incorrect!", HttpStatus.UNAUTHORIZED),
    ROLE_NOT_FOUND(1014, "Role not found!", HttpStatus.NOT_FOUND),
    PERMISSION_NOT_FOUND(1015, "Permission not found!", HttpStatus.NOT_FOUND),
    PRODUCT_NOT_FOUND(1016, "Product not found!", HttpStatus.NOT_FOUND),
    PRODUCT_SKU_EXISTED(1017, "Product SKU existed!", HttpStatus.BAD_REQUEST),
    CATEGORY_NOT_FOUND(1018, "Category not found!", HttpStatus.NOT_FOUND),
    FILE_EMPTY(1019, "File is empty!", HttpStatus.BAD_REQUEST),
    FILE_INVALID(1020, "Invalid file path!", HttpStatus.BAD_REQUEST),
    FILE_NOT_IMAGE(1021, "File must be an image!", HttpStatus.BAD_REQUEST),
    FILE_TOO_LARGE(1022, "File size must not exceed 5MB!", HttpStatus.BAD_REQUEST),
    FILE_UPLOAD_FAILED(1023, "Could not upload file!", HttpStatus.INTERNAL_SERVER_ERROR)
    ;

    private int code;
    private String message;
    private HttpStatusCode statusCode;
}

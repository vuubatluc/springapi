package com.vu.springapi.dto.request;

import com.vu.springapi.exception.ErrorCode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class UserCreateRequest {

    @NotBlank(message = "NOT_BLANK")
    private String name;

    @NotBlank(message = "NOT_BLANK")
    private String username;

    @Size(min = 8, message = "PASSWORD_INVALID")
    @NotBlank(message = "NOT_BLANK")
    private String password;

    @NotBlank(message = "NOT_BLANK")
    private String email;

    @NotNull(message = "NOT_BLANK")
    private LocalDate dob;

}

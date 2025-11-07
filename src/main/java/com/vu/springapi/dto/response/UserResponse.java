package com.vu.springapi.dto.response;


import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String id;
    private String name;
    private String username;
    private String email;
    private Set<String> roles;
}

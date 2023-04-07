package com.cda.freely.controller.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationRequest {
    @NotEmpty(message = "First name cannot be empty")
    private String first_name;
    private String last_name;

    private String gender;
    private String email;
    private String password;

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

}

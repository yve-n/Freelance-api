package com.cda.freely.controller.auth;

import com.cda.freely.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class RegistrationResponse {
    private String message;
    private User user;

    public RegistrationResponse() {
    }

    public RegistrationResponse(String message, User user) {

        this.message = message;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

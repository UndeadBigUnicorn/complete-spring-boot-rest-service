package com.xquestions.fullrestcomplete.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginRequest {

    @NotNull
    @Size(min = 5, message = "Username should not be less than 5")
    private String username;

    @NotNull
    @Size(min = 5, message = "Password should not be less than 5")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

package com.gorent.api.dto;

public class RegisterUserDTO {

    public static final int PASSWORD_MIN_LENGTH = 6;
    public static final int PASSWORD_MAX_LENGTH = 100;

    private String email;

    private String password;

    private String confirmPassword;

    public RegisterUserDTO() {
    }

    public RegisterUserDTO(String email, String password, String confirmPassword) {
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}

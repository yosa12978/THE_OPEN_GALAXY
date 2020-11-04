package com.yosa.theopengalaxy.dtos;

public class AccountReadDto {
    public String username;
    public String email;
    public String password;
    public String password_confrim;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getPassword_confrim() {
        return password_confrim;
    }

    public void setPassword_confrim(String password_confrim) {
        this.password_confrim = password_confrim;
    }
}

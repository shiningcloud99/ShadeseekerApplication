package com.example.myapplication;

public class HelperClass {

    String username, email, password, confirm;

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

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public HelperClass(String username, String email, String password, String confirm) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.confirm = confirm;
    }

    public HelperClass() {
    }
}
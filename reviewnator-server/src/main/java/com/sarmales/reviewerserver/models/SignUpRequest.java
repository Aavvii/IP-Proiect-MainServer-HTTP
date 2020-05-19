package com.sarmales.reviewerserver.models;

public class SignUpRequest {
    private String name;
    private String lastName;
    private String nickname;
    private String password;
    private String email;

    public SignUpRequest(String name, String lastName, String nickname, String password, String email) {
        this.name = name;
        this.lastName = lastName;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

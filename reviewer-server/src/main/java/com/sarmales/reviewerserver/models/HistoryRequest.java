package com.sarmales.reviewerserver.models;

public class HistoryRequest {
    private String username;

    public HistoryRequest(String username) {
        this.username = username;
    }
    public HistoryRequest(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

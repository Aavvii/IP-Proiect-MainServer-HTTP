package com.sarmales.reviewerserver.models;

public class HistoryRequest {
    private String nickname;

    public HistoryRequest(String nickname) {
        this.nickname = nickname;
    }
    public HistoryRequest(){}

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}

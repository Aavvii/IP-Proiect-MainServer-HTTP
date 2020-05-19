package com.sarmales.reviewerserver.models;

public class MobileAppRequest {
    private String encoding;
    private String isbn;
    private String nickname;

    public MobileAppRequest() {
    }
    public MobileAppRequest(String encoding, String isbn, String nickname) {
        this.encoding = encoding;
        this.isbn = isbn;
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEncoding() {
        return encoding;
    }
    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }
}
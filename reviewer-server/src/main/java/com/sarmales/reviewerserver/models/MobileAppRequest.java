package com.sarmales.reviewerserver.models;

public class MobileAppRequest {
    private String encoding;
    private String isbn;

    public MobileAppRequest() {
    }
    public MobileAppRequest(String encoding, String isbn) {
        this.encoding = encoding;
        this.isbn = isbn;
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
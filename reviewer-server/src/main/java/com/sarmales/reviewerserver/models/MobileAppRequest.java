package com.sarmales.reviewerserver.models;

public class MobileAppRequest {
    private String encoding;
    private long isbn;

    public MobileAppRequest() {
    }

    public MobileAppRequest(long isbn) {
        this.isbn = isbn;
    }

    public MobileAppRequest(String encoding) {
        this.encoding = encoding;
    }

    public long getIsbn() {
        return isbn;
    }

    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }
}

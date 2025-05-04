package ru.hits.common.dtos.notification;

import java.util.List;

public class NotificationRequest {
    private String title;
    private String body;

    private List<String> tokens;

    public NotificationRequest() {
    }

    public NotificationRequest(String title, String body, List<String> tokens) {
        this.title = title;
        this.body = body;
        this.tokens = tokens;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<String> getTokens() {
        return tokens;
    }

    public void setTokens(List<String> tokens) {
        this.tokens = tokens;
    }
}

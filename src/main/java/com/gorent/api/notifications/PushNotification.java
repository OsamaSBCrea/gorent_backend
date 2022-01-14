package com.gorent.api.notifications;

public class PushNotification {

    private String title;
    private String message;
    private String topic;
    private String token;

    public PushNotification() {
    }

    public PushNotification(String title, String message, String topic, String token) {
        this.title = title;
        this.message = message;
        this.topic = topic;
        this.token = token;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public String toString() {
        return "PushNotification{" +
                "title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", topic='" + topic + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}

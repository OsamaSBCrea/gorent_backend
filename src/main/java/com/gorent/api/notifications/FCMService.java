package com.gorent.api.notifications;

import com.google.firebase.messaging.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class FCMService {

    private final Logger log = LoggerFactory.getLogger(FCMService.class);

    public void sendMessageToToken(PushNotification notification, Long propertyId)
            throws InterruptedException, ExecutionException {
        Map<String, String> notificationData = new HashMap<>();
        notificationData.put("PROPERTY_ID", propertyId.toString());
        Message message = getPreconfiguredMessageWithData(notificationData, notification);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonOutput = gson.toJson(message);
        log.info(notification.toString());
        String response = sendAndGetResponse(message);
        log.info("Sent message to token. Device token: " + notification.getToken() + ", " + response + " msg " + jsonOutput);
    }

    private String sendAndGetResponse(Message message) throws InterruptedException, ExecutionException {
        return FirebaseMessaging.getInstance().sendAsync(message).get();
    }


    private AndroidConfig getAndroidConfig(String topic) {
        return AndroidConfig.builder()
                .setTtl(Duration.ofMinutes(2).toMillis()).setCollapseKey(topic)
                .setPriority(AndroidConfig.Priority.HIGH)
                .setNotification(AndroidNotification.builder()
                        .setTag(topic).build()).build();
    }

    private ApnsConfig getApnsConfig(String topic) {
        return ApnsConfig.builder()
                .setAps(Aps.builder().setCategory(topic).setThreadId(topic).build()).build();
    }

    private Message getPreconfiguredMessageToToken(PushNotification notification) {
        return getPreconfiguredMessageBuilder(notification).setToken(notification.getToken())
                .build();
    }

    private Message getPreconfiguredMessageWithoutData(PushNotification notification) {
        return getPreconfiguredMessageBuilder(notification).setTopic(notification.getTopic())
                .build();
    }

    private Message getPreconfiguredMessageWithData(Map<String, String> data, PushNotification notification) {
        return getPreconfiguredMessageBuilder(notification).putAllData(data).setToken(notification.getToken())
                .build();
    }

    private Message.Builder getPreconfiguredMessageBuilder(PushNotification notification) {
        AndroidConfig androidConfig = getAndroidConfig(notification.getTopic());
        ApnsConfig apnsConfig = getApnsConfig(notification.getTopic());
        return Message.builder()
                .setApnsConfig(apnsConfig).setAndroidConfig(androidConfig).setNotification(
                        new Notification(notification.getTitle(), notification.getMessage()));
    }

}

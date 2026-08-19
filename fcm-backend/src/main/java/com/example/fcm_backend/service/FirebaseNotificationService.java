package com.example.fcm_backend.service;

import com.example.fcm_backend.domain.DeviceToken;
import com.example.fcm_backend.exception.NotificationDeliveryException;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FirebaseNotificationService {

    private final DeviceTokenService deviceTokenService;

    public void sendToDevice(String token, String title, String body) {
        try {
            String messageId = FirebaseMessaging.getInstance().send(buildMessage(token, title, body));
            log.info("Notification sent. MessageId: {}", messageId);
        } catch (FirebaseMessagingException e) {
            log.error("Failed to deliver notification: {}", e.getMessage());
            throw new NotificationDeliveryException("Failed to deliver push notification: " + e.getMessage(), e);
        }
    }

    public void broadcast(String title, String body) {
        List<DeviceToken> tokens = deviceTokenService.findAll();
        log.info("Broadcasting to {} devices", tokens.size());

        long successCount = tokens.stream()
                .filter(deviceToken -> trySend(deviceToken.getToken(), title, body))
                .count();

        log.info("Broadcast complete. Success: {}/{}", successCount, tokens.size());
    }

    private boolean trySend(String token, String title, String body) {
        try {
            FirebaseMessaging.getInstance().send(buildMessage(token, title, body));
            return true;
        } catch (FirebaseMessagingException e) {
            log.error("Failed to send to device: {}", e.getMessage());
            return false;
        }
    }

    private Message buildMessage(String token, String title, String body) {
        return Message.builder()
                .setToken(token)
                .setNotification(Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build())
                .build();
    }
}

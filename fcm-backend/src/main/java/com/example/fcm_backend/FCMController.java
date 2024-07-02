package com.example.fcm_backend;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fcm")
public class FCMController {

    private static final Logger logger = LoggerFactory.getLogger(FCMController.class);

    @Autowired
    private TokenRepository tokenRepository;

    @PostMapping("/register-token")
    public ResponseEntity<String> registerToken(@RequestBody TokenRequest tokenRequest) {
        if (tokenRequest.getToken() == null || tokenRequest.getToken().isEmpty()) {
            return ResponseEntity.badRequest().body("Token is required");
        }

        if (tokenRepository.existsByToken(tokenRequest.getToken())) {
            return ResponseEntity.ok("Token already registered");
        }

        Token newToken = new Token();
        newToken.setToken(tokenRequest.getToken());
        tokenRepository.save(newToken);

        return ResponseEntity.ok("Token registered");
    }

    @PostMapping("/send-notification")
    public ResponseEntity<String> sendNotification(@RequestBody NotificationRequest notificationRequest) {
        if (notificationRequest.getTitle() == null || notificationRequest.getBody() == null) {
            return ResponseEntity.badRequest().body("Title and body are required");
        }

        List<Token> tokens = tokenRepository.findAll();

        Notification notification = Notification.builder()
                .setTitle(notificationRequest.getTitle())
                .setBody(notificationRequest.getBody())
                .build();

        MulticastMessage message = MulticastMessage.builder()
                .setNotification(notification)
                .addAllTokens(tokens.stream().map(Token::getToken).toList())
                .build();

        try {
            FirebaseMessaging.getInstance().sendMulticast(message);
            return ResponseEntity.ok("Notification sent");
        } catch (FirebaseMessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending notification");
        }
    }

    @GetMapping("/tokens")
    public List<Token> getTokens() {
        return tokenRepository.findAll();
    }

    @Data
    static class TokenRequest {
        private String token;
    }

    @Data
    static class NotificationRequest {
        private String title;
        private String body;
    }
}



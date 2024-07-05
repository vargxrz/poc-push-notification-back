package com.example.fcm_backend;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    public void registerToken(String token) {
        if (!tokenRepository.existsByToken(token)) {
            Token newToken = new Token();
            newToken.setToken(token);
            tokenRepository.save(newToken);
        }
    }

    public void sendNotification(String token, String title, String body) throws FirebaseMessagingException {
        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();

        Message message = Message.builder()
                .setToken(token)
                .setNotification(notification)
                .build();

        FirebaseMessaging.getInstance().send(message);
    }

    public void sendDailyNotifications() {
        List<Token> tokens = tokenRepository.findAll();
        for (Token token : tokens) {
            try {
                sendNotification(token.getToken(), "Teste Programado", "Testando Testando");
            } catch (FirebaseMessagingException e) {
                System.err.println("Erro ao enviar a notificação: " + e.getMessage());
            }
        }
    }
}
package com.example.fcm_backend;

import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/fcm")
public class FCMController {

    @Autowired
    private TokenService tokenService;

    @PostMapping("/register-token")
    public ResponseEntity<String> registerToken(@RequestBody TokenRequest tokenRequest) {
        if (tokenRequest.getToken() == null || tokenRequest.getToken().isEmpty()) {
            return ResponseEntity.badRequest().body("Token is required");
        }

        tokenService.registerToken(tokenRequest.getToken());

        try {
            tokenService.sendNotification(tokenRequest.getToken(), "Bem-vindo!", "Seu token foi registrado com sucesso.");
        } catch (FirebaseMessagingException e) {
            return ResponseEntity.status(500).body("Erro ao enviar a notificação: " + e.getMessage());
        }

        return ResponseEntity.ok("Token registrado e notificação enviada");
    }

    @PostMapping("/send-notification")
    public ResponseEntity<String> sendNotification(@RequestBody TokenRequest tokenRequest) {
        try {
            tokenService.sendNotification(tokenRequest.getToken(), tokenRequest.getTitle(), tokenRequest.getMessage());
            return ResponseEntity.ok("Notificação enviada");
        } catch (FirebaseMessagingException e) {
            return ResponseEntity.status(500).body("Erro ao enviar a notificação: " + e.getMessage());
        }
    }

    @Data
    static class TokenRequest {
        private String token;
        private String message;
        private String title;
    }
}

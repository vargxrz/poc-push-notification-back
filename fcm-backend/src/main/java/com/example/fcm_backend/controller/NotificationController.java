package com.example.fcm_backend.controller;

import com.example.fcm_backend.dto.request.RegisterTokenRequest;
import com.example.fcm_backend.dto.request.SendNotificationRequest;
import com.example.fcm_backend.dto.response.ApiResponse;
import com.example.fcm_backend.service.DeviceTokenService;
import com.example.fcm_backend.service.FirebaseNotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@Tag(name = "Notifications", description = "Device token registration and push notification delivery")
public class NotificationController {

    private final DeviceTokenService deviceTokenService;
    private final FirebaseNotificationService firebaseNotificationService;

    @PostMapping("/register")
    @Operation(summary = "Register a device token and send a welcome notification")
    public ResponseEntity<ApiResponse<Void>> registerToken(@Valid @RequestBody RegisterTokenRequest request) {
        deviceTokenService.register(request.getToken());
        firebaseNotificationService.sendToDevice(request.getToken(), "Bem-vindo!", "Seu dispositivo foi registrado com sucesso.");
        return ResponseEntity.ok(ApiResponse.ok("Token registrado com sucesso", null));
    }

    @PostMapping("/send")
    @Operation(summary = "Send a push notification to a specific device")
    public ResponseEntity<ApiResponse<Void>> sendNotification(@Valid @RequestBody SendNotificationRequest request) {
        firebaseNotificationService.sendToDevice(request.getToken(), request.getTitle(), request.getBody());
        return ResponseEntity.ok(ApiResponse.ok("Notificação enviada com sucesso", null));
    }
}

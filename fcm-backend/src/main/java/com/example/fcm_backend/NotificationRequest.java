package com.example.fcm_backend;

import lombok.Data;

@Data
public class NotificationRequest {
    private String title;
    private String body;

}

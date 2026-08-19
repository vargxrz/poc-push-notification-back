package com.example.fcm_backend.scheduler;

import com.example.fcm_backend.service.FirebaseNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationScheduler {

    private final FirebaseNotificationService firebaseNotificationService;

    @Scheduled(cron = "${notification.scheduler.daily-cron:0 0 9 * * *}")
    public void scheduleDailyNotification() {
        log.info("Starting scheduled daily notification broadcast");
        firebaseNotificationService.broadcast("Notificação diária", "Bom dia! Confira as novidades de hoje.");
    }
}

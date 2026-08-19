package com.example.fcm_backend.service;

import com.example.fcm_backend.domain.DeviceToken;
import com.example.fcm_backend.repository.DeviceTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeviceTokenService {

    private final DeviceTokenRepository deviceTokenRepository;

    public void register(String token) {
        if (deviceTokenRepository.existsByToken(token)) {
            log.debug("Token already registered, skipping");
            return;
        }
        DeviceToken deviceToken = new DeviceToken();
        deviceToken.setToken(token);
        deviceTokenRepository.save(deviceToken);
        log.info("Device token registered");
    }

    public List<DeviceToken> findAll() {
        return deviceTokenRepository.findAll();
    }
}

package ru.hits.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.hits.common.dtos.notification.NotificationRequest;
import ru.hits.common.security.JwtUserData;
import ru.hits.service.FirebasePushService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final FirebasePushService firebasePushService;

    @GetMapping("/integration/notification/send/{id}")
    public ResponseEntity<?> sendNotification(@PathVariable UUID id) {
        firebasePushService.sendNotification(id);
        return ResponseEntity.ok("Уведомления отправлены");
    }

    @PostMapping("/api/notification/register")
    public ResponseEntity<?> registration(
            Authentication authentication,
            @RequestHeader(name = "firebase-token") String token){
        JwtUserData userData = (JwtUserData) authentication.getPrincipal();
        firebasePushService.registrationUser(token, userData.getId());
        return ResponseEntity.ok("Подключено");
    }
}


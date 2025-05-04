package ru.hits.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hits.common.dtos.notification.NotificationRequest;
import ru.hits.service.FirebasePushService;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final FirebasePushService firebasePushService;

    public NotificationController(FirebasePushService firebasePushService) {
        this.firebasePushService = firebasePushService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(@RequestBody NotificationRequest request) {
        firebasePushService.sendNotification(request.getTitle(), request.getBody(), request.getTokens());
        return ResponseEntity.ok("Уведомления отправлены");
    }
}


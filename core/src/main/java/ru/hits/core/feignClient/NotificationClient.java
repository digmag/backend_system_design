package ru.hits.core.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@FeignClient(name="notification-client",url = "${NOTIFICATION_SERVICE_URL:http://localhost:8080}")
public interface NotificationClient {
    @GetMapping("/integration/notification/send/{id}")
    void sendNotification(@PathVariable UUID id);
}

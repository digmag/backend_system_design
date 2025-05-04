package ru.hits.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FirebasePushService {
    public void sendNotification(String title, String body, List<String> tokens) {
        for (String token : tokens) {
            Message message = Message.builder()
                    .setToken(token)
                    .setNotification(Notification.builder()
                            .setTitle(title)
                            .setBody(body)
                            .build())
                    .build();

            try {
                String response = FirebaseMessaging.getInstance().send(message);
                System.out.println("Уведомление успешно отправлено: " + response);
            } catch (FirebaseMessagingException e) {
                System.err.println("Ошибка при отправке уведомления: " + e.getMessage());
            }
        }
    }
}


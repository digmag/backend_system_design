package ru.hits.service;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hits.common.dtos.user.Status;
import ru.hits.common.dtos.user.UserResponseDTO;
import ru.hits.common.security.exception.BadRequestException;
import ru.hits.common.security.exception.ForbiddenException;
import ru.hits.common.security.exception.NotFoundException;
import ru.hits.feignClient.UserClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FirebasePushService {
    private final Map<UserResponseDTO, String> usersToNotification = new HashMap<>();
    private final UserClient userClient;
    private final FirebaseApp firebaseApp;
    @Transactional
    @SneakyThrows
    public void registrationUser(String token, UUID userId) {
        UserResponseDTO user;
        try{
            user = userClient.getOne(userId);
        }catch (RuntimeException e){
            throw new NotFoundException("Пользователь для регистрации не найден");
        }
        usersToNotification.put(user, token);
    }


    public void sendNotification(UUID clientId){
        usersToNotification.forEach((userResponseDTO, token) -> {
            Notification notificationUser = Notification.builder()
                    .setTitle("Новая операция")
                    .setBody("Ваша информация изменилась, проверьте ее")
                    .build();
            Notification notificationEmployee = Notification.builder()
                    .setTitle("Информация пользователя изменилась")
                    .setBody("Информация пользователя: "+userResponseDTO.getEmail()+" изменилась")
                    .build();
            if(userResponseDTO.getId().equals(clientId)){
                Message message = Message.builder()
                        .setToken(token)
                        .setNotification(notificationUser)
                        .build();
                try {
                    FirebaseMessaging.getInstance(firebaseApp).send(message);
                } catch (FirebaseMessagingException e) {
                    throw new ForbiddenException("Не удалось отправить сообщение");
                }
            }
            if (userResponseDTO.getStatus() == Status.EMPLOYEE){
                Message message = Message.builder()
                        .setToken(token)
                        .setNotification(notificationEmployee)
                        .build();
                try {
                    FirebaseMessaging.getInstance().send(message);
                } catch (FirebaseMessagingException e) {
                    throw new ForbiddenException("Не удалось отправить сообщение");
                }
            }

        });
    }
}


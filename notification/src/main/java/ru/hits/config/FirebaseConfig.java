package ru.hits.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    private final FirebaseOptions firebaseOptions;
    @SneakyThrows
    public FirebaseConfig(){
        InputStream serviceAccount =
                new ClassPathResource("test-b5a4e-firebase-adminsdk-fbsvc-791500452d.json").getInputStream();
        firebaseOptions = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();
    }

    @Bean
    public FirebaseApp firebaseApp(){
        System.out.println("Firebase успешно инициализирован");
        return FirebaseApp.initializeApp(firebaseOptions);
    }
}

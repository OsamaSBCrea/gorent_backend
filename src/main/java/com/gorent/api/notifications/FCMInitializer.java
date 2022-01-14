package com.gorent.api.notifications;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.gorent.api.config.GorentProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
public class FCMInitializer {

    private final GorentProperties gorentProperties;

    private final Logger log = LoggerFactory.getLogger(FCMInitializer.class);

    public FCMInitializer(GorentProperties gorentProperties) {
        this.gorentProperties = gorentProperties;
    }

    @PostConstruct
    public void initialize() {
        try {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(
                            new ClassPathResource(gorentProperties.getFirebaseConfigFile()).getInputStream()))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                log.info("Firebase app has been initialized successfully");
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

}

package za.co.RecruitmentZone.configuration;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Objects;

@Configuration
class GoogleCloudConfig {

    @Value("${spring.cloud.gcp.credentials.location}")
    private String keyFilePath;

    @Bean
    public Storage storage() {
        try {
            // Create a StorageOptions object with the credentials from the key file
            StorageOptions storageOptions = StorageOptions.newBuilder()
                    .setCredentials(ServiceAccountCredentials.fromStream(
                            Objects.requireNonNull(getClass().getResourceAsStream(keyFilePath))))
                    .build();

            // Create and return a Storage instance
            return storageOptions.getService();
        } catch (IOException e) {
            throw new RuntimeException("Error initializing Google Cloud Storage.", e);
        }
    }
}

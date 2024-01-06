package za.co.recruitmentzone.employee.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Component
public class TokenGeneratorDecoder {
    private final Logger log = LoggerFactory.getLogger(TokenGeneratorDecoder.class);

    public String encodeToken(String rawToken, String secretKey) {
        // Convert the rawToken (long value) to a string
        String tokenString = String.valueOf(rawToken);

        // Concatenate the token string with the secret key
        String dataToHash = tokenString + secretKey;

        // Generate HMAC-SHA-256 hash
        //byte[] hashedBytes = generateHmacSHA256Hash(dataToHash.getBytes(), secretKey);

        // Encode the hash to a Base64 string
       // return Base64.getUrlEncoder().withoutPadding().encodeToString(hashedBytes);
        return dataToHash;
    }


    public boolean validateToken(String rawToken, String encodedToken, String secretKey) {
        // Convert the rawToken (long value) to a string
        String tokenString = String.valueOf(rawToken);

        // Concatenate the token string with the secret key
        String dataToHash = tokenString + secretKey;

        // Generate HMAC-SHA-256 hash
        //byte[] expectedHash = generateHmacSHA256Hash(dataToHash.getBytes(), secretKey);

        // Decode the encoded token to get the actual hash
        //byte[] actualHash = Base64.getUrlDecoder().decode(encodedToken);

        // Compare the expected and actual hashes
        //return MessageDigest.isEqual(expectedHash, actualHash);
        return true;
    }


    private byte[] generateHmacSHA256Hash(byte[] data, String secretKey) {
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            return sha256_HMAC.doFinal(data);
        } catch (Exception e) {
            log.info("Error generating HMAC SHA-256 hash\n {}", e.getMessage());
            throw new RuntimeException("Error generating HMAC SHA-256 hash", e);
        }
    }


}


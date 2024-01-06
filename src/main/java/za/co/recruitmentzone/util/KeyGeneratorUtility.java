package za.co.recruitmentzone.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.KeyPair;
import java.security.KeyPairGenerator;


public class KeyGeneratorUtility {
    // check dan vega and joe granger videos on this topic
    private static final Logger log = LoggerFactory.getLogger(KeyGeneratorUtility.class);

    public static KeyPair generateRSAKey() {
        KeyPair keyPair;

        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception e) {
            log.info("Exception");
            throw new IllegalStateException("EXCEPTION");
        }

        return keyPair;
    }
}

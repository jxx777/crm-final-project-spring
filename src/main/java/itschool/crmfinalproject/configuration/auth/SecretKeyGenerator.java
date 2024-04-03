package itschool.crmfinalproject.configuration.auth;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * Utility class for generating a secure random secret key.
 */
public class SecretKeyGenerator {

    /**
     * Generates a base64 encoded secure random key.
     * This method creates a 256-bit secure random key using {@link SecureRandom},
     * which is then encoded into a base64 string.
     *
     * @return A base64 encoded string representing the generated secret key.
     */
    public static String generateSecretKey() {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[32]; // 256-bit key
        random.nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }
}
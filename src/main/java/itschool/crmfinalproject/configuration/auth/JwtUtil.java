package itschool.crmfinalproject.configuration.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static itschool.crmfinalproject.configuration.auth.SecretKeyGenerator.generateSecretKey;

/**
 * Utility class for JWT (JSON Web Token) operations, including token generation and validation.
 */
@Component
public class JwtUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    private final String SECRET_KEY = generateSecretKey();

    /**
     * Generates an access token for a specified username with a set expiration time.
     *
     * @param username the username for which the access token will be generated
     * @return a JWT access token as a String
     */
    public String generateAccessToken(String username) {
        long EXPIRATION_TIME_ACCESS_TOKEN = TimeUnit.MINUTES.toMillis(30);
        return JWT.create()
                .withSubject(username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME_ACCESS_TOKEN))
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    /**
     * Generates a refresh token for a specified username with a set expiration time.
     *
     * @param username the username for which the refresh token will be generated
     * @return a JWT refresh token as a String
     */
    public String generateRefreshToken(String username) {
        long EXPIRATION_TIME_REFRESH_TOKEN = TimeUnit.DAYS.toMillis(1);
        return JWT.create()
                .withSubject(username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME_REFRESH_TOKEN))
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    /**
     * Validates a token's integrity and checks if it is intended for the specified user.
     *
     * @param token        the JWT to be validated
     * @param userDetails  the user details against which the token will be validated
     * @return true if the token is valid and false otherwise
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * Validates the integrity of a refresh token.
     *
     * @param token the JWT to be validated
     * @return true if the token is valid and false otherwise
     */
    public Boolean validateRefreshToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET_KEY)).build();
            DecodedJWT jwt = verifier.verify(token);
            return !jwt.getExpiresAt().before(new Date());
        } catch (JWTVerificationException exception) {
            logger.error("Error validating refresh token: {}", exception.getMessage());
            return false;
        }
    }

    /**
     * Extracts the username from a given token.
     *
     * @param token the JWT from which the username will be extracted
     * @return the username if extraction is successful, null otherwise
     */
    public String extractUsername(String token) {
        DecodedJWT decodedJWT = getDecodedJWT(token);
        return decodedJWT != null ? decodedJWT.getSubject() : null;
    }

    /**
     * Checks if a token is expired.
     *
     * @param token the JWT to check for expiration
     * @return true if the token is expired, false otherwise
     */
    private Boolean isTokenExpired(String token) {
        DecodedJWT decodedJWT = getDecodedJWT(token);
        return decodedJWT != null && decodedJWT.getExpiresAt().before(new Date());
    }

    /**
     * Decodes a JWT and returns its {@link DecodedJWT} instance.
     *
     * @param token the JWT to be decoded
     * @return the decoded JWT, or null if an error occurs during decoding
     */
    private DecodedJWT getDecodedJWT(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET_KEY)).build();
            return verifier.verify(token);
        } catch (JWTVerificationException exception) {
            logger.error("Error decoding token: {}", exception.getMessage());
            return null;
        }
    }
}
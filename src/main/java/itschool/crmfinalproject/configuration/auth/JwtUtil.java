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

@Component
public class JwtUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    private final String SECRET_KEY = generateSecretKey();

    public String generateAccessToken(String username) {
        long EXPIRATION_TIME_ACCESS_TOKEN = TimeUnit.MINUTES.toMillis(30);
        return JWT.create()
                .withSubject(username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME_ACCESS_TOKEN))
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    public String generateRefreshToken(String username) {
        long EXPIRATION_TIME_REFRESH_TOKEN = TimeUnit.DAYS.toMillis(1);
        return JWT.create()
                .withSubject(username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME_REFRESH_TOKEN))
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

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

    public String extractUsername(String token) {
        DecodedJWT decodedJWT = getDecodedJWT(token);
        return decodedJWT != null ? decodedJWT.getSubject() : null;
    }

    private Boolean isTokenExpired(String token) {
        DecodedJWT decodedJWT = getDecodedJWT(token);
        return decodedJWT != null && decodedJWT.getExpiresAt().before(new Date());
    }

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
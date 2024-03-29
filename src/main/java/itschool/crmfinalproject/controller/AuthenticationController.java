package itschool.crmfinalproject.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import itschool.crmfinalproject.configuration.auth.JwtUtil;
import itschool.crmfinalproject.model.auth.RequestAuthenticationDTO;
import itschool.crmfinalproject.model.auth.RequestRegistrationDTO;
import itschool.crmfinalproject.service.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> signUp(@RequestBody RequestRegistrationDTO requestRegistrationDTO) {
        try {
            return authenticationService.createAccount(requestRegistrationDTO);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error processing request");
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody RequestAuthenticationDTO requestAuthenticationDTO) {
        try {
            // Use accessAccount to authenticate the user
            ResponseEntity<?> authResponse = authenticationService.accessAccount(requestAuthenticationDTO);

            // If authentication fails, forward the error response
            // is2xxSuccessful() - Not falling into 200-299
            if (!authResponse.getStatusCode().is2xxSuccessful()) {
                return authResponse;
            }

            // If authentication is successful, generate both access and refresh tokens
            String accessToken = jwtUtil.generateAccessToken(requestAuthenticationDTO.username());
            String refreshToken = jwtUtil.generateRefreshToken(requestAuthenticationDTO.username());

            // Create a response map or a DTO to send both tokens back to the client
            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", accessToken);
            tokens.put("refreshToken", refreshToken);

            return ResponseEntity.ok(tokens);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error processing request");
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String, String> tokenRequest) {
        String refreshToken = tokenRequest.get("refreshToken");
        if (!jwtUtil.validateRefreshToken(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
        }
        String username = jwtUtil.extractUsername(refreshToken);

        String newAccessToken = jwtUtil.generateAccessToken(username);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", newAccessToken);
        return ResponseEntity.ok(tokens);
    }
}
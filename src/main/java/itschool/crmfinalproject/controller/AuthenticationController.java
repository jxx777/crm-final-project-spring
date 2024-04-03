package itschool.crmfinalproject.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import itschool.crmfinalproject.configuration.auth.JwtUtil;
import itschool.crmfinalproject.model.auth.RequestAuthenticationDTO;
import itschool.crmfinalproject.model.auth.RequestRegistrationDTO;
import itschool.crmfinalproject.service.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication Controller", description = "Controller for handling authentication operations.")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final JwtUtil jwtUtil;

    @Operation(summary = "Register a new user", description = "Endpoint to register a new user in the system.")
    @PostMapping("/register")
    public ResponseEntity<?> signUp(@RequestBody RequestRegistrationDTO requestRegistrationDTO) {
        try {
            return authenticationService.createAccount(requestRegistrationDTO);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error processing request");
        }
    }

    @Operation(summary = "Sign in", description = "Endpoint for user authentication. Returns access and refresh tokens.")
    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody RequestAuthenticationDTO requestAuthenticationDTO) {
        try {
            ResponseEntity<?> authResponse = authenticationService.accessAccount(requestAuthenticationDTO);
            if (!authResponse.getStatusCode().is2xxSuccessful()) {
                return authResponse;
            }
            String accessToken = jwtUtil.generateAccessToken(requestAuthenticationDTO.username());
            String refreshToken = jwtUtil.generateRefreshToken(requestAuthenticationDTO.username());
            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", accessToken);
            tokens.put("refreshToken", refreshToken);
            return ResponseEntity.ok(tokens);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error processing request");
        }
    }

    @Operation(summary = "Refresh the access token", description = "Endpoint to refresh the access token using a refresh token.")
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
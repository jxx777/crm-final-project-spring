package itschool.crmfinalproject.authentification.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import itschool.crmfinalproject.authentification.config.JwtUtil;
import itschool.crmfinalproject.authentification.model.RequestAuthenticationDTO;
import itschool.crmfinalproject.authentification.model.RequestRegistrationDTO;
import itschool.crmfinalproject.authentification.service.AuthenticationService;
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
@Tag(name = "Authentication Service", description = "Handles user authentication processes, token generation, and session management.")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final JwtUtil jwtUtil;

    @Operation(summary = "Register a new user", description = "Endpoint to register a new user in the system.")
    @ApiResponse(responseCode = "200", description = "User registered successfully")
    @ApiResponse(responseCode = "400", description = "Invalid user data provided")
    @PostMapping("/register")
    public ResponseEntity<?> signUp(@RequestBody @Parameter(description = "Registration data") RequestRegistrationDTO requestRegistrationDTO) {
        try {
            return authenticationService.createAccount(requestRegistrationDTO);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error processing request");
        }
    }

    @Operation(summary = "Log in", description = "Endpoint for user authentication. Returns access and refresh tokens.")
    @ApiResponse(responseCode = "200", description = "Authentication successful")
    @ApiResponse(responseCode = "401", description = "Authentication failed")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody RequestAuthenticationDTO requestAuthenticationDTO) {
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

    @Operation(summary = "Refresh the access token", description = "Endpoint to refresh the access token using a refresh token. The request should contain the refresh token.")
    @ApiResponse(responseCode = "200", description = "Token refreshed successfully. Returns the new access token.")
    @ApiResponse(responseCode = "401", description = "Invalid refresh token. The refresh token provided is not valid or expired.")
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody @Parameter(description = "A JSON object containing the refresh token. Example: {\"refreshToken\": \"<your_refresh_token_here>\"}") Map<String, String> tokenRequest) {
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
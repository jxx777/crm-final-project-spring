package itschool.crmfinalproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import itschool.crmfinalproject.model.user.UserDTO;
import itschool.crmfinalproject.service.app.AvatarService;
import itschool.crmfinalproject.service.app.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "User Service", description = "Responsible for user data management, supporting information retrieval and user profile updates.")
public class UserController {

    private final UserService userService;
    private final AvatarService avatarService;

    @Operation(summary = "Get all users", description = "Retrieve a list of all users.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all users", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class)))
    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Operation(summary = "Get user by ID", description = "Retrieve fieldDetails of a specific user by their ID.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved user fieldDetails", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class)))
    @ApiResponse(responseCode = "404", description = "User not found")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @Operation(summary = "Get user by username", description = "Retrieve fieldDetails of a user by their username.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved user fieldDetails", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class)))
    @ApiResponse(responseCode = "404", description = "User not found")
    @GetMapping("/username/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.findByUsername(username));
    }

    @Operation(summary = "Update user email", description = "Update the email address of a user.")
    @ApiResponse(responseCode = "200", description = "Successfully updated user email")
    @ApiResponse(responseCode = "404", description = "User not found")
    @PatchMapping("/email/{id}")
    public ResponseEntity<UserDTO> updateUserEmail(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateUserEmail(id, userDTO.email()));
    }

    @Operation(summary = "Update username", description = "Update the username of a user.")
    @ApiResponse(responseCode = "200", description = "Successfully updated username")
    @ApiResponse(responseCode = "404", description = "User not found")
    @PatchMapping("/username/{id}")
    public ResponseEntity<UserDTO> updateUsername(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateUsername(id, userDTO.username()));
    }

    @Operation(summary = "Delete user", description = "Delete a user by their ID.")
    @ApiResponse(responseCode = "200", description = "User successfully deleted")
    @ApiResponse(responseCode = "404", description = "User not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        return userService.deleteById(id);
    }

    @Operation(summary = "Upload user avatar", description = "Upload an avatar image for a user.")
    @ApiResponse(responseCode = "200", description = "Avatar uploaded successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    @PutMapping("/avatar/{id}")
    public ResponseEntity<?> uploadUserAvatar(@PathVariable Long id, @RequestParam("avatar") MultipartFile avatar) {
        return userService.saveUserAvatar(id, avatar);
    }

    @Operation(summary = "Retrieve user avatar", description = "Retrieve avatar image for a user.")
    @ApiResponse(responseCode = "200", description = "Avatar retrieved successfully")
    @ApiResponse(responseCode = "404", description = "User or avatar not found")
    @GetMapping("/avatar/{id}")
    public byte[] getUserAvatar(@PathVariable Long id) {
        return avatarService.getUserAvatar(id);
    }
}
package itschool.crmfinalproject.controller;

import itschool.crmfinalproject.entity.user.Role;
import itschool.crmfinalproject.model.user.RoleUpdateDTO;
import itschool.crmfinalproject.model.user.UserDTO;
import itschool.crmfinalproject.service.app.AvatarService;
import itschool.crmfinalproject.service.app.RoleService;
import itschool.crmfinalproject.service.app.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AvatarService avatarService;
    private final RoleService roleService;

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> allUsers = userService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    @GetMapping("/usernames")
    public ResponseEntity<List<String>> getAllUsersUsernames() {
        List<String> allUsersUsernames = userService.getAllUsersUsernames();
        return ResponseEntity.ok(allUsersUsernames);
    }

    @GetMapping("/nicknames")
    public ResponseEntity<List<String>> getAllUsersNicknames() {
        List<String> allUsersEmails = userService.getAllUsersEmails();
        return ResponseEntity.ok(allUsersEmails);
    }

    @GetMapping("/roles")
    public ResponseEntity<?> getAllRoles() {
        List<Role> rolesList = roleService.getRoles();
        return ResponseEntity.ok(rolesList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        UserDTO user = userService.findByUsername(username);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/avatar/{id}")
    public ResponseEntity<byte[]> getUserAvatar(@PathVariable Long id) {
        byte[] avatar = avatarService.getUserAvatar(id);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) // Adjust based on the actual image type
                .body(avatar);
    }

    @PatchMapping("/email/{id}")
    public ResponseEntity<UserDTO> updateUserEmail(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        UserDTO user = userService.updateUserEmail(id, userDTO.email());
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/username/{id}")
    public ResponseEntity<UserDTO> updateUsername(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        UserDTO user = userService.updateUsername(id, userDTO.username());
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/role/{userId}")
    public ResponseEntity<UserDTO> updateUserRoles(@PathVariable Long userId, @RequestBody RoleUpdateDTO roleUpdateDTO) {
        UserDTO userToUpdate = userService.updateUserRole(userId, roleUpdateDTO.role());
        return ResponseEntity.ok(userToUpdate);
    }

    @PutMapping("/avatar/{id}")
    public ResponseEntity<?> uploadUserAvatar(@PathVariable Long id, @RequestParam("avatar") MultipartFile avatar) {
        return userService.saveUserAvatar(id, avatar);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        return userService.deleteById(id);
    }
}
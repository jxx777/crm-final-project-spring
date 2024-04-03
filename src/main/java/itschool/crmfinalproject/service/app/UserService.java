package itschool.crmfinalproject.service.app;

import itschool.crmfinalproject.entity.user.RoleEnum;
import itschool.crmfinalproject.entity.user.User;
import itschool.crmfinalproject.model.user.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<UserDTO> getAllUsers();

    List<String> getAllUsersUsernames();

    List<String> getAllUsersEmails();

    User createUser(User user);

    UserDTO findById(Long userId);

    UserDTO findByUsername(String username);

    UserDTO updateUserEmail(Long userId, String newEmail);

    UserDTO updateUsername(Long userId, String newUsername);

    UserDTO updateUserRole(Long userId, RoleEnum newRole);

    ResponseEntity<?> deleteById(Long id);

    String userId(String username);

    ResponseEntity<?> saveUserAvatar(Long userId, MultipartFile avatarFile);
}
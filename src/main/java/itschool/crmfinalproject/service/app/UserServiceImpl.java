package itschool.crmfinalproject.service.app;

import itschool.crmfinalproject.entity.user.Role;
import itschool.crmfinalproject.entity.user.RoleEnum;
import itschool.crmfinalproject.entity.user.User;
import itschool.crmfinalproject.exceptions.AvatarUploadException;
import itschool.crmfinalproject.exceptions.UserNotFoundException;
import itschool.crmfinalproject.mapper.UserMapper;
import itschool.crmfinalproject.model.user.UserDTO;
import itschool.crmfinalproject.repository.RoleRepository;
import itschool.crmfinalproject.repository.UserRepository;
import itschool.crmfinalproject.utility.GenerateResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final AvatarService avatarService;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(myUser -> org.springframework.security.core.userdetails.User.builder()
                .username(myUser.getUsername())
                .password(myUser.getPassword())
                .authorities(myUser.getAuthorities()).build())
                .orElse(null);
    }

    public User loadUserByRegisterToken(String token) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByRegisterToken(token);
        return user.orElse(null);
    }

    public String userId(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userOptional.map(
                user -> user.getId()
                        .toString())
                .orElseThrow(() -> new UserNotFoundException("User not found: " + username)
                );
    }

    public void activateUser(final User user) {
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public UserDTO findById(Long userId) {
        return userRepository.findById(userId)
                .map(userMapper::userToUserDto)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
    }

    @Override
    @Transactional
    public UserDTO findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::userToUserDto)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
            .map(userMapper::userToUserDto)
            .toList();
    }

    public List<String> getAllUsersUsernames() {
        return userRepository.findAll().stream()
            .map(User::getUsername)
            .toList();
    }

    public List<String> getAllUsersEmails() {
        return userRepository.findAll().stream()
            .map(User::getEmail)
            .toList();
    }

    public User createUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username is already taken");
        }

        // Encrypt the password before saving the user
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Set default properties for the new user
        user.setEnabled(true); // Or false if you require email verification
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);

        // Ensure the avatar is null for a new user
        user.setAvatar(null);

        // Assign a default role
        Role defaultRole = roleRepository.findByName(RoleEnum.USER).orElseThrow(() -> new IllegalStateException("User Role not found."));
        user.setRole(defaultRole);

        // Save the user to the repository
        return userRepository.save(user);
    }

    @SneakyThrows
    @Override
    public ResponseEntity<?> deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            return GenerateResponse.notFound("User not found", null);
        }

        userRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "User successfully deleted."));
    }

    @Override
    public UserDTO updateUserEmail(Long userId, String newEmail) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with ID: " + userId));
        user.setEmail(newEmail);
        userRepository.save(user);
        return userMapper.userToUserDto(user);
    }

    @Override
    public UserDTO updateUsername(Long userId, String newUsername) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + userId));
        user.setUsername(newUsername);
        userRepository.save(user);
        return userMapper.userToUserDto(user);
    }

    public UserDTO updateUserRole(Long userId, RoleEnum roleEnum) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with username: " + userId));
        Role role = roleRepository.findByName(roleEnum).orElseThrow(() -> new IllegalStateException("Role not found."));
        user.setRole(role);
        return userMapper.userToUserDto(user);
    }

    @Override
    public ResponseEntity<?> saveUserAvatar(Long userId, MultipartFile avatarFile) {
        try {
            avatarService.saveUserAvatar(userId, avatarFile);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Avatar uploaded successfully.");
            return ResponseEntity.ok(response);
        } catch (AvatarUploadException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Avatar upload failed.");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "An error occurred.");
            errorResponse.put("message", "Failed to upload avatar.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
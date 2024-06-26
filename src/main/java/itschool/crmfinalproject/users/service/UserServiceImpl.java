package itschool.crmfinalproject.users.service;

import itschool.crmfinalproject.users.entity.Role;
import itschool.crmfinalproject.users.entity.User;
import itschool.crmfinalproject.users.enums.RoleEnum;
import itschool.crmfinalproject.exceptions.AvatarUploadException;
import itschool.crmfinalproject.exceptions.UserNotFoundException;
import itschool.crmfinalproject.users.mapper.UserMapper;
import itschool.crmfinalproject.users.model.UserDTO;
import itschool.crmfinalproject.users.repository.RoleRepository;
import itschool.crmfinalproject.users.repository.UserRepository;
import itschool.crmfinalproject.common.utility.GenerateResponse;
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

    public String getIdByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userOptional.map(user -> user.getId().toString())
                .orElseThrow(() -> new UserNotFoundException("User not found: " + username));
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
                .map(userMapper::toUserDto)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
    }

    @Override
    @Transactional
    public UserDTO findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::toUserDto)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
            .map(userMapper::toUserDto)
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
        Role defaultRole = roleRepository.findByRole(RoleEnum.USER).orElseThrow(() -> new IllegalStateException("User Role not found."));
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
        return userMapper.toUserDto(user);
    }

    @Override
    public UserDTO updateUsername(Long userId, String newUsername) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + userId));
        user.setUsername(newUsername);
        userRepository.save(user);
        return userMapper.toUserDto(user);
    }

    public UserDTO updateUserRole(Long userId, RoleEnum roleEnum) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with username: " + userId));
        Role role = roleRepository.findByRole(roleEnum).orElseThrow(() -> new IllegalStateException("Role not found."));
        user.setRole(role);
        return userMapper.toUserDto(user);
    }

    @SneakyThrows
    @Override
    public ResponseEntity<?> saveUserAvatar(Long userId, MultipartFile avatarFile) {
        try {
            avatarService.saveUserAvatar(userId, avatarFile);
            return GenerateResponse.success("Avatar uploaded", null);
        } catch (AvatarUploadException e) {
            return GenerateResponse.badRequest("Error uploading avatar", e.getMessage());
        }
    }
}
package itschool.crmfinalproject.authentification.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import itschool.crmfinalproject.authentification.config.JwtUtil;
import itschool.crmfinalproject.users.entity.Role;
import itschool.crmfinalproject.users.entity.User;
import itschool.crmfinalproject.users.enums.RoleEnum;
import itschool.crmfinalproject.users.mapper.RegisterUserMapper;
import itschool.crmfinalproject.authentification.model.RequestAuthenticationDTO;
import itschool.crmfinalproject.authentification.model.RequestRegistrationDTO;
import itschool.crmfinalproject.users.repository.RoleRepository;
import itschool.crmfinalproject.users.repository.UserRepository;
import itschool.crmfinalproject.users.service.UserService;
import itschool.crmfinalproject.common.utility.GenerateResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserService userService;
    private final RegisterUserMapper registerUserMapper;

    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public ResponseEntity<String> accessAccount(RequestAuthenticationDTO param) throws JsonProcessingException {
        UserDetails userDetails = userService.loadUserByUsername(param.username());
        if (userDetails == null) {
            return GenerateResponse.notFound("User not found", null);
        }
        if (!encoder.matches(param.password(), userDetails.getPassword())) {
            return GenerateResponse.badRequest("Invalid password", null);
        }
        return GenerateResponse.success("Sign In Success", jwtUtil.generateAccessToken(userDetails.getUsername()));
    }

    @Transactional(rollbackOn = RuntimeException.class)
    public ResponseEntity<String> createAccount(RequestRegistrationDTO param) throws JsonProcessingException {
        if (userRepository.findByUsername(param.username()).isPresent()) {
            return GenerateResponse.badRequest("Username is already taken", null);
        }

        User customUser = registerUserMapper.signUpToUser(param);
        customUser.setPassword(encoder.encode(param.password()));

        Role userRole = roleRepository.findByRole(RoleEnum.ADMIN).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        customUser.setRole(userRole);
        customUser.setRegisterToken(UUID.randomUUID().toString());

        userRepository.save(customUser);
        return GenerateResponse.created("Sign Up Success", "You can now login into your account. No activation needed! For now.");
    }
}
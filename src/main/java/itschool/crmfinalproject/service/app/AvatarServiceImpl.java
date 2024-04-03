package itschool.crmfinalproject.service.app;

import itschool.crmfinalproject.entity.user.User;
import itschool.crmfinalproject.exceptions.AvatarUploadException;
import itschool.crmfinalproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AvatarServiceImpl implements AvatarService {

    private final UserRepository userRepository;

    @Override
    public void saveUserAvatar(Long userId, MultipartFile avatarFile) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        try {
            byte[] avatarBytes = avatarFile.getBytes();
            user.setAvatar(avatarBytes);
            userRepository.save(user);
        } catch (IOException e) {
            throw new AvatarUploadException("Error saving user avatar");
        }
    }

    @Override
    public byte[] getUserAvatar(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return user.getAvatar();
    }
}
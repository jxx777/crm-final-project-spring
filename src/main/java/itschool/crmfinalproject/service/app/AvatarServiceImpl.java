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
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AvatarUploadException("User not found with ID: " + userId));
        try {
            user.setAvatar(avatarFile.getBytes());
            userRepository.save(user);
        } catch (IOException e) {
            throw new AvatarUploadException("Error occurred while saving avatar.");
        }
    }

    @Override
    public byte[] getUserAvatar(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AvatarUploadException("User not found with ID: " + userId));
        return user.getAvatar();
    }
}
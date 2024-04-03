package itschool.crmfinalproject.service.app;

import org.springframework.web.multipart.MultipartFile;

public interface AvatarService {
    void saveUserAvatar(Long userId, MultipartFile avatarFile);
    byte[] getUserAvatar(Long userId);
}

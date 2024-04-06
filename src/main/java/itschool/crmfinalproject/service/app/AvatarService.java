package itschool.crmfinalproject.service.app;

import org.springframework.web.multipart.MultipartFile;

/**
 * Service interface for handling user avatar operations.
 */
public interface AvatarService {

    /**
     * Saves the avatar for a given user.
     *
     * @param userId The ID of the user whose avatar is being saved.
     * @param avatarFile The {@link MultipartFile} object containing the avatar data.
     */
    void saveUserAvatar(Long userId, MultipartFile avatarFile);

    /**
     * Retrieves the avatar for a given user.
     *
     * @param userId The ID of the user whose avatar is being retrieved.
     * @return A byte array containing the avatar data.
     */
    byte[] getUserAvatar(Long userId);
}
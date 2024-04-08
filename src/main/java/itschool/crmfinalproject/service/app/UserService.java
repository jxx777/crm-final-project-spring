package itschool.crmfinalproject.service.app;

import itschool.crmfinalproject.entity.user.User;
import itschool.crmfinalproject.enums.RoleEnum;
import itschool.crmfinalproject.model.user.UserDTO;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Interface for defining user service operations.
 */
public interface UserService extends UserDetailsService {

    /**
     * Retrieves a list of all users.
     *
     * @return A list of {@link UserDTO} objects representing all users.
     */
    List<UserDTO> getAllUsers();

    /**
     * Retrieves a list of all usernames.
     *
     * @return A list of strings representing all usernames.
     */
    List<String> getAllUsersUsernames();

    /**
     * Retrieves a list of all user emails.
     *
     * @return A list of strings representing all user emails.
     */
    List<String> getAllUsersEmails();

    /**
     * Creates a new user in the system.
     *
     * @param user The {@link User} entity to be created.
     * @return The created {@link User} entity.
     */
    User createUser(User user);

    /**
     * Finds a user by their ID.
     *
     * @param userId The ID of the user to find.
     * @return A {@link UserDTO} representing the found user.
     */
    UserDTO findById(Long userId);

    /**
     * Finds a user by their username.
     *
     * @param username The username of the user to find.
     * @return A {@link UserDTO} representing the found user.
     */
    UserDTO findByUsername(String username);

    /**
     * Updates the email of a user.
     *
     * @param userId   The ID of the user whose email is to be updated.
     * @param newEmail The new email to be assigned to the user.
     * @return A {@link UserDTO} representing the updated user.
     */
    UserDTO updateUserEmail(Long userId, String newEmail);

    /**
     * Updates the username of a user.
     *
     * @param userId      The ID of the user whose username is to be updated.
     * @param newUsername The new username to be assigned to the user.
     * @return A {@link UserDTO} representing the updated user.
     */
    UserDTO updateUsername(Long userId, String newUsername);

    /**
     * Updates the role of a user.
     *
     * @param userId  The ID of the user whose role is to be updated.
     * @param newRole The new role to be assigned to the user.
     * @return A {@link UserDTO} representing the updated user.
     */
    UserDTO updateUserRole(Long userId, RoleEnum newRole);

    /**
     * Deletes a user by their ID.
     *
     * @param id The ID of the user to be deleted.
     * @return A {@link ResponseEntity} indicating the result of the deletion operation.
     */
    ResponseEntity<?> deleteById(Long id);

    /**
     * Retrieves the ID of a user by their username.
     *
     * @param username The username of the user whose ID is to be retrieved.
     * @return A string representing the user's ID.
     */
    String userId(String username);

    /**
     * Saves an avatar for a user.
     *
     * @param userId     The ID of the user whose avatar is to be saved.
     * @param avatarFile The {@link MultipartFile} containing the avatar image.
     * @return A {@link ResponseEntity} indicating the result of the save operation.
     */
    ResponseEntity<?> saveUserAvatar(Long userId, MultipartFile avatarFile);
}
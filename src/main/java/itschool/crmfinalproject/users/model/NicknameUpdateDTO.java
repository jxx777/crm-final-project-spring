package itschool.crmfinalproject.users.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO for updating a user's nickname.
 */
@Setter
@Getter
public class NicknameUpdateDTO {
    @Schema(description = "New nickname for the user") private String nickname;
}

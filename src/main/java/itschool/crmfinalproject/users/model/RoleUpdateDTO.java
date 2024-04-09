package itschool.crmfinalproject.users.model;

import io.swagger.v3.oas.annotations.media.Schema;
import itschool.crmfinalproject.users.enums.RoleEnum;

/**
 * DTO for updating a user's role.
 */
public record RoleUpdateDTO(
        @Schema(description = "New role for the user") RoleEnum role
) {
}

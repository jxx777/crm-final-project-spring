package itschool.crmfinalproject.model.auth;

public record RequestAuthenticationDTO(
        String username,
        String email,
        String password
) {
}

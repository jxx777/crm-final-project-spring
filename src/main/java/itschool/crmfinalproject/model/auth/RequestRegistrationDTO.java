package itschool.crmfinalproject.model.auth;

public record RequestRegistrationDTO(
        String username,
        String email,
        String password,
        String confirmPassword,
        String firstName,
        String lastName,
        String phoneNumber
) {
}
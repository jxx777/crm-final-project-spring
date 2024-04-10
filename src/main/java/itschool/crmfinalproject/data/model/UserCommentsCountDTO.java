package itschool.crmfinalproject.data.model;

/**
 * Represents the count of comments made by a user, identifying the most active users.
 */
public record UserCommentsCountDTO(String author, Integer count) {
}

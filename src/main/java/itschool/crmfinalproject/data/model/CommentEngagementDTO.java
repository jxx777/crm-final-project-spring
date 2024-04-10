package itschool.crmfinalproject.data.model;

/**
 * Represents the engagement data for a single comment, including likes and replies count.
 */
public record CommentEngagementDTO(String commentId, Integer likesCount, Integer repliesCount) {
}

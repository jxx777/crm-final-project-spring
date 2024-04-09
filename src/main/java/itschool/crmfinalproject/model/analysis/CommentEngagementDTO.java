package itschool.crmfinalproject.model.analysis;

/**
 * Represents the engagement data for a single comment, including likes and replies count.
 */
public record CommentEngagementDTO(String commentId, Integer likesCount, Integer repliesCount) {
}

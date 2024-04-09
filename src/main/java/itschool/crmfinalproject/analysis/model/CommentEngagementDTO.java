package itschool.crmfinalproject.analysis.model;

/**
 * Represents the engagement data for a single comment, including likes and replies count.
 */
public record CommentEngagementDTO(String commentId, Integer likesCount, Integer repliesCount) {
}

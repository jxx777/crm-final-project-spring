package itschool.crmfinalproject.model.analysis;

import java.util.List;

/**
 * Represents the distribution of comments across different length categories.
 */
public record CommentLengthDistributionDTO(String lengthCategory, Integer count, List<String> comments) {
}

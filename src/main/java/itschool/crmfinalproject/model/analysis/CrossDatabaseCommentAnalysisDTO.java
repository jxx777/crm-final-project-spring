package itschool.crmfinalproject.model.analysis;

/**
 * Analysis data that crosses database boundaries, often combining information from different sources.
 */
public record CrossDatabaseCommentAnalysisDTO(
        String entityId,
        String entityName,
        long commentCount
) {
}

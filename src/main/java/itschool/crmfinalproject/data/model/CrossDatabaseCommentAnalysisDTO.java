package itschool.crmfinalproject.data.model;

/**
 * Analysis data that crosses database boundaries, often combining information from different sources.
 */
public record CrossDatabaseCommentAnalysisDTO(
        String entityId,
        String entityName,
        long commentCount
) {
}

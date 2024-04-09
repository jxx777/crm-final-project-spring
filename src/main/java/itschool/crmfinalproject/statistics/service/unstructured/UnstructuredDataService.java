package itschool.crmfinalproject.statistics.service.unstructured;

import itschool.crmfinalproject.analysis.model.*;

import java.util.List;

public interface UnstructuredDataService {

    List<CategoryCommentsCountDTO> countCommentsPerEventCategory();

    List<UserCommentsCountDTO> findTopActiveUsers();

    List<EventPopularityDTO> findEventPopularityOverTime();

    List<CommentEngagementDTO> analyzeCommentEngagement();

    List<CommentLengthDistributionDTO> analyzeCommentLengthDistribution();
}
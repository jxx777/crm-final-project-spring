package itschool.crmfinalproject.service.data.unstructured;


import itschool.crmfinalproject.model.analysis.*;

import java.util.List;

public interface UnstructuredDataService {

    List<CategoryCommentsCountDTO> countCommentsPerEventCategory();

    List<UserCommentsCountDTO> findTopActiveUsers();

    List<EventPopularityDTO> findEventPopularityOverTime();

    List<CommentEngagementDTO> analyzeCommentEngagement();

    List<CommentLengthDistributionDTO> analyzeCommentLengthDistribution();
}
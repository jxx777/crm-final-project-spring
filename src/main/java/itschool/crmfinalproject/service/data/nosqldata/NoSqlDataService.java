package itschool.crmfinalproject.service.data.nosqldata;


import itschool.crmfinalproject.model.analysis.*;

import java.util.List;

public interface NoSqlDataService {

    List<CategoryCommentsCountDTO> countCommentsPerEventCategory();

    List<UserCommentsCountDTO> findTopActiveUsers();

    List<EventPopularityDTO> findEventPopularityOverTime();

    List<CommentEngagementDTO> analyzeCommentEngagement();

    List<CommentLengthDistributionDTO> analyzeCommentLengthDistribution();
}
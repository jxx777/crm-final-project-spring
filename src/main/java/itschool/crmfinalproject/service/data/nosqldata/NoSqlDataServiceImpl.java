package itschool.crmfinalproject.service.data.nosqldata;
import itschool.crmfinalproject.model.analysis.*;
import itschool.crmfinalproject.repository.NoSqlDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoSqlDataServiceImpl implements NoSqlDataService {

    private final NoSqlDataRepository noSqlDataRepository;

    @Override
    public List<CategoryCommentsCountDTO> countCommentsPerEventCategory() {
        return noSqlDataRepository.countCommentsPerEventCategory();
    }

    @Override
    public List<UserCommentsCountDTO> findTopActiveUsers() {
        return noSqlDataRepository.findTopActiveUsers();
    }

    @Override
    public List<EventPopularityDTO> findEventPopularityOverTime() {
        return noSqlDataRepository.findEventPopularityOverTime();
    }

    @Override
    public List<CommentEngagementDTO> analyzeCommentEngagement() {
        return noSqlDataRepository.analyzeCommentEngagement();
    }

    @Override
    public List<CommentLengthDistributionDTO> analyzeCommentLengthDistribution() {
        return noSqlDataRepository.analyzeCommentLengthDistribution();
    }
}
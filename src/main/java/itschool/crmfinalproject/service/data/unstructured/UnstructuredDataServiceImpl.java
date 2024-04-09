package itschool.crmfinalproject.service.data.unstructured;

import itschool.crmfinalproject.model.analysis.*;
import itschool.crmfinalproject.repository.UnstructuredDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UnstructuredDataServiceImpl implements UnstructuredDataService {

    private final UnstructuredDataRepository unstructuredDataRepository;

    @Override
    public List<CategoryCommentsCountDTO> countCommentsPerEventCategory() {
        return unstructuredDataRepository.countCommentsPerEventCategory();
    }

    @Override
    public List<UserCommentsCountDTO> findTopActiveUsers() {
        return unstructuredDataRepository.findTopActiveUsers();
    }

    @Override
    public List<EventPopularityDTO> findEventPopularityOverTime() {
        return unstructuredDataRepository.findEventPopularityOverTime();
    }

    @Override
    public List<CommentEngagementDTO> analyzeCommentEngagement() {
        return unstructuredDataRepository.analyzeCommentEngagement();
    }

    @Override
    public List<CommentLengthDistributionDTO> analyzeCommentLengthDistribution() {
        return unstructuredDataRepository.analyzeCommentLengthDistribution();
    }
}
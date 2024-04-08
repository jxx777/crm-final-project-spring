package itschool.crmfinalproject.repository;

import itschool.crmfinalproject.entity.app.event.Comment;
import itschool.crmfinalproject.model.analysis.*;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NoSqlDataRepository extends MongoRepository<Comment, String> {

    // Count of Comments Per Event Category
    @Aggregation(pipeline = {
            "{$lookup: {from: 'events', localField: 'eventId', foreignField: 'id', as: 'event'}}",
            "{$unwind: '$event'}",
            "{$group: {_id: '$event.eventCategory', count: {$sum: 1}}}",
            "{$project: {category: '$_id', count: 1}}"
    })
    List<CategoryCommentsCountDTO> countCommentsPerEventCategory();

    // Top Active Users by Comments
    @Aggregation(pipeline = {
            "{$group: {_id: '$author', count: {$sum: 1}}}",
            "{$sort: {count: -1}}",
            "{$limit: 10}",
            "{$project: {author: '$_id', count: 1}}"
    })
    List<UserCommentsCountDTO> findTopActiveUsers();

    // Event Popularity Over Time
    @Aggregation(pipeline = {
            "{$lookup: {from: 'events', localField: 'eventId', foreignField: 'id', as: 'event'}}",
            "{$unwind: '$event'}",
            "{$group: {_id: {event: '$eventId', month: {$month: '$timestamp'}, year: {$year: '$timestamp'}}, count: {$sum: 1}}}",
            "{$sort: {'_id.year': 1, '_id.month': 1}}",
            "{$project: {eventId: '$_id.event', month: '$_id.month', year: '$_id.year', count: 1}}"
    })
    List<EventPopularityDTO> findEventPopularityOverTime();

    // Comment Engagement Analysis
    @Aggregation(pipeline = {
            "{$project: {commentId: '$_id', likesCount: {$size: '$likes'}, repliesCount: {$size: '$replies'}}}",
            "{$sort: {likesCount: -1, repliesCount: -1}}"
    })
    List<CommentEngagementDTO> analyzeCommentEngagement();

    // Distribution of Comment Lengths
    @Aggregation(pipeline = {
            "{$project: {length: {$strLenCP: '$text'}, text: 1}}",
            "{$bucket: {groupBy: '$length', boundaries: [0, 50, 100, 150, 200, Infinity], default: 'Unknown', output: {count: {$sum: 1}, comments: {$push: '$text'}}}}"
    })
    List<CommentLengthDistributionDTO> analyzeCommentLengthDistribution();
}
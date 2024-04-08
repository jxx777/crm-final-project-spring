package itschool.crmfinalproject.mapper;

import itschool.crmfinalproject.entity.app.event.Comment;
import itschool.crmfinalproject.model.comment.CommentDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentDTO toCommentDTO(Comment comment);
    Comment toComment(CommentDTO commentDTO);
}
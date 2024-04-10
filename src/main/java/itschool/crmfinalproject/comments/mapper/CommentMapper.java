package itschool.crmfinalproject.comments.mapper;

import itschool.crmfinalproject.comments.document.Comment;
import itschool.crmfinalproject.comments.model.CommentDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentDTO toCommentDTO(Comment comment);

    Comment toComment(CommentDTO commentDTO);
}
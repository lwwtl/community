package life.rlw.community.mapper;

import life.rlw.community.model.Comment;
import life.rlw.community.model.CommentExample;
import life.rlw.community.model.Question;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CommentExtMapper {
    int inCommentCount(Comment comment);
}
package com.cmc.board.infrastructure.query;

import static com.cmc.board.domain.comment.CommentStatus.PUBLISHED;

import com.cmc.board.infrastructure.database.jpa.CommentJpaRepository;
import com.cmc.board.infrastructure.database.jpa.entity.CommentEntity;
import com.cmc.board.infrastructure.query.dto.CommentDto;
import com.cmc.user.application.query.UserDto;
import com.cmc.user.application.query.UserQuery;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentQueryImpl implements CommentQuery {

    private final UserQuery userQuery;
    private final CommentJpaRepository commentJpaRepository;

    @Override
    public List<CommentDto> getComments(Long postId, Long userId) {
        List<CommentEntity> comments = commentJpaRepository.findAllByPostIdAndStatusOrderByIdDesc(postId, PUBLISHED);
        List<Long> authorIds = comments.stream().map(CommentEntity::getAuthorId).toList();
        Map<Long, UserDto> users = userQuery.getUsers(authorIds);
        return comments.stream()
                .map(c -> CommentDto.of(c, users.get(c.getAuthorId()), userId))
                .toList();
    }
}

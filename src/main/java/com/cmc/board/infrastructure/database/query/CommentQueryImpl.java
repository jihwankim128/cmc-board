package com.cmc.board.infrastructure.database.query;

import com.cmc.board.infrastructure.database.jpa.CommentJpaRepository;
import com.cmc.board.presentation.query.CommentQuery;
import com.cmc.board.presentation.query.dto.CommentDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentQueryImpl implements CommentQuery {

    private final CommentJpaRepository commentJpaRepository;

    @Override
    public List<CommentDto> getComments() {
        return commentJpaRepository.findAll()
                .stream()
                .map(c -> new CommentDto(
                        c.getId(),
                        c.getPostId(),
                        c.getParentId(),
                        c.getAuthorId(),
                        c.getContent(),
                        c.getStatus(),
                        c.getCreatedAt()
                ))
                .toList();
    }
}

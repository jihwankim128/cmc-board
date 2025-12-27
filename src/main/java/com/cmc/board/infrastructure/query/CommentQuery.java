package com.cmc.board.infrastructure.query;

import com.cmc.board.infrastructure.query.dto.CommentDto;
import java.util.List;

public interface CommentQuery {

    List<CommentDto> getComments();
}

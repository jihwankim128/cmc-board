package com.cmc.board.presentation.query;

import com.cmc.board.presentation.query.dto.CommentDto;
import java.util.List;

public interface CommentQuery {

    List<CommentDto> getComments();
}

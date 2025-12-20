package com.cmc.board.application.port.out;

public interface ValidateCategoryPort {

    boolean existsById(Long categoryId);
}
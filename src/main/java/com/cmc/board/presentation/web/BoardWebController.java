package com.cmc.board.presentation.web;

import com.cmc.global.auth.annotation.AuthRole;
import com.cmc.global.auth.annotation.PreAuth;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardWebController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/categories")
    @PreAuth(value = AuthRole.ADMIN)
    public String categories() {
        return "board/categories";
    }
}

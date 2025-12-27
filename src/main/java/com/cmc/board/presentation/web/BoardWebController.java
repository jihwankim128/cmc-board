package com.cmc.board.presentation.web;

import com.cmc.global.auth.annotation.AuthRole;
import com.cmc.global.auth.annotation.PreAuth;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BoardWebController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/posts/write")
    @PreAuth
    public String posts() {
        return "board/write";
    }

    @GetMapping("/categories")
    @PreAuth(value = AuthRole.ADMIN)
    public String categories() {
        return "board/categories";
    }

    @GetMapping("/posts/{postId}")
    @PreAuth(value = AuthRole.ANONYMOUS)
    public String postDetail(@PathVariable Long postId, Model model) {
        model.addAttribute("postId", postId);
        return "board/detail";
    }

    @GetMapping("/posts")
    @PreAuth(value = AuthRole.ANONYMOUS)
    public String posts(@RequestParam(required = false) Long categoryId, Model model) {
        model.addAttribute("categoryId", categoryId);
        return "board/list";
    }

    @GetMapping("/posts/{postId}/edit")
    @PreAuth(AuthRole.USER)
    public String editForm(@PathVariable Long postId, Model model) {
        model.addAttribute("postId", postId);
        return "board/edit";
    }
}

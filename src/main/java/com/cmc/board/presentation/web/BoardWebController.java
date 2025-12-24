package com.cmc.board.presentation.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardWebController {

    @GetMapping("/")
    public String index() {
        return "index";
    }
}

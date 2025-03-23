package com.baeldung.boot.jsp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ReactAppController {

    @GetMapping("react")
    public String index() {
        return "react-app";
    }
}

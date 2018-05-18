package com.tgrajkowski.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StaticWebPageController {
    @RequestMapping("/tomek")
    public String index() {
        return "index";
    }
}

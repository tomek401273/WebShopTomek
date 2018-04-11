package com.tgrajkowski.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Controller
@CrossOrigin("*")
public class StaticWebPageController {
    @RequestMapping("tom/static")
    public String index(Map<String, Object> model) {
        model.put("variable", "My Thyemleaf variable");
        model.put("one", 1);
        model.put("two", 2);
        return "index";
    }

}

package com.project.sqlDemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WebController {

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String index(Model model) {
        return "index";
    }

}

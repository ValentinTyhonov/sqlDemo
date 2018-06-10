package com.project.sqlDemo.controller;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public class WebControllerImpl implements WebController {

    @Override
    public String index(Model model) {
        return "index";
    }

}

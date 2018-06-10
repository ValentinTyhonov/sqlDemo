package com.project.sqlDemo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/department")
public interface DepartmentController {

    @RequestMapping(path = "", method = RequestMethod.GET)
    ResponseEntity<?> getAllDepartments();

}

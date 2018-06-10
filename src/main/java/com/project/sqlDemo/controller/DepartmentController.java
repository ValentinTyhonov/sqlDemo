package com.project.sqlDemo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/department")
public interface DepartmentController {

    /**
     * This endpoint for getting all Department records from db
     *
     * @return JSON object with all Department records
     */
    @RequestMapping(path = "", method = RequestMethod.GET)
    ResponseEntity<?> getAllDepartments();

}

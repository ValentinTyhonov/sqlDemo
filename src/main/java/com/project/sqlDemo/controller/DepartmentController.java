package com.project.sqlDemo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.sqlDemo.dto.DTOUtilMapper;
import com.project.sqlDemo.dto.DepartmentDTO;
import com.project.sqlDemo.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/department")
public class DepartmentController {

    @Autowired
    private DepartmentService service;

    /**
     * This endpoint for getting all Department records from db
     *
     * @return JSON object with all Department records
     */
    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<?> getAllDepartments() {
        try {
            List<DepartmentDTO> departments = DTOUtilMapper.departmentToDTO(service.getAll());
            String responseData = new ObjectMapper().writeValueAsString(departments);
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

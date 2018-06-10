package com.project.sqlDemo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.sqlDemo.dto.DTOUtilMapper;
import com.project.sqlDemo.dto.DepartmentDTO;
import com.project.sqlDemo.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DepartmentControllerImpl implements DepartmentController {

    @Autowired
    private DepartmentService service;

    @Override
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

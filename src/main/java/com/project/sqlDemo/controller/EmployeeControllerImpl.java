package com.project.sqlDemo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.sqlDemo.dto.DTOUtilMapper;
import com.project.sqlDemo.dto.EmployeeDTO;
import com.project.sqlDemo.entity.Employee;
import com.project.sqlDemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class EmployeeControllerImpl implements EmployeeController {

    @Autowired
    private EmployeeService service;

    @Override
    public ResponseEntity<?> getEmployees(Pageable pageable) {
        try {
            String responseData = getPageableData(service.getPage(pageable));
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> searchEmployee(Pageable pageable, @RequestParam("name") String name) {
        try {
            String responseData = getPageableData(service.search(pageable, name));
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> updateEmployee(@RequestBody Employee employee) {
        try {
            if (service.getByID(employee.getId()) != null) {
                service.save(employee);
                return new ResponseEntity<>("Record was updated successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Record was not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> removeEmployee(@RequestParam("id") long id) {
        try {
            Employee employee = service.getByID(id);
            if (employee != null) {
                service.remove(employee);
                return new ResponseEntity<>("Record was removed successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Record was not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String getPageableData(Page<Employee> page) throws JsonProcessingException {
        List<EmployeeDTO> employees = DTOUtilMapper.employeeToDTO(page.getContent());
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("content", employees);
        responseData.put("pages", page.getTotalPages());
        return new ObjectMapper().writeValueAsString(responseData);
    }

}

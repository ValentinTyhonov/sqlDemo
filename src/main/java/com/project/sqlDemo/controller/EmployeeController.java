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
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    /**
     * This endpoint for getting page with Employee records from db
     *
     * @param pageable - object with information about
     *                 requested page number and page size
     * @return JSON object with Employee records and total number of pages
     */
    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<?> getEmployees(Pageable pageable) {
        try {
            String responseData = getPageableData(service.getPage(pageable));
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This endpoint for getting page with Employee records,
     * which name starts with <name>, from db
     *
     * @param pageable - object with information about
     *                 requested page number and page size
     * @param name - string that record name should start with
     * @return JSON object with Employee records,
     *              which name starts with <name>,
     *              and total number of pages
     */
    @RequestMapping(path = "/search", method = RequestMethod.GET)
    public ResponseEntity<?> searchEmployee(Pageable pageable, @RequestParam("name") String name) {
        try {
            String responseData = getPageableData(service.search(pageable, name));
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This endpoint for putting updated Employee record to db
     *
     * @param employee - updated Employee record
     * @return message with information of executing update operation
     */
    @RequestMapping(path = "", method = RequestMethod.PUT)
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

    /**
     * This endpoint for removing Employee record from db
     *
     * @param id - id of Employee record for removing from db
     * @return message with information of executing delete operation
     */
    @RequestMapping(path = "", method = RequestMethod.DELETE)
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

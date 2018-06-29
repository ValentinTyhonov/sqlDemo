package com.project.sqlDemo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.sqlDemo.entity.Employee;
import com.project.sqlDemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/employee")
public class EmployeeController {

    private static final String INCORRECT_PARAMETERS = "Incorrect parameters. Page should be not lower then 1. Size should be not lower then 0.";
    private static final String RECORD_NOT_FOUND = "Record is not found";
    private static final String RECORD_INSERTED = "Record has been inserted successfully";
    private static final String RECORD_NOT_INSERTED = "Record is not inserted";
    private static final String RECORD_UPDATED = "Record has been updated successfully";
    private static final String RECORD_REMOVED = "Record has been removed successfully";

    @Autowired
    private EmployeeService service;

    /**
     * This endpoint for getting page with Employee records from db
     *
     * @param page - current page number
     * @param size - page size
     * @return JSON object with Employee records and total number of pages
     */
    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<?> getEmployees(@RequestParam("page") int page, @RequestParam("size") int size) {
        try {
            if (page < 1 || size < 0) {
                return  new ResponseEntity<>(INCORRECT_PARAMETERS, HttpStatus.BAD_REQUEST);
            } else {
                String responseData = getPageableData(service.getPage(page, size));
                return new ResponseEntity<>(responseData, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This endpoint for getting page with Employee records,
     * which name starts with <name>, from db
     *
     * @param page - current page number
     * @param size - page size
     * @param name - string that record name should start with
     * @return JSON object with Employee records,
     *              which name starts with <name>,
     *              and total number of pages
     */
    @RequestMapping(path = "/search", method = RequestMethod.GET)
    public ResponseEntity<?> searchEmployee(@RequestParam("page") int page, @RequestParam("size") int  size, @RequestParam("name") String name) {
        try {
            if (page < 1 || size < 0) {
                return  new ResponseEntity<>(INCORRECT_PARAMETERS, HttpStatus.BAD_REQUEST);
            } else {
                String responseData = getPageableData(service.search(page, size, name));
                return new ResponseEntity<>(responseData, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This endpoint for inserting new Employee record to db
     *
     * @param employee - new Employee record
     * @return message with information of executing insert operation
     */
    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<?> createEmployee(@RequestBody Employee employee) {
        try {
            if (service.create(employee)) {
                return new ResponseEntity<>(RECORD_INSERTED, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(RECORD_NOT_INSERTED, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
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
            if (service.update(employee)) {
                return new ResponseEntity<>(RECORD_UPDATED, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(RECORD_NOT_FOUND, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
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
            if(service.remove(id)) {
                return new ResponseEntity<>(RECORD_REMOVED, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(RECORD_NOT_FOUND, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String getPageableData(List<Employee> page) throws JsonProcessingException {
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("content", page);
        responseData.put("pages", 5);
        return new ObjectMapper().writeValueAsString(responseData);
    }

}

package com.project.sqlDemo.controller;

import com.project.sqlDemo.entity.Employee;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/employee")
public interface EmployeeController {

    /**
     * This endpoint for getting page with Employee records from db
     *
     * @param pageable - object with information about
     *                 requested page number and page size
     * @return JSON object with Employee records and total number of pages
     */
    @RequestMapping(path = "", method = RequestMethod.GET)
    ResponseEntity<?> getEmployees(Pageable pageable);

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
    ResponseEntity<?> searchEmployee(Pageable pageable, @RequestParam("name") String name);

    /**
     * This endpoint for putting updated Employee record to db
     *
     * @param employee - updated Employee record
     * @return message with information of executing update operation
     */
    @RequestMapping(path = "", method = RequestMethod.PUT)
    ResponseEntity<?> updateEmployee(@RequestBody Employee employee);

    /**
     * This endpoint for removing Employee record from db
     *
     * @param id - id of Employee record for removing from db
     * @return message with information of executing delete operation
     */
    @RequestMapping(path = "", method = RequestMethod.DELETE)
    ResponseEntity<?> removeEmployee(@RequestParam("id") long id);

}

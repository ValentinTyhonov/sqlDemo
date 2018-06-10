package com.project.sqlDemo.controller;

import com.project.sqlDemo.entity.Employee;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/employee")
public interface EmployeeController {

    @RequestMapping(path = "", method = RequestMethod.GET)
    ResponseEntity<?> getEmployees(Pageable pageable);

    @RequestMapping(path = "/search", method = RequestMethod.GET)
    ResponseEntity<?> searchEmployee(Pageable pageable, @RequestParam("name") String name);

    @RequestMapping(path = "", method = RequestMethod.PUT)
    ResponseEntity<?> updateEmployee(@RequestBody Employee employee);

    @RequestMapping(path = "", method = RequestMethod.DELETE)
    ResponseEntity<?> removeEmployee(@RequestParam("id") long id);

}

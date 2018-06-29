package com.project.sqlDemo.service;

import com.project.sqlDemo.entity.Employee;

import java.util.List;

public interface EmployeeService {

    /**
     * This method is used for getting all employees from db
     *
     * @return list with all employees records
     */
    List<Employee> getAll();

    /**
     * This method is used for getting page with
     * some number of records from db
     *
     * @param page - current page number
     * @param size - page size
     * @return page with requested number of records
     */
    List<Employee> getPage(int page, int size);

    /**
     * This method is used for fetching record by id from db
     *
     * @param id - record id
     * @return fetched record from db
     */
    Employee getByID(Long id);

    /**
     * This method is used for fetching records from db,
     * which name starts with <name>
     *
     * @param page - current page number
     * @param size - page size
     * @param name - string that record name should start with
     * @return page with requested number of records
     */
    List<Employee> search(int page, int size, String name);

    /**
     * This method is used for storing new record in db
     *
     * @param employee - new employee record
     */
    boolean create(Employee employee);

    /**
     * This method is used for updating existed record in db
     *
     * @param employee - updated employee record
     */
    boolean update(Employee employee);

    /**
     * Tis method is used for removing record from db
     *
     * @param id - record id, that should be removed
     */
    boolean remove(Long id);

}

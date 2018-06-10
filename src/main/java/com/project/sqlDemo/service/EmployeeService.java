package com.project.sqlDemo.service;

import com.project.sqlDemo.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {

    /**
     * This method is used for storing new record
     * or updating existed one in db
     *
     * @param employee - new or updated record
     */
    void save(Employee employee);

    /**
     * This method is used for getting page with
     * some number of records from db
     *
     * @param pageable - object with information about
     *                 requested page number and page size
     * @return page with requested number of records
     */
    Page<Employee> getPage(Pageable pageable);

    /**
     * This method is used for fetching record by id from db
     *
     * @param id - record id
     * @return fetched record from db
     */
    Employee getByID(Long id);

    /**
     * Tis method is used for removing record from db
     *
     * @param employee - record, that should be removed
     */
    void remove(Employee employee);

    /**
     * This method is used for fetching records from db,
     * which name starts with <name>
     *
     * @param pageable - object with information about
     *                 requested page number and page size
     * @param name - string that record name should start with
     * @return page with requested number of records
     */
    Page<Employee> search(Pageable pageable, String name);

}

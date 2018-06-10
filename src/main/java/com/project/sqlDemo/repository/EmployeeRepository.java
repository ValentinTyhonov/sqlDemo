package com.project.sqlDemo.repository;

import com.project.sqlDemo.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Provides CRUD operations for Employee entity
 *
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /**
     * This method is used for getting each record from db, where name starts with <name>
     *
     * @param pageable - object with information about
     *                 requested page number and page size
     * @param name - parameter for searching by
     * @return page with employees
     */
    Page<Employee> findByNameStartingWith(Pageable pageable, String name);

}

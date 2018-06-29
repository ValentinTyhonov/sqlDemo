package com.project.sqlDemo.repository;

import com.project.sqlDemo.entity.Employee;

import java.util.List;

/**
 * Provides CRUD operations for Employee entity
 *
 */
public interface EmployeeRepository {

    List<Employee> findAll();

    List<Employee> findAll(int skip, int limit);

    Employee findById(Long id);

    List<Employee> findByName(int skip, int limit, String name);

    boolean create(Employee employee);

    boolean update(Employee employee);

    boolean delete(Long id);

}

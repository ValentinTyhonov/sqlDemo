package com.project.sqlDemo.repository;

import com.project.sqlDemo.entity.Department;

import java.util.List;

/**
 * Provides CRUD operations for Department entity
 *
 */
public interface DepartmentRepository {

    List<Department> findAll();

}

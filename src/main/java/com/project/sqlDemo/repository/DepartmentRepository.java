package com.project.sqlDemo.repository;

import com.project.sqlDemo.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Provides CRUD operations for Department entity
 *
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

}

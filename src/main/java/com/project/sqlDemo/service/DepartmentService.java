package com.project.sqlDemo.service;

import com.project.sqlDemo.entity.Department;

import java.util.List;

public interface DepartmentService {

    /**
     * This method is used for getting all departments from db
     *
     * @return list with all department records
     */
    List<Department> getAll();

}

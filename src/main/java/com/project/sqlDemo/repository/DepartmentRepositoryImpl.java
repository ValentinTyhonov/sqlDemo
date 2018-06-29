package com.project.sqlDemo.repository;

import com.project.sqlDemo.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.util.List;

@Repository
public class DepartmentRepositoryImpl implements DepartmentRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional(readOnly = true)
    public List<Department> findAll() {
        return jdbcTemplate.query("SELECT * FROM tbldepartments",
            (ResultSet resultSet, int i) -> new Department(resultSet.getLong("dpID"), resultSet.getString("dpName")));
    }

}

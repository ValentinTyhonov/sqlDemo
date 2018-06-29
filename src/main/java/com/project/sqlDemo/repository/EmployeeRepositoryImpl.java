package com.project.sqlDemo.repository;

import com.project.sqlDemo.entity.Department;
import com.project.sqlDemo.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional(readOnly = true)
    public List<Employee> findAll() {
        return jdbcTemplate.query("SELECT * FROM tblemployees", (resultSet, i) -> mapToObject(resultSet));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> findAll(int skip, int limit) {
        String sql = "SELECT * FROM tblemployees INNER JOIN tbldepartments ON department_dpid = dpID LIMIT ? OFFSET ?";
        Object[] params = { limit, skip };
        return jdbcTemplate.query(sql, params, (resultSet, i) -> mapToObject(resultSet));
    }

    @Override
    @Transactional(readOnly = true)
    public Employee findById(Long id) {
        String sql = "SELECT * FROM tblemployees INNER JOIN tbldepartments ON department_dpid = dpID WHERE empID = ?";
        Object[] params = { id };
        int[] types = { Types.BIGINT };
        return jdbcTemplate.queryForObject(sql, params, types, (resultSet, i) -> mapToObject(resultSet));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> findByName(int skip, int limit, String name) {
        String sql = "SELECT * FROM tblemployees INNER JOIN tbldepartments ON department_dpid = dpID WHERE empName LIKE ? LIMIT ? OFFSET ?";
        Object[] params = { name + "%", limit, skip };
        return jdbcTemplate.query(sql, params, (resultSet, i) -> mapToObject(resultSet));
    }

    @Override
    public boolean create(Employee employee) {
        String sql = "INSERT INTO tblemployees (empID, empName, empActive, department_dpid) VALUES (?, ?, ?, ?)";
        Object[] params = new Object[] { employee.getId(), employee.getName(), employee.isActive(), employee.getDepartment().getId() };
        int[] types = { Types.BIGINT, Types.VARCHAR, Types.BIT, Types.BIGINT };
        return jdbcTemplate.update(sql, params, types) == 1;
    }

    @Override
    public boolean update(Employee employee) {
        String sql = "UPDATE tblemployees SET empName = ?, empActive = ?, department_dpid = ? WHERE empID = ?";
        Object[] params = new Object[] { employee.getName(), employee.isActive(), employee.getDepartment().getId(), employee.getId() };
        int[] types = { Types.VARCHAR, Types.BIT, Types.BIGINT, Types.BIGINT };
        return jdbcTemplate.update(sql, params, types) == 1;
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM tblemployees WHERE empID = ?";
        Object[] params = new Object[] { id };
        int[] types = { Types.BIGINT };
        return jdbcTemplate.update(sql, params, types) == 1;
    }

    private Employee mapToObject(ResultSet resultSet) throws SQLException {
        return new Employee(
            resultSet.getLong("empID"),
            resultSet.getString("empName"),
            resultSet.getBoolean("empActive"),
            new Department(resultSet.getLong("dpID"), resultSet.getString("dpName"))
        );
    }

}

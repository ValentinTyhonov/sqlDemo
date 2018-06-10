package com.project.sqlDemo.dto;

import com.project.sqlDemo.entity.Department;
import com.project.sqlDemo.entity.Employee;

import java.util.ArrayList;
import java.util.List;

/**
 * Used for mapping objects to DTO (data transfer object)
 */
public class DTOUtilMapper {

    public static List<EmployeeDTO> employeeToDTO(List<Employee> employees) {
        List<EmployeeDTO> employeeDTOs = new ArrayList<>();
        employees.forEach(employee -> employeeDTOs.add(new EmployeeDTO(employee.getId(), employee.getName(), employee.isActive(),
            new DepartmentDTO(employee.getDepartment().getId(), employee.getDepartment().getName()))));
        return employeeDTOs;
    }

    public static List<DepartmentDTO> departmentToDTO(List<Department> departments) {
        List<DepartmentDTO> departmentDTOs = new ArrayList<>();
        departments.forEach(department -> departmentDTOs.add(new DepartmentDTO(department.getId(), department.getName())));
        return departmentDTOs;
    }

}

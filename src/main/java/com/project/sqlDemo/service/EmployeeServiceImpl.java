package com.project.sqlDemo.service;

import com.project.sqlDemo.entity.Employee;
import com.project.sqlDemo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    @Override
    public List<Employee> getPage(int page, int size) {
        return employeeRepository.findAll((page-1)*size, size);
    }

    @Override
    public Employee getByID(Long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public List<Employee> search(int page, int size, String name) {
        return employeeRepository.findByName((page-1)*size, size, name);
    }

    @Override
    public boolean create(Employee employee) {
        return employeeRepository.create(employee);
    }

    @Override
    public boolean update(Employee employee) {
        return employeeRepository.update(employee);
    }

    @Override
    public boolean remove(Long id) {
        return employeeRepository.delete(id);
    }

}

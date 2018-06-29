package com.project.sqlDemo.service;

import com.project.sqlDemo.SqlDemoApplication;
import com.project.sqlDemo.entity.Department;
import com.project.sqlDemo.entity.Employee;
import com.project.sqlDemo.repository.EmployeeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SqlDemoApplication.class)
public class EmployeeServiceTest {

    @Autowired
    private EmployeeService service;

    @MockBean
    private EmployeeRepository repository;

    private Employee employee;

    @Before
    public void before() {
        employee = new Employee(1L, "Thomas", true, new Department());
    }

    @Test
    public void testGetAll() {
        when(repository.findAll()).thenReturn(Collections.singletonList(employee));
        List<Employee> foundEmployees = service.getAll();
        verify(repository, times(1)).findAll();
        assertEquals(1, foundEmployees.size());
        assertEquals(true, foundEmployees.contains(employee));
    }

    @Test
    public void testGetPage() {
        when(repository.findAll(anyInt(), anyInt())).thenReturn(new ArrayList<>(Collections.singletonList(employee)));
        List<Employee> foundEmployees = service.getPage(1, 3);
        verify(repository, times(1)).findAll(0, 3);
        assertEquals(foundEmployees, new ArrayList<>(Collections.singletonList(employee)));
        assertEquals(1, foundEmployees.size());
        assertEquals(true, foundEmployees.contains(employee));
    }

    @Test
    public void testGetByID() {
        long id = 1L;
        when(repository.findById(id)).thenReturn(employee);
        Employee foundEmployee = service.getByID(id);
        verify(repository, times(1)).findById(id);
        assertEquals(employee, foundEmployee);
    }

    @Test
    public void testSearch() {
        String conditionForSearching = "Th";
        when(repository.findByName(anyInt(), anyInt(), anyString())).thenReturn(new ArrayList<>(Collections.singletonList(employee)));
        List<Employee> foundEmployees = service.search(1, 3, conditionForSearching);
        verify(repository, times(1)).findByName(0, 3, conditionForSearching);
        assertEquals(foundEmployees, new ArrayList<>(Collections.singletonList(employee)));
        assertEquals(1, foundEmployees.size());
        assertEquals(true, foundEmployees.contains(employee));
    }

    @Test
    public void testCreate() {
        when(repository.update(employee)).thenReturn(true);
        assertEquals(true, service.update(employee));
        verify(repository, times(1)).update(employee);
    }

    @Test
    public void testUpdate() {
        when(repository.create(employee)).thenReturn(true);
        assertEquals(true, service.create(employee));
        verify(repository, times(1)).create(employee);
    }

    @Test
    public void testRemove() {
        long id = 1L;
        when(repository.delete(id)).thenReturn(true);
        assertEquals(true, service.remove(id));
        verify(repository, times(1)).delete(id);
    }

}

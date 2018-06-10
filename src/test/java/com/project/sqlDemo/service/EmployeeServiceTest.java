package com.project.sqlDemo.service;

import com.project.sqlDemo.SqlDemoApplication;
import com.project.sqlDemo.entity.Department;
import com.project.sqlDemo.entity.Employee;
import com.project.sqlDemo.repository.EmployeeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
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

    @Mock
    private Pageable pageable;

    private Employee employee;

    @Before
    public void before() {
        employee = new Employee(1L, "Thomas", true, new Department());

        when(pageable.getPageNumber()).thenReturn(0);
        when(pageable.getPageSize()).thenReturn(10);
    }

    @Test
    public void testSave() {
        service.save(employee);
        verify(repository, times(1)).save(employee);
    }

    @Test
    public void testGetPage() {
        when(repository.findAll(pageable)).thenReturn(new PageImpl<>(Collections.singletonList(employee)));
        Page foundPage = service.getPage(pageable);
        verify(repository, times(1)).findAll(pageable);
        assertEquals(foundPage, new PageImpl<>(Collections.singletonList(employee)));
        assertEquals(foundPage.getTotalElements(), 1);
        assertEquals(foundPage.getContent().contains(employee), true);
    }

    @Test
    public void testGetByID() {
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.of(employee));
        Employee foundEmployee = service.getByID(id);
        verify(repository, times(1)).findById(id);
        assertEquals(foundEmployee, employee);
    }

    @Test
    public void testGetByIDNotFound() {
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());
        Employee foundEmployee = service.getByID(id);
        verify(repository, times(1)).findById(id);
        assertEquals(foundEmployee, null);
    }

    @Test
    public void testRemove() {
        service.remove(employee);
        verify(repository, times(1)).delete(employee);
    }

    @Test
    public void testSearch() {
        String conditionForSearching = "Th";
        when(repository.findByNameStartingWith(pageable, conditionForSearching)).thenReturn(new PageImpl<>(Collections.singletonList(employee)));
        Page foundPage = service.search(pageable, conditionForSearching);
        verify(repository, times(1)).findByNameStartingWith(pageable, conditionForSearching);
        assertEquals(foundPage, new PageImpl<>(Collections.singletonList(employee)));
        assertEquals(foundPage.getTotalElements(), 1);
        assertEquals(foundPage.getContent().contains(employee), true);
    }

}

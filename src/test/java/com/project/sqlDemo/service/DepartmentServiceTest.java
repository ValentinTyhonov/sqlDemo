package com.project.sqlDemo.service;

import com.project.sqlDemo.SqlDemoApplication;
import com.project.sqlDemo.entity.Department;
import com.project.sqlDemo.repository.DepartmentRepository;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SqlDemoApplication.class)
public class DepartmentServiceTest {

    @Autowired
    private DepartmentService service;

    @MockBean
    private DepartmentRepository repository;

    @Test
    public void testGetAll() {
        Department department = new Department(1L, "Glue", new ArrayList<>());
        when(repository.findAll()).thenReturn(Collections.singletonList(department));
        List<Department> foundDepartments = service.getAll();
        verify(repository, times(1)).findAll();
        assertEquals(foundDepartments.size(), 1);
        assertEquals(foundDepartments.contains(department), true);
    }

}

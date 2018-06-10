package com.project.sqlDemo.repository;

import com.project.sqlDemo.entity.Department;
import com.project.sqlDemo.entity.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void whenFindByName_thenReturnEmployee() {
        // given
        setDepartment();
        setEmployee(1L, "Thomas");
        setEmployee(2L, "Magnus");

        // when
        Page result = employeeRepository.findByNameStartingWith(Pageable.unpaged(), "Th");

        // then
        assertEquals(result.getContent().size(), 1);
        assertEquals(((Employee) result.getContent().get(0)).getName(), "Thomas");
    }

    private void setDepartment() {
        Department department = new Department(1L, "Glue", new ArrayList<>());
        entityManager.merge(department);
        entityManager.flush();
    }

    private void setEmployee(Long id, String name) {
        Employee employee = new Employee(id, name, true, new Department(1L, "Glue", new ArrayList<>()));
        entityManager.merge(employee);
        entityManager.flush();
    }

}

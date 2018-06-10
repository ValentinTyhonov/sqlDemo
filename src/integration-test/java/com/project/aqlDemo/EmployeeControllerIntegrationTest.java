package com.project.aqlDemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.project.sqlDemo.SqlDemoApplication;
import com.project.sqlDemo.entity.Department;
import com.project.sqlDemo.entity.Employee;
import com.project.sqlDemo.repository.DepartmentRepository;
import com.project.sqlDemo.repository.EmployeeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = SqlDemoApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
public class EmployeeControllerIntegrationTest {

    private static final String ID = "id";
    private static final String PAGE = "page";
    private static final String PAGE_VAL = "0";;
    private static final String SIZE = "size";
    private static final String SIZE_VAL = "3";
    private static final String NAME = "name";
    private static final String NAME_VAL = "de";

    private static final String GET_EMPLOYEES = "src/integration-test/resources/testData/employeesGet.json";
    private static final String SEARCH_EMPLOYEES = "src/integration-test/resources/testData/employeesSearch.json";
    private static final String UPDATED_EMPLOYEE = "src/integration-test/resources/testData/employeeUpdated.json";


    @Autowired
    private MockMvc mvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Before
    public void before () {
        Department someDepartment = new Department(1L, "someDepartment", new ArrayList<>());
        departmentRepository.save(someDepartment);
        employeeRepository.save(new Employee(1L, "dev_1", true, someDepartment));
        employeeRepository.save(new Employee(2L, "admin", false, someDepartment));
        employeeRepository.save(new Employee(3L, "manager", true, someDepartment));
        employeeRepository.save(new Employee(4L, "dev_2", true, someDepartment));
        employeeRepository.save(new Employee(5L, "guest", false, someDepartment));
    }

    @Test
    public void testGetEmployees() throws Exception {
        String expectedContent = new ObjectMapper().readTree(Files.readAllBytes(Paths.get(GET_EMPLOYEES))).toString();
        mvc.perform(MockMvcRequestBuilders.get("/employee")
            .param(PAGE, PAGE_VAL)
            .param(SIZE, SIZE_VAL)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(expectedContent))
            .andDo(print())
            .andReturn();
    }

    @Test
    public void testSearchEmployees() throws Exception {
        String expectedContent = new ObjectMapper().readTree(Files.readAllBytes(Paths.get(SEARCH_EMPLOYEES))).toString();
        mvc.perform(MockMvcRequestBuilders.get("/employee/search")
            .param(PAGE, PAGE_VAL)
            .param(SIZE, SIZE_VAL)
            .param(NAME, NAME_VAL)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(expectedContent))
            .andDo(print())
            .andReturn();
    }

    @Test
    public void testUpdateEmployee() throws Exception {
        String content = new ObjectMapper().readTree(Files.readAllBytes(Paths.get(UPDATED_EMPLOYEE))).toString();
        mvc.perform(MockMvcRequestBuilders.put("/employee")
            .contentType(MediaType.APPLICATION_JSON)
            .content(content))
            .andExpect(status().isOk())
            .andExpect(content().string("Record was updated successfully"))
            .andDo(print())
            .andReturn();
    }

    @Test
    public void testUpdateEmployeeNotFound() throws Exception {
        String content = ((ObjectNode) new ObjectMapper().readTree(Files.readAllBytes(Paths.get(UPDATED_EMPLOYEE)))).put("id", 100L).toString();
        mvc.perform(MockMvcRequestBuilders.put("/employee")
            .contentType(MediaType.APPLICATION_JSON)
            .content(content))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Record was not found"))
            .andDo(print())
            .andReturn();
    }

    @Test
    public void testRemoveEmployee() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/employee")
            .param(ID, "5")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("Record was removed successfully"))
            .andDo(print())
            .andReturn();
    }

    @Test
    public void testRemoveEmployeeNotFound() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/employee")
            .param(ID, "500")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Record was not found"))
            .andDo(print())
            .andReturn();
    }

}

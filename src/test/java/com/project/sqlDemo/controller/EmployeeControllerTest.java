package com.project.sqlDemo.controller;

import com.project.sqlDemo.entity.Department;
import com.project.sqlDemo.entity.Employee;
import com.project.sqlDemo.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    private static final String INCORRECT_PARAMETERS = "Incorrect parameters. Page should be not lower then 1. Size should be not lower then 0.";
    private static final String RECORD_NOT_FOUND = "Record is not found";
    private static final String RECORD_INSERTED = "Record has been inserted successfully";
    private static final String RECORD_NOT_INSERTED = "Record is not inserted";
    private static final String RECORD_UPDATED = "Record has been updated successfully";
    private static final String RECORD_REMOVED = "Record has been removed successfully";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private EmployeeController controller;

    @MockBean
    private EmployeeService service;

    private Employee employee;

    @Before
    public void before() {
        employee = new Employee(1L, "Thomas", true, new Department(1L, "Glue"));
    }

    @Test
    public void testGetEmployees() throws Exception {
        when(service.getPage(anyInt(), anyInt())).thenReturn(new ArrayList<>(Collections.singletonList(employee)));
        String expectedContent = "{\"pages\":1,\"content\":[{\"id\":1,\"name\":\"Thomas\",\"active\":true,\"department\":{\"id\":1,\"name\":\"Glue\"}}]}";

        mvc.perform(MockMvcRequestBuilders.get("/employee")
            .param("page", "1")
            .param("size", "10")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(expectedContent))
            .andDo(print())
            .andReturn();
    }

    @Test
    public void testGetEmployeesIncorrectParam() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/employee")
            .param("page", "0")
            .param("size", "10")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(content().string(INCORRECT_PARAMETERS))
            .andDo(print())
            .andReturn();
    }

    @Test
    public void testSearchEmployees() throws Exception {
        when(service.search(anyInt(), anyInt(), eq("Th"))).thenReturn(new ArrayList<>(Collections.singletonList(employee)));
        String expectedContent = "{\"pages\":1,\"content\":[{\"id\":1,\"name\":\"Thomas\",\"active\":true,\"department\":{\"id\":1,\"name\":\"Glue\"}}]}";

        mvc.perform(MockMvcRequestBuilders.get("/employee/search")
            .param("page", "1")
            .param("size", "10")
            .param("name", "Th")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(expectedContent))
            .andReturn();
    }

    @Test
    public void testSearchEmployeesIncorrectParam() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/employee/search")
            .param("page", "1")
            .param("size", "-1")
            .param("name", "Th")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(content().string(INCORRECT_PARAMETERS))
            .andReturn();
    }

    @Test
    public void testCreateEmployee() throws Exception {
        when(service.create(any(Employee.class))).thenReturn(true);
        String content = "{\"id\":1,\"name\":\"Thomas\", \"active\":false, \"department\":{\"id\":1, \"name\":\"Glue\"}}";

        mvc.perform(MockMvcRequestBuilders.post("/employee")
            .contentType(MediaType.APPLICATION_JSON)
            .content(content))
            .andExpect(status().isOk())
            .andExpect(content().string(RECORD_INSERTED))
            .andReturn();
    }

    @Test
    public void testCreateEmployeeAlreadyExists() throws Exception {
        when(service.update(any(Employee.class))).thenReturn(false);
        String content = "{\"id\":1,\"name\":\"Thomas\", \"active\":false, \"department\":{\"id\":1, \"name\":\"Glue\"}}";

        mvc.perform(MockMvcRequestBuilders.post("/employee")
            .contentType(MediaType.APPLICATION_JSON)
            .content(content))
            .andExpect(status().isBadRequest())
            .andExpect(content().string(RECORD_NOT_INSERTED))
            .andReturn();
    }

    @Test
    public void testUpdateEmployee() throws Exception {
        when(service.update(any(Employee.class))).thenReturn(true);
        String content = "{\"id\":1,\"name\":\"Thomas\", \"active\":false, \"department\":{\"id\":1, \"name\":\"Glue\"}}";

        mvc.perform(MockMvcRequestBuilders.put("/employee")
            .contentType(MediaType.APPLICATION_JSON)
            .content(content))
            .andExpect(status().isOk())
            .andExpect(content().string(RECORD_UPDATED))
            .andReturn();
    }

    @Test
    public void testUpdateEmployeeNotFound() throws Exception {
        when(service.update(any(Employee.class))).thenReturn(false);
        String content = "{\"id\":1,\"name\":\"Thomas\", \"active\":false, \"department\":{\"id\":1, \"name\":\"Glue\"}}";

        mvc.perform(MockMvcRequestBuilders.put("/employee")
            .contentType(MediaType.APPLICATION_JSON)
            .content(content))
            .andExpect(status().isNotFound())
            .andExpect(content().string(RECORD_NOT_FOUND))
            .andReturn();
    }

    @Test
    public void testRemoveEmployee() throws Exception {
        when(service.remove(1L)).thenReturn(true);

        mvc.perform(MockMvcRequestBuilders.delete("/employee?id=1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(RECORD_REMOVED))
            .andReturn();
    }

    @Test
    public void testRemoveEmployeeNotFound() throws Exception {
        when(service.remove(1L)).thenReturn(false);

        mvc.perform(MockMvcRequestBuilders.delete("/employee?id=1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(content().string(RECORD_NOT_FOUND))
            .andReturn();
    }

}

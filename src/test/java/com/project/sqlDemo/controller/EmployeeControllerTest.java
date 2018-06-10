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
import org.springframework.data.domain.*;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
@EnableSpringDataWebSupport
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private EmployeeController controller;

    @MockBean
    private EmployeeService service;

    private Employee employee;

    @Before
    public void before() {
        employee = new Employee(1L, "Thomas", true, new Department(1L, "Glue", new ArrayList<>()));
    }

    @Test
    public void testGetEmployees() throws Exception {
        when(service.getPage(isA(Pageable.class))).thenReturn(new PageImpl<>(Collections.singletonList(employee)));
        String expectedContent = "{\"pages\":1,\"content\":[{\"id\":1,\"name\":\"Thomas\",\"active\":true,\"department\":{\"id\":1,\"name\":\"Glue\"}}]}";

        mvc.perform(MockMvcRequestBuilders.get("/employee")
            .param("page", "0")
            .param("size", "10")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(expectedContent))
            .andDo(print())
            .andReturn();
    }

    @Test
    public void testSearchEmployees() throws Exception {
        when(service.search(isA(Pageable.class), eq("Th"))).thenReturn(new PageImpl<>(Collections.singletonList(employee)));
        String expectedContent = "{\"pages\":1,\"content\":[{\"id\":1,\"name\":\"Thomas\",\"active\":true,\"department\":{\"id\":1,\"name\":\"Glue\"}}]}";

        mvc.perform(MockMvcRequestBuilders.get("/employee/search")
            .param("page", "0")
            .param("size", "10")
            .param("name", "Th")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(expectedContent))
            .andReturn();
    }

    @Test
    public void testUpdateEmployee() throws Exception {
        when(service.getByID(1L)).thenReturn(employee);
        String content = "{\"id\":1,\"name\":\"Thomas\", \"active\":false, \"department\":{\"id\":1, \"name\":\"Glue\"}}";

        mvc.perform(MockMvcRequestBuilders.put("/employee")
            .contentType(MediaType.APPLICATION_JSON)
            .content(content))
            .andExpect(status().isOk())
            .andExpect(content().string("Record was updated successfully"))
            .andReturn();
    }

    @Test
    public void testUpdateEmployeeNotFound() throws Exception {
        when(service.getByID(1L)).thenReturn(null);
        String content = "{\"id\":1,\"name\":\"Thomas\", \"active\":false, \"department\":{\"id\":1, \"name\":\"Glue\"}}";

        mvc.perform(MockMvcRequestBuilders.put("/employee")
            .contentType(MediaType.APPLICATION_JSON)
            .content(content))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Record was not found"))
            .andReturn();
    }

    @Test
    public void testRemoveEmployee() throws Exception {
        when(service.getByID(1L)).thenReturn(employee);

        mvc.perform(MockMvcRequestBuilders.delete("/employee?id=1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("Record was removed successfully"))
            .andReturn();
    }

    @Test
    public void testRemoveEmployeeNotFound() throws Exception {
        when(service.getByID(1L)).thenReturn(null);

        mvc.perform(MockMvcRequestBuilders.delete("/employee?id=1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Record was not found"))
            .andReturn();
    }

}

package com.project.sqlDemo.controller;

import com.project.sqlDemo.entity.Department;
import com.project.sqlDemo.service.DepartmentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(DepartmentController.class)
public class DepartmentControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DepartmentService service;

    @Test
    public void testGetAllDepartments() throws Exception {
        Department department = new Department(1L, "Glue");
        when(service.getAll()).thenReturn(Collections.singletonList(department));
        String expectedContent = "[{\"id\":1,\"name\":\"Glue\"}]";

        mvc.perform(MockMvcRequestBuilders.get("/department")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(expectedContent))
            .andReturn();
    }

}

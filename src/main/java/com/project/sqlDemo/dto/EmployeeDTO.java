package com.project.sqlDemo.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeDTO {

    private Long id;
    private String name;
    private boolean active;
    private DepartmentDTO department;

}

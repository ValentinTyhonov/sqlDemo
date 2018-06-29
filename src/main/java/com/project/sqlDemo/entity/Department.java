package com.project.sqlDemo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.List;

/**
 * Used as entity for tbldepartments table
 *
 */
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
public class Department {

    @NonNull
    private Long id;

    @NonNull
    private String name;

    @ToString.Exclude
    @JsonIgnore
    private List<Employee> employees;

}

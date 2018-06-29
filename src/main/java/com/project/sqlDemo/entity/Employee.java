package com.project.sqlDemo.entity;

import lombok.*;

/**
 * Used as entity for tblemployees table
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Employee {

    private Long id;

    private String name;

    private boolean active;

    private Department department;

}

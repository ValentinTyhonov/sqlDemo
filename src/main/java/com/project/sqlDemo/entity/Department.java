package com.project.sqlDemo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Used as entity for tbldepartments table
 *
 */
@Entity
@Table(name = "tbldepartments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "dpID")
    private Long id;

    @Column(name = "dpName")
    private String name;

    @OneToMany(mappedBy = "department")
    private List<Employee> employees;

}

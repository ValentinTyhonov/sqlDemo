package com.project.sqlDemo.entity;

import lombok.*;

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
@ToString
public class Department {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "dpID")
    private Long id;

    @Column(name = "dpName")
    private String name;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Employee> employees;

}

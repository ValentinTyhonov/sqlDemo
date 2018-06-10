package com.project.sqlDemo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Used as entity for tblemployees table
 *
 */
@Entity
@Table(name = "tblemployees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "empID")
    private Long id;

    @Column(name = "empName")
    private String name;

    @Column(name = "empActive")
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "department_dpid")
    private Department department;

}

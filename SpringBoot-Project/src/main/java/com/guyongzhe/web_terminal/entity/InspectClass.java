package com.guyongzhe.web_terminal.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "inspect_class")
public class InspectClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "class_name")
    private String className;
}

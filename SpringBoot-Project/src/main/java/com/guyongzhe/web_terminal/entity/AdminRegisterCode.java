package com.guyongzhe.web_terminal.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "admin_register_code")
public class AdminRegisterCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "content")
    private String content;
}

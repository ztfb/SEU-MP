package com.guyongzhe.web_terminal.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "admin_information")
public class AdminInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "password")
    private String password;
    @Column(name = "nick_name")
    private String nickName;
}

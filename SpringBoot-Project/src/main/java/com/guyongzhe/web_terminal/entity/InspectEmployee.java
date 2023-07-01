package com.guyongzhe.web_terminal.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "inspect_employee")
public class InspectEmployee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "password")
    private String password;
    @Column(name = "job_number")
    private String jobNumber;
    @Column(name = "name")
    private String name;
    @Column(name = "sex")
    private String sex;
    @Column(name = "age")
    private int age;
    @Column(name = "job")
    private String job;
    @Column(name = "tel")
    private String tel;
    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @Column(name = "inspect_number")
    private int inspectNumber;
    @Column(name = "inspect_finish_number")
    private int inspectFinishNumber;
    @Column(name = "exception_found_number")
    private int exceptionFoundNumber;
    @Column(name = "inspect_class_name")
    private String className;
    @Column(name = "inspect_class_id")
    private int inspectClassId;
}

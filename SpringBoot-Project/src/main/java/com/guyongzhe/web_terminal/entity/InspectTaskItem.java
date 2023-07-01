package com.guyongzhe.web_terminal.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Entity
@Data
@Table(name = "inspect_task_item")
public class InspectTaskItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "start_time")
    @JsonFormat(pattern = "HH:mm:ss")
    private Time startTime;
    @Column(name = "end_time")
    @JsonFormat(pattern = "HH:mm:ss")
    private Time endTime;
    @Column(name = "inspect_employee_name")
    private String inspectEmployeeName;
    @Column(name = "inspect_employee_number")
    private String inspectEmployeeNumber;
    @Column(name = "inspect_class_name")
    private String inspectClassName;
    @Column(name = "inspect_route_name")
    private String inspectRouteName;
    @Column(name = "inspect_task_id")
    private int inspectTaskId;
    @Column(name = "inspect_employee_id")
    private int inspectEmployeeId;
    @Column(name = "finish")
    private int finish;
}

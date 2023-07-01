package com.guyongzhe.web_terminal.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "inspect_point_abnormal_log")
public class InspectPointAbnormalLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "report_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date reportTime;
    @Column(name = "description")
    private String description;
    @Column(name = "picture_path")
    private String picturePath;
    @Column(name = "status")
    private String status;
    @Column(name = "inspect_point_id")
    private int inspectPointId;
    @Column(name = "inspect_employee_id")
    private int inspectEmployeeId;
}

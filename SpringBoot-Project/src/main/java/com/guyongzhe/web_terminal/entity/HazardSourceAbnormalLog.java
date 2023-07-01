package com.guyongzhe.web_terminal.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "hazard_source_abnormal_log")
public class HazardSourceAbnormalLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "report_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date reportTime;
    @Column(name = "exception_type")
    private String exceptionType;
    @Column(name = "exception_data")
    private String exceptionData;
    @Column(name = "exception_description")
    private String exceptionDescription;
    @Column(name = "status")
    private String status;
    @Column(name = "hazard_source_id")
    private int hazardSourceId;
}

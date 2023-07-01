package com.guyongzhe.web_terminal.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "hazard_source")
public class HazardSource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "type")
    private String type;
    @Column(name = "number")
    private String number;
    @Column(name = "area")
    private String area;
    @Column(name = "longitude")
    private double longitude;
    @Column(name = "latitude")
    private double latitude;
    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @Column(name = "abnormal_number")
    private int abnormalNumber;
    @Column(name = "state")
    private int state;
}

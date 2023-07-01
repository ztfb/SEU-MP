package com.guyongzhe.web_terminal.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "statistics_data")
public class StatisticsData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "inspect_number")
    private int inspectNumber;
    @Column(name = "inspect_point_abnormal_number")
    private int inspectPointAbnormalNumber;
    @Column(name = "hazard_source_number")
    private int hazardSourceNumber;
    @Column(name = "device_number")
    private int deviceNumber;
    @Column(name = "device_normal_number")
    private int deviceNormalNumber;
}

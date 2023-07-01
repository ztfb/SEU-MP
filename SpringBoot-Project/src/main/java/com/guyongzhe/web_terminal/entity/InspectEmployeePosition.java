package com.guyongzhe.web_terminal.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "inspect_employee_position")
public class InspectEmployeePosition {
    @Id
    private int id;
    @Column(name = "longitude")
    private double longitude;
    @Column(name = "latitude")
    private double latitude;
    @Column(name = "inspect_employee_id")
    private int inspectEmployeeId;
}

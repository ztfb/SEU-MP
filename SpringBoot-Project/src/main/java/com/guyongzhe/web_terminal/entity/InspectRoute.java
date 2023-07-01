package com.guyongzhe.web_terminal.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "inspect_route")
public class InspectRoute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "route_name")
    private String routeName;
    @Column(name = "inspect_point_number")
    private int inspectPointNumber;
}

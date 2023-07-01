package com.guyongzhe.web_terminal.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "inspect_task")
public class InspectTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "inspect_class_name")
    private String inspectClassName;
    @Column(name = "inspect_route_name")
    private String inspectRouteName;
    @Column(name = "finish")
    private int finish;
    @Column(name = "inspect_route_id")
    private int inspectRouteId;
    @Column(name = "inspect_class_id")
    private int inspectClassId;
}

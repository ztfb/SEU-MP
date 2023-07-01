package com.guyongzhe.web_terminal.controller;

import com.guyongzhe.web_terminal.common.Result;
import com.guyongzhe.web_terminal.entity.*;
import com.guyongzhe.web_terminal.service.InspectPointInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/pointinfo")
@CrossOrigin
public class InspectPointInformationController {
    @Autowired
    InspectPointInformationService inspectPointInformationService;
    @GetMapping("/findLogs")
    public List<InspectPointAbnormalLog> findLogs(Integer pid, Integer eid, String status){
        return inspectPointInformationService.findLogs(pid,eid,status);
    }
    @GetMapping("/findAll")
    public List<InspectPointAbnormalLog> findAll(){
        return inspectPointInformationService.findAll();
    }
    @GetMapping("/updateResult")
    public Result updateResult(Integer id,String status){
        return inspectPointInformationService.updateResult(id,status);
    }
}

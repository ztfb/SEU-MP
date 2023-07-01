package com.guyongzhe.web_terminal.controller;

import com.guyongzhe.web_terminal.common.Result;
import com.guyongzhe.web_terminal.entity.HazardSourceAbnormalLog;
import com.guyongzhe.web_terminal.service.HazardSourceInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/hazardinfo")
@CrossOrigin
public class HazardSourceInformationController {
    @Autowired
    HazardSourceInformationService hazardSourceInformationService;
    @GetMapping("/findLogs")
    public List<HazardSourceAbnormalLog> findLogs(Integer hid,String status){
        return hazardSourceInformationService.findLogs(hid,status);
    }
    @GetMapping("/findAll")
    public List<HazardSourceAbnormalLog> findAll(){
        return hazardSourceInformationService.findAll();
    }
    @GetMapping("/updateResult")
    public Result updateResult(Integer id,String status){
        return hazardSourceInformationService.updateResult(id,status);
    }
}

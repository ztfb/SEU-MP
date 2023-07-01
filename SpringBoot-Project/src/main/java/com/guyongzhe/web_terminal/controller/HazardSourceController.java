package com.guyongzhe.web_terminal.controller;

import com.guyongzhe.web_terminal.common.Result;
import com.guyongzhe.web_terminal.entity.HazardSource;
import com.guyongzhe.web_terminal.entity.InspectEmployee;
import com.guyongzhe.web_terminal.service.HazardSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/hazard")
@CrossOrigin
public class HazardSourceController {
    @Autowired
    HazardSourceService hazardSourceService;
    @PostMapping("/add")
    public Result add(@RequestBody HazardSource hazardSource){
        return hazardSourceService.add(hazardSource);
    }//新增信息
    @DeleteMapping("/delete")
    public Result delete(Integer id){
        return hazardSourceService.delete(id);
    }//删除信息
    @PutMapping("/update")
    public Result update(@RequestBody HazardSource hazardSource){
        return hazardSourceService.update(hazardSource);
    }//更新信息
    @GetMapping("/find")
    public List<HazardSource> find(String type,String area,String number){
        return hazardSourceService.find(type,area,number);
    }//查询员工信息
    @GetMapping("/findById")
    public HazardSource findById(Integer id){
        return hazardSourceService.findById(id);
    }
    @GetMapping("/findAll")
    public List<HazardSource> findAll(){//根据班名返回员工信息
        return hazardSourceService.findAll();
    }
}

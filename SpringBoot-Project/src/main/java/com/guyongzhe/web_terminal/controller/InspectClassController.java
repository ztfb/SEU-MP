package com.guyongzhe.web_terminal.controller;

import com.guyongzhe.web_terminal.common.Result;
import com.guyongzhe.web_terminal.entity.InspectClass;
import com.guyongzhe.web_terminal.service.InspectClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/class")
@CrossOrigin
public class InspectClassController {
    @Autowired
    InspectClassService inspectClassService;
    @PostMapping("/add")
    public Result add(@RequestBody InspectClass inspectClass){
        return inspectClassService.add(inspectClass);
    }//新增信息
    @DeleteMapping("/delete")
    public Result delete(String className){
        return inspectClassService.delete(className);
    }//删除信息
    @PutMapping("/update")
    public Result update(@RequestBody InspectClass inspectClass){
        return inspectClassService.update(inspectClass);
    }//更新信息
    @GetMapping("/find")
    public InspectClass find(String className){
        return inspectClassService.find(className);
    }//查询信息
    @GetMapping("/findAll")
    public List<InspectClass> findAll(){return inspectClassService.findAll();}
}

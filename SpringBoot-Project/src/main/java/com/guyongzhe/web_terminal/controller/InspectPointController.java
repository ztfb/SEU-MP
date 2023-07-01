package com.guyongzhe.web_terminal.controller;

import com.guyongzhe.web_terminal.common.Result;
import com.guyongzhe.web_terminal.entity.InspectPoint;
import com.guyongzhe.web_terminal.service.InspectPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/point")
@CrossOrigin
public class InspectPointController {
    @Autowired
    InspectPointService inspectPointService;
    @PostMapping("/add")
    public Result add(@RequestBody InspectPoint inspectPoint){
        return inspectPointService.add(inspectPoint);
    }//新增信息
    @DeleteMapping("/delete")
    public Result delete(Integer id){
        return inspectPointService.delete(id);
    }//删除信息
    @PutMapping("/update")
    public Result update(@RequestBody InspectPoint inspectPoint){
        return inspectPointService.update(inspectPoint);
    }//更新信息
    @GetMapping("/findById")
    public InspectPoint findById(Integer id){
        return inspectPointService.findById(id);
    }
    @GetMapping("/find")
    public List<InspectPoint> find(String type, String area, String number){
        return inspectPointService.find(type,area,number);
    }//查询员工信息
    @GetMapping("/findAll")
    public List<InspectPoint> findAll(){//根据班名返回员工信息
        return inspectPointService.findAll();
    }
}

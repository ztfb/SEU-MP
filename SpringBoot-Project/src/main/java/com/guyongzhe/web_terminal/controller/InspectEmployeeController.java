package com.guyongzhe.web_terminal.controller;

import com.guyongzhe.web_terminal.common.Result;
import com.guyongzhe.web_terminal.entity.InspectEmployee;
import com.guyongzhe.web_terminal.entity.InspectEmployeePosition;
import com.guyongzhe.web_terminal.entity.InspectPoint;
import com.guyongzhe.web_terminal.service.InspectEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/employee")
@CrossOrigin
public class InspectEmployeeController {
    @Autowired
    InspectEmployeeService inspectEmployeeService;
    @PostMapping("/add")
    public Result add(@RequestBody InspectEmployee inspectEmployee){
        return inspectEmployeeService.add(inspectEmployee);
    }//新增员工信息
    @DeleteMapping("/delete")
    public Result delete(Integer id){
        return inspectEmployeeService.delete(id);
    }//删除员工信息
    @PutMapping("/update")
    public Result update(@RequestBody InspectEmployee inspectEmployee){
        return inspectEmployeeService.update(inspectEmployee);
    }//更新员工信息
    @GetMapping("/findById")
    public InspectEmployee findById(Integer id){return inspectEmployeeService.findById(id);}
    @GetMapping("/find")
    public List<InspectEmployee> find(String jobNumber,String name){
        return inspectEmployeeService.find(jobNumber,name);
    }//查询员工信息
    @GetMapping("/find2")
    public List<InspectEmployee> find2(String jobNumber,String name,String sex){
        return inspectEmployeeService.find2(jobNumber,name,sex);
    }//查询员工信息
    @GetMapping("/find3")
    public List<InspectEmployee> find3(String jobNumber,String name,String sex,String className){
        return inspectEmployeeService.find3(jobNumber,name,sex,className);
    }//查询员工信息
    @GetMapping("/finds")
    public List<InspectEmployee> finds(String className){//根据班名返回员工信息
        return inspectEmployeeService.finds(className);
    }
    @GetMapping("/findAll")
    public List<InspectEmployee> findAll(){//根据班名返回员工信息
        return inspectEmployeeService.findAll();
    }
    @GetMapping("/findAllp")
    public List<InspectEmployeePosition> findAllp(){//根据班名返回员工信息
        return inspectEmployeeService.findAllp();
    }
    @GetMapping("/findPage")
    public List<InspectEmployee> findPage(Integer currentPageIndex,Integer pageSize){
        return inspectEmployeeService.findPage(currentPageIndex,pageSize);//使用当前页指数以及页大小实现分页查询
    }
}

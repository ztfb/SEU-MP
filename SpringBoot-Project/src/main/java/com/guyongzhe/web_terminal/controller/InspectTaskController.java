package com.guyongzhe.web_terminal.controller;

import com.guyongzhe.web_terminal.common.Result;
import com.guyongzhe.web_terminal.entity.InspectPoint;
import com.guyongzhe.web_terminal.entity.InspectTask;
import com.guyongzhe.web_terminal.entity.InspectTaskItem;
import com.guyongzhe.web_terminal.service.InspectTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/task")
@CrossOrigin
public class InspectTaskController {
    @Autowired
    private InspectTaskService inspectTaskService;
    @PostMapping("/addItem")
    public Result addItem(@RequestBody InspectTaskItem inspectTaskItem){
        return inspectTaskService.addItem(inspectTaskItem);//添加任务子项
    }
    @PutMapping("/updateTask")
    public Result updateTask(String routeName,String className){
        return inspectTaskService.updateTask(routeName,className);
    }
    @GetMapping("/findTask")
    public List<InspectTask> findTask(){
        return inspectTaskService.findTask();//查询所有的大任务
    }
    @GetMapping("/findItem")
    public List<InspectTaskItem> findItem(String className){
        return inspectTaskService.findItem(className);//根据班名查询所有的子任务
    }
    @PutMapping("/updateItem")
    public Result updateItem(@RequestBody InspectTaskItem inspectTaskItem){
        return inspectTaskService.updateItem(inspectTaskItem);
    }
    @GetMapping("/findItems")
    public List<InspectTaskItem> findItems(){return inspectTaskService.findItems();}
    @PutMapping("/deleteItem")
    public Result deleteItem(@RequestBody InspectTaskItem inspectTaskItem){
        return inspectTaskService.deleteItem(inspectTaskItem);
    }
}

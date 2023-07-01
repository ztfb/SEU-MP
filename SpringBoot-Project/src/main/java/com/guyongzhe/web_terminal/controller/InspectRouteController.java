package com.guyongzhe.web_terminal.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.guyongzhe.web_terminal.common.Result;
import com.guyongzhe.web_terminal.entity.InspectPoint;
import com.guyongzhe.web_terminal.entity.InspectRoute;
import com.guyongzhe.web_terminal.service.InspectRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/route")
@CrossOrigin
public class InspectRouteController {
    @Autowired
    private InspectRouteService inspectRouteService;
    @PostMapping("/add")
    public Result add(String routeName){
        return inspectRouteService.add(routeName);
    }
    @DeleteMapping("/delete")
    public Result delete(String routeName){
        return inspectRouteService.delete(routeName);
    }
    @DeleteMapping("/update1")
    public Result update1(String routeName,String pointNumber){
        return inspectRouteService.update1(routeName,pointNumber);//根据巡检点的id将该巡检点移除该路线
    }
    @PutMapping("/update2")
    public Result update2(String routeName,String pointNumber){ return inspectRouteService.update2(routeName,pointNumber);}//根据巡检点的id将该巡检点加入到该路线中 }
    @GetMapping("/find")
    public List<InspectPoint> find(String routeName){
        return inspectRouteService.find(routeName);//根据巡检路线名查路线上的所有点
    }
    @GetMapping("/findAll")
    public List<InspectRoute> findAll(){
        return inspectRouteService.findAll();
    }
}

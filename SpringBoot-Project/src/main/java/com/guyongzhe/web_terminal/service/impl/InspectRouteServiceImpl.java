package com.guyongzhe.web_terminal.service.impl;

import com.baomidou.mybatisplus.extension.api.R;
import com.guyongzhe.web_terminal.common.Result;
import com.guyongzhe.web_terminal.entity.InspectPoint;
import com.guyongzhe.web_terminal.entity.InspectRoute;
import com.guyongzhe.web_terminal.entity.InspectTask;
import com.guyongzhe.web_terminal.mapper.InspectPointMapper;
import com.guyongzhe.web_terminal.mapper.InspectRouteMapper;
import com.guyongzhe.web_terminal.mapper.InspectTaskMapper;
import com.guyongzhe.web_terminal.service.InspectRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class InspectRouteServiceImpl implements InspectRouteService {
    @Autowired
    private InspectRouteMapper inspectRouteMapper;
    @Autowired
    private InspectPointMapper inspectPointMapper;
    @Autowired
    private InspectTaskMapper inspectTaskMapper;
    @Override
    public Result add(String routeName) {
        List<InspectRoute> list=inspectRouteMapper.findAll();
        if(list.size()>=9)return new Result("1","巡检路线已达上限",null);
        else {
            InspectRoute temp=inspectRouteMapper.findInspectRouteByRouteName(routeName);
            if(!Objects.isNull(temp)){
                return new Result("1","路线名已存在",null);
            }
            InspectRoute inspectRoute=new InspectRoute();
            inspectRoute.setRouteName(routeName);
            inspectRoute.setInspectPointNumber(0);//开始时为0
            inspectRouteMapper.save(inspectRoute);//保存
            //保存后查找，为了得到id
            InspectRoute temp1=inspectRouteMapper.findInspectRouteByRouteName(routeName);
            // 创建巡检任务
            InspectTask inspectTask=new InspectTask();
            inspectTask.setInspectRouteName(temp1.getRouteName());
            inspectTask.setFinish(0);
            inspectTask.setInspectRouteId(temp1.getId());
            inspectTask.setInspectClassId(0);// 初始化为0
            inspectTaskMapper.save(inspectTask);
            return new Result("0","保存成功",null);
        }
    }

    @Override
    public Result delete(String routeName) {
        InspectRoute inspectRoute=inspectRouteMapper.findInspectRouteByRouteName(routeName);
        int id=inspectRoute.getId();
        InspectTask inspectTask=inspectTaskMapper.findInspectTaskByInspectRouteId(id);
        int tid=inspectTask.getId();
        if(inspectRoute.getInspectPointNumber()!=0)return new Result("1","请删除所有点后再删除路线",null);
        else {
            //清掉巡检点中的外键
            List<InspectPoint> list=inspectPointMapper.findInspectPointsByInspectRouteId(id);
            for(InspectPoint inspectPoint:list){
                inspectPoint.setInspectRouteId(0);//清除外键
            }
            inspectPointMapper.saveAll(list);//批量保存
            inspectRouteMapper.deleteById(id);//删除巡检路线
            inspectTaskMapper.deleteById(tid);//删除巡检任务
            return new Result("0","删除成功",null);
        }
    }

    @Override
    public Result update1(String routeName,String pointNumber) {//移除巡检点
        InspectRoute inspectRoute=inspectRouteMapper.findInspectRouteByRouteName(routeName);
        if(inspectRoute.getInspectPointNumber()==0)return new Result("1","没有巡检点",null);
        inspectRoute.setInspectPointNumber(inspectRoute.getInspectPointNumber()-1);
        inspectRouteMapper.save(inspectRoute);
        InspectPoint inspectPoint=inspectPointMapper.findInspectPointByNumber(pointNumber);
        inspectPoint.setInspectRouteId(0);//清除外键
        inspectPointMapper.save(inspectPoint);
        return new Result("0","移除成功",null);
    }

    @Override
    public Result update2(String routeName,String pointNumber) {//增加巡检点
        InspectRoute inspectRoute=inspectRouteMapper.findInspectRouteByRouteName(routeName);
        int rid=inspectRoute.getId();
        if(inspectRoute.getInspectPointNumber()>=10)return new Result("1","巡检点已达上限",null);
        inspectRoute.setInspectPointNumber(inspectRoute.getInspectPointNumber()+1);
        InspectPoint inspectPoint=inspectPointMapper.findInspectPointByNumber(pointNumber);
        if(inspectPoint.getInspectRouteId()!=0)return new Result("1","该巡检点已在其他巡检路线中",null);
        inspectPoint.setInspectRouteId(rid);
        inspectPointMapper.save(inspectPoint);
        inspectRouteMapper.save(inspectRoute);
        return new Result("0","添加成功",null);
    }

    @Override
    public List<InspectPoint> find(String routeName) {
        InspectRoute inspectRoute=inspectRouteMapper.findInspectRouteByRouteName(routeName);
        if(Objects.isNull(inspectRoute)){
            return null;//路线名不存在时，应返回空
        }
        return inspectPointMapper.findInspectPointsByInspectRouteId(inspectRoute.getId());
    }

    @Override
    public List<InspectRoute> findAll() {
        return inspectRouteMapper.findAll();
    }
}

package com.guyongzhe.web_terminal.service.impl;

import com.guyongzhe.web_terminal.common.Result;
import com.guyongzhe.web_terminal.entity.InspectPoint;
import com.guyongzhe.web_terminal.entity.InspectRoute;
import com.guyongzhe.web_terminal.mapper.InspectPointMapper;
import com.guyongzhe.web_terminal.mapper.InspectRouteMapper;
import com.guyongzhe.web_terminal.service.InspectPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class InspectPointServiceImpl implements InspectPointService {
    @Autowired
    private InspectPointMapper inspectPointMapper;
    @Autowired
    private InspectRouteMapper inspectRouteMapper;
    @Override
    public Result add(InspectPoint inspectPoint) {
        InspectPoint temp=inspectPointMapper.findInspectPointByNumber(inspectPoint.getNumber());
        if(!Objects.isNull(temp)){
            return new Result("1","编号已存在",null);
        }
        //设置默认属性
        inspectPoint.setAbnormalNumber(0);
        inspectPoint.setFinish(0);
        inspectPoint.setState(0);
        inspectPointMapper.save(inspectPoint);//id自增长
        return new Result("0","保存成功",null);
    }

    @Override
    public Result delete(Integer id) {
        //删除巡检点
        InspectPoint inspectPoint=inspectPointMapper.findInspectPointById(id);
        InspectRoute inspectRoute=inspectRouteMapper.findInspectRouteById(inspectPoint.getInspectRouteId());
        inspectRoute.setInspectPointNumber(inspectRoute.getInspectPointNumber()-1);
        inspectRouteMapper.save(inspectRoute);
        inspectPointMapper.deleteById(id);

        return new Result("0","删除成功",null);
    }

    @Override
    public Result update(InspectPoint inspectPoint) {
        InspectPoint temp=inspectPointMapper.findInspectPointByNumber(inspectPoint.getNumber());
        if(!Objects.isNull(temp)){
            return new Result("1","编号已存在",null);
        }//以下是编号不存在
        InspectPoint OldInspectPoint=inspectPointMapper.findInspectPointById(inspectPoint.getId());//获得原始对象
        //手动更新需要更新的字段
        OldInspectPoint.setType(inspectPoint.getType());OldInspectPoint.setArea(inspectPoint.getArea());
        OldInspectPoint.setNumber(inspectPoint.getNumber());
        OldInspectPoint.setLongitude(inspectPoint.getLongitude());OldInspectPoint.setLatitude(inspectPoint.getLatitude());
        OldInspectPoint.setCreateTime(inspectPoint.getCreateTime());
        //保存信息
        inspectPointMapper.save(OldInspectPoint);
        return new Result("0","保存成功",null);
    }

    @Override
    public InspectPoint findById(Integer id) {
        return inspectPointMapper.findInspectPointById(id);
    }

    @Override
    public List<InspectPoint> find(String type, String area, String number) {
        List<InspectPoint> list=new ArrayList<InspectPoint>();
        if(!Objects.isNull(number)&&!number.equals("")&&!Objects.isNull(type)&&!type.equals("")&&!Objects.isNull(area)&&!area.equals("")){
            return inspectPointMapper.findInspectPointsByTypeAndAreaAndNumber(type,area,number);
        }else if(!Objects.isNull(type)&&!type.equals("")&&!Objects.isNull(area)&&!area.equals("")){
            return inspectPointMapper.findInspectPointsByTypeAndArea(type,area);
        }else if(!Objects.isNull(type)&&!type.equals("")&&!Objects.isNull(number)&&!number.equals("")){
            return inspectPointMapper.findInspectPointsByTypeAndNumber(type,number);
        }else if(!Objects.isNull(area)&&!area.equals("")&&!Objects.isNull(number)&&!number.equals("")){
            return inspectPointMapper.findInspectPointsByAreaAndNumber(area,number);
        }else if(!Objects.isNull(number)&&!number.equals("")){
            InspectPoint inspectPoint=inspectPointMapper.findInspectPointByNumber(number);
            if(!Objects.isNull(inspectPoint))list.add(inspectPoint);
            return list;
        }
        else if(!Objects.isNull(type)&&!type.equals(""))return inspectPointMapper.findInspectPointsByType(type);
        else if(!Objects.isNull(area)&&!area.equals(""))return inspectPointMapper.findInspectPointsByArea(area);
        return null;
    }

    @Override
    public List<InspectPoint> findAll() {
        return inspectPointMapper.findAll();//返回全部的查询数据
    }
}

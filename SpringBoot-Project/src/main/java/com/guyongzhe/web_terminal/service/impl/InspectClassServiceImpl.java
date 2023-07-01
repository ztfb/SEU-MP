package com.guyongzhe.web_terminal.service.impl;

import com.guyongzhe.web_terminal.common.Result;
import com.guyongzhe.web_terminal.entity.InspectClass;
import com.guyongzhe.web_terminal.entity.InspectEmployee;
import com.guyongzhe.web_terminal.mapper.InspectClassMapper;
import com.guyongzhe.web_terminal.mapper.InspectEmployeeMapper;
import com.guyongzhe.web_terminal.service.InspectClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class InspectClassServiceImpl implements InspectClassService {
    @Autowired
    InspectClassMapper inspectClassMapper;
    @Autowired
    InspectEmployeeMapper inspectEmployeeMapper;
    @Override
    public Result add(InspectClass inspectClass) {
        InspectClass temp=inspectClassMapper.findInspectClassByClassName(inspectClass.getClassName());
        if(Objects.isNull(temp)) {//当班名不存在时才可以增加信息
            inspectClassMapper.save(inspectClass);//直接把传入的对象保存下来即可
            return new Result("0", "保存成功", null);//前端可能要进行刷新操作（即重新向服务器发起请求）
        }else return new Result("1","班名已存在",null);
    }

    @Override
    public Result delete(String className) {
        List<InspectEmployee> list=inspectEmployeeMapper.findInspectEmployeesByClassName(className);
        if(!Objects.isNull(list)&&list.size()!=0)return new Result("1","请先删除该班的全部员工",null);
        InspectClass inspectClass=inspectClassMapper.findInspectClassByClassName(className);
        inspectClassMapper.deleteById(inspectClass.getId());
        return new Result("0","删除成功",null);//前端也要进行相应的更新
    }

    @Override
    public Result update(InspectClass inspectClass) {
        InspectClass temp=inspectClassMapper.findInspectClassByClassName(inspectClass.getClassName());
        if(temp.getId()!=inspectClass.getId()){
            return new Result("1","班名已存在",null);
        }
        inspectClassMapper.save(inspectClass);//由于前端已经把id信息传过来了，所以可以直接保存
        return new Result("0","保存成功",null);
    }

    @Override
    public InspectClass find(String className) {
        if(!Objects.isNull(className)){
            return inspectClassMapper.findInspectClassByClassName(className);
        }
        return null;//全空，返回空数据
    }

    @Override
    public List<InspectClass> findAll() {
        return inspectClassMapper.findAll();
    }
}

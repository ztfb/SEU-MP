package com.guyongzhe.web_terminal.service.impl;

import com.guyongzhe.web_terminal.common.Result;
import com.guyongzhe.web_terminal.entity.HazardSource;
import com.guyongzhe.web_terminal.mapper.HazardSourceMapper;
import com.guyongzhe.web_terminal.service.HazardSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class HazardSourceServiceImpl implements HazardSourceService {
    @Autowired
    HazardSourceMapper hazardSourceMapper;
    @Override
    public Result add(HazardSource hazardSource) {
        HazardSource temp=hazardSourceMapper.findHazardSourceByNumber(hazardSource.getNumber());
        if(!Objects.isNull(temp)){
            return new Result("1","编号已存在",null);
        }
        hazardSource.setAbnormalNumber(0);
        hazardSource.setState(0);//异常次数和状态设置为默认值
        hazardSourceMapper.save(hazardSource);
        return new Result("0","保存成功",null);
    }

    @Override
    public Result delete(Integer id) {
        hazardSourceMapper.deleteById(id);
        return new Result("0","删除成功",null);
    }

    @Override
    public Result update(HazardSource hazardSource) {
        HazardSource temp=hazardSourceMapper.findHazardSourceByNumber(hazardSource.getNumber());
        if(!Objects.isNull(temp)){
            return new Result("1","编号已存在",null);
        }//以下是编号不存在
        HazardSource OldHazardSource=hazardSourceMapper.findHazardSourceById(hazardSource.getId());//获得原始对象
        //手动更新需要更新的字段
        OldHazardSource.setType(hazardSource.getType());OldHazardSource.setArea(hazardSource.getArea());
        OldHazardSource.setNumber(hazardSource.getNumber());
        OldHazardSource.setLongitude(hazardSource.getLongitude());OldHazardSource.setLatitude(hazardSource.getLatitude());
        OldHazardSource.setCreateTime(hazardSource.getCreateTime());
        //保存信息
        hazardSourceMapper.save(OldHazardSource);
        return new Result("0","保存成功",null);
    }

    @Override
    public List<HazardSource> find(String type, String area, String number) {
        List<HazardSource> list= new ArrayList<HazardSource>();
        if(!Objects.isNull(number)&&!number.equals("")&&!Objects.isNull(type)&&!type.equals("")&&!Objects.isNull(area)&&!area.equals("")){
            return hazardSourceMapper.findHazardSourcesByTypeAndNumberAndArea(type,number,area);
        }else if(!Objects.isNull(type)&&!type.equals("")&&!Objects.isNull(number)&&!number.equals("")){
            return hazardSourceMapper.findHazardSourcesByTypeAndNumber(type,number);
        }
        else if(!Objects.isNull(number)&&!number.equals("")&&!Objects.isNull(area)&&!area.equals("")){
            return hazardSourceMapper.findHazardSourcesByAreaAndNumber(area,number);
        }
        else if(!Objects.isNull(type)&&!type.equals("")&&!Objects.isNull(area)&&!area.equals("")){
            return hazardSourceMapper.findHazardSourcesByTypeAndArea(type,area);
        }else if(!Objects.isNull(number)&&!number.equals("")){
            HazardSource hazardSource=hazardSourceMapper.findHazardSourceByNumber(number);
            if(!Objects.isNull(hazardSource))list.add(hazardSource);
            return list;
        }
        else if(!Objects.isNull(type)&&!type.equals(""))return hazardSourceMapper.findHazardSourcesByType(type);
        else if(!Objects.isNull(area)&&!area.equals(""))return hazardSourceMapper.findHazardSourcesByArea(area);
        return null;
    }

    @Override
    public HazardSource findById(Integer id) {
        return hazardSourceMapper.findHazardSourceById(id);
    }

    @Override
    public List<HazardSource> findAll() {
        return hazardSourceMapper.findAll();//返回全部的查询数据
    }
}

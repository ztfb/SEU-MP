package com.guyongzhe.web_terminal.service.impl;

import com.guyongzhe.web_terminal.common.Result;
import com.guyongzhe.web_terminal.entity.InspectClass;
import com.guyongzhe.web_terminal.entity.InspectEmployee;
import com.guyongzhe.web_terminal.entity.InspectEmployeePosition;
import com.guyongzhe.web_terminal.mapper.InspectClassMapper;
import com.guyongzhe.web_terminal.mapper.InspectEmployeeMapper;
import com.guyongzhe.web_terminal.mapper.InspectEmployeePositionMapper;
import com.guyongzhe.web_terminal.service.InspectEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class InspectEmployeeServiceImpl implements InspectEmployeeService {
    @Autowired
    InspectEmployeeMapper inspectEmployeeMapper;
    @Autowired
    InspectEmployeePositionMapper inspectEmployeePositionMapper;
    @Autowired
    InspectClassMapper inspectClassMapper;
    @Override
    public Result add(InspectEmployee inspectEmployee) {
        //查班名
        InspectClass inspectClass=inspectClassMapper.findInspectClassByClassName(inspectEmployee.getClassName());
        if(Objects.isNull(inspectClass)){
            return new Result("1","班名不存在",null);
        }
        InspectEmployee temp=inspectEmployeeMapper.findInspectEmployeeByUserName(inspectEmployee.getUserName());
        if(!Objects.isNull(temp)){
            return new Result("1","用户名已存在",null);
        }
        InspectEmployee temp2=inspectEmployeeMapper.findInspectEmployeeByJobNumber(inspectEmployee.getJobNumber());
        if(!Objects.isNull(temp2)){
            return new Result("1","工号已存在",null);
        }
        inspectEmployee.setInspectClassId(inspectClass.getId());//传入班id
        BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        String p=passwordEncoder.encode(inspectEmployee.getPassword());//进行加密操作
        inspectEmployee.setPassword(p);
        //设置默认属性
        inspectEmployee.setInspectNumber(0);inspectEmployee.setInspectFinishNumber(0);
        inspectEmployee.setExceptionFoundNumber(0);
        inspectEmployeeMapper.save(inspectEmployee);//直接把传入的对象保存下来即可
        InspectEmployee temp3=inspectEmployeeMapper.findInspectEmployeeByJobNumber(inspectEmployee.getJobNumber());
        InspectEmployeePosition inspectEmployeePosition=new InspectEmployeePosition();
        inspectEmployeePosition.setLatitude(0);
        inspectEmployeePosition.setLongitude(0);
        inspectEmployeePosition.setInspectEmployeeId(temp3.getId());
        return new Result("0","保存成功",null);//前端可能要进行刷新操作（即重新向服务器发起请求）
    }
    @Override
    public Result delete(Integer id) {
        InspectEmployeePosition inspectEmployeePosition=inspectEmployeePositionMapper.findInspectEmployeePositionByInspectEmployeeId(id);
        inspectEmployeePositionMapper.deleteById(inspectEmployeePosition.getId());
        inspectEmployeeMapper.deleteById(id);
        return new Result("0","删除成功",null);//前端也要进行相应的更新
    }
    @Override
    public Result update(InspectEmployee inspectEmployee) {
        //查班名
        InspectClass inspectClass=inspectClassMapper.findInspectClassByClassName(inspectEmployee.getClassName());
        if(Objects.isNull(inspectClass)){
            return new Result("1","班名不存在",null);
        }
        InspectEmployee temp=inspectEmployeeMapper.findInspectEmployeeByUserName(inspectEmployee.getUserName());
        if(temp.getId()!=inspectEmployee.getId()){
            return new Result("1","用户名已存在",null);
        }
        InspectEmployee OldInspectEmployee=inspectEmployeeMapper.findInspectEmployeeById(inspectEmployee.getId());//获得原始对象
        OldInspectEmployee.setInspectClassId(inspectClass.getId());//传入班id
        OldInspectEmployee.setClassName(inspectClass.getClassName());//传入班名
        BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        String p=passwordEncoder.encode(inspectEmployee.getPassword());//进行加密操作
        OldInspectEmployee.setPassword(p);
        //手动修改字段
        OldInspectEmployee.setUserName(inspectEmployee.getUserName());
        OldInspectEmployee.setJobNumber(inspectEmployee.getJobNumber());
        OldInspectEmployee.setName(inspectEmployee.getName());
        OldInspectEmployee.setAge(inspectEmployee.getAge());
        OldInspectEmployee.setJob(inspectEmployee.getJob());
        OldInspectEmployee.setTel(inspectEmployee.getTel());
        OldInspectEmployee.setCreateTime(inspectEmployee.getCreateTime());
        inspectEmployeeMapper.save(OldInspectEmployee);//由于前端已经把id信息传过来了，所以可以直接保存
        return new Result("0","保存成功",null);
    }

    @Override
    public InspectEmployee findById(Integer id) {
        return inspectEmployeeMapper.findInspectEmployeeById(id);
    }

    @Override
    public List<InspectEmployee> find(String jobNumber, String name) {
        List<InspectEmployee> list=new ArrayList<InspectEmployee>();
        if(!Objects.isNull(jobNumber)&&!jobNumber.equals("")&&!Objects.isNull(name)&&!name.equals("")){
            InspectEmployee inspectEmployee= inspectEmployeeMapper.findInspectEmployeeByJobNumberAndName(jobNumber,name);
            if(!Objects.isNull(inspectEmployee))list.add(inspectEmployee);
            return list;
        }else if(!Objects.isNull(jobNumber)&&!jobNumber.equals("")){
            InspectEmployee inspectEmployee= inspectEmployeeMapper.findInspectEmployeeByJobNumber(jobNumber);
            if(!Objects.isNull(inspectEmployee))list.add(inspectEmployee);
            return list;
        }else if(!Objects.isNull(name)&&!name.equals("")){
            return inspectEmployeeMapper.findInspectEmployeesByName(name);
        }
        return null;//全空，返回空数据
    }

    @Override
    public List<InspectEmployee> find2(String jobNumber, String name, String sex) {
        List<InspectEmployee> list=new ArrayList<InspectEmployee>();
        if(!Objects.isNull(jobNumber)&&!jobNumber.equals("")&&!Objects.isNull(name)&&!name.equals("")&&!Objects.isNull(sex)&&!sex.equals("")){
            InspectEmployee inspectEmployee= inspectEmployeeMapper.findInspectEmployeeByNameAndJobNumberAndSex(name,jobNumber,sex);
            if(!Objects.isNull(inspectEmployee))list.add(inspectEmployee);
            return list;
        }else if(!Objects.isNull(jobNumber)&&!jobNumber.equals("")&&!Objects.isNull(name)&&!name.equals("")){
            InspectEmployee inspectEmployee= inspectEmployeeMapper.findInspectEmployeeByJobNumberAndName(jobNumber,name);
            if(!Objects.isNull(inspectEmployee))list.add(inspectEmployee);
            return list;
        }else if(!Objects.isNull(name)&&!name.equals("")&&!Objects.isNull(sex)&&!sex.equals("")){
            return inspectEmployeeMapper.findInspectEmployeesByNameAndSex(name,sex);
        }else if(!Objects.isNull(jobNumber)&&!jobNumber.equals("")&&!Objects.isNull(sex)&&!sex.equals("")){
            InspectEmployee inspectEmployee= inspectEmployeeMapper.findInspectEmployeeByJobNumberAndSex(jobNumber,sex);
            if(!Objects.isNull(inspectEmployee))list.add(inspectEmployee);
            return list;
        }else if(!Objects.isNull(jobNumber)&&!jobNumber.equals("")){
            InspectEmployee inspectEmployee= inspectEmployeeMapper.findInspectEmployeeByJobNumber(jobNumber);
            if(!Objects.isNull(inspectEmployee))list.add(inspectEmployee);
            return list;
        }else if(!Objects.isNull(sex)&&!sex.equals("")){
            return inspectEmployeeMapper.findInspectEmployeesBySex(sex);
        }else if(!Objects.isNull(name)&&!name.equals("")){
            return inspectEmployeeMapper.findInspectEmployeesByName(name);
        }
        return null;
    }

    @Override
    public List<InspectEmployee> find3(String jobNumber, String name, String sex, String className) {
        List<InspectEmployee> list=find2(jobNumber,name,sex);
        List<InspectEmployee> l=new ArrayList<InspectEmployee>();
        if(!Objects.isNull(list)&&list.size()!=0){
            for(InspectEmployee inspectEmployee:list){
                if(className.equals(inspectEmployee.getClassName())){
                    l.add(inspectEmployee);
                }
            }
        }
        return l;
    }

    @Override
    public List<InspectEmployee> finds(String className) {
        return inspectEmployeeMapper.findInspectEmployeesByClassName(className);
    }
    @Override
    public List<InspectEmployee> findAll() {
        return inspectEmployeeMapper.findAll();
    }

    @Override
    public List<InspectEmployeePosition> findAllp() {
        return inspectEmployeePositionMapper.findAll();
    }

    @Override
    public List<InspectEmployee> findPage(Integer currentPageIndex, Integer pageSize) {
        Pageable pageable= PageRequest.of(currentPageIndex-1,pageSize);
        Page<InspectEmployee> page=inspectEmployeeMapper.findAll(pageable);
        return page.getContent();
    }
}

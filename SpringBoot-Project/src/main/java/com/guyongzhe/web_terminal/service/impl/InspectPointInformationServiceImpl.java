package com.guyongzhe.web_terminal.service.impl;

import com.guyongzhe.web_terminal.common.Result;
import com.guyongzhe.web_terminal.entity.*;
import com.guyongzhe.web_terminal.mapper.*;
import com.guyongzhe.web_terminal.service.InspectPointInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class InspectPointInformationServiceImpl implements InspectPointInformationService {
    @Autowired
    InspectPointMapper inspectPointMapper;
    @Autowired
    InspectPointAbnormalLogMapper inspectPointAbnormalLogMapper;

    @Override
    public List<InspectPointAbnormalLog> findAll() {
        return inspectPointAbnormalLogMapper.findAll();
    }

    @Override
    public List<InspectPointAbnormalLog> findLogs(Integer pid, Integer eid, String status) {
        if(!Objects.isNull(pid)&&pid!=0&&!Objects.isNull(eid)&&eid!=0&&!Objects.isNull(status)&&!status.equals("")){
            return inspectPointAbnormalLogMapper.findInspectPointAbnormalLogsByInspectPointIdAndInspectEmployeeIdAndStatus(pid,eid,status);
        }else if(!Objects.isNull(pid)&&pid!=0&&!Objects.isNull(eid)&&eid!=0){
            return inspectPointAbnormalLogMapper.findInspectPointAbnormalLogsByInspectPointIdAndInspectEmployeeId(pid, eid);
        }else if(!Objects.isNull(eid)&&eid!=0&&!Objects.isNull(status)&&!status.equals("")){
            return inspectPointAbnormalLogMapper.findInspectPointAbnormalLogsByInspectEmployeeIdAndStatus(eid,status);
        }else if(!Objects.isNull(pid)&&pid!=0&&!Objects.isNull(status)&&!status.equals("")){
            return inspectPointAbnormalLogMapper.findInspectPointAbnormalLogsByInspectPointIdAndStatus(pid,status);
        }else if(!Objects.isNull(pid)&&pid!=0){
            return inspectPointAbnormalLogMapper.findInspectPointAbnormalLogsByInspectPointId(pid);
        }else if(!Objects.isNull(eid)&&eid!=0){
            return inspectPointAbnormalLogMapper.findInspectPointAbnormalLogsByInspectEmployeeId(eid);
        }else if(!Objects.isNull(status)&&!status.equals("")){
            return inspectPointAbnormalLogMapper.findInspectPointAbnormalLogsByStatus(status);
        }
        return null;
    }
    @Override
    public Result updateResult(Integer id,String status) {
        InspectPointAbnormalLog old=inspectPointAbnormalLogMapper.findInspectPointAbnormalLogById(id);
        old.setStatus(status);
        inspectPointAbnormalLogMapper.save(old);
        return new Result("0","保存成功",null);
    }
}

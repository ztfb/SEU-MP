package com.guyongzhe.web_terminal.service.impl;

import com.guyongzhe.web_terminal.common.Result;
import com.guyongzhe.web_terminal.entity.HazardSourceAbnormalLog;
import com.guyongzhe.web_terminal.mapper.HazardSourceAbnormalLogMapper;
import com.guyongzhe.web_terminal.mapper.HazardSourceMapper;
import com.guyongzhe.web_terminal.service.HazardSourceInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class HazardSourceInformationServiceImpl implements HazardSourceInformationService {
    @Autowired
    HazardSourceMapper hazardSourceMapper;
    @Autowired
    HazardSourceAbnormalLogMapper hazardSourceAbnormalLogMapper;

    @Override
    public List<HazardSourceAbnormalLog> findLogs(Integer hid, String status) {
        if(!Objects.isNull(hid)&&hid!=0&&!Objects.isNull(status)&&!status.equals("")){
            return hazardSourceAbnormalLogMapper.findHazardSourceAbnormalLogsByHazardSourceIdAndStatus(hid,status);
        }else if(!Objects.isNull(hid)&&hid!=0){
            return hazardSourceAbnormalLogMapper.findHazardSourceAbnormalLogsByHazardSourceId(hid);
        }else if(!Objects.isNull(status)&&!status.equals("")){
            return hazardSourceAbnormalLogMapper.findHazardSourceAbnormalLogsByStatus(status);
        }
        return null;
    }

    @Override
    public List<HazardSourceAbnormalLog> findAll() {
        return hazardSourceAbnormalLogMapper.findAll();
    }

    @Override
    public Result updateResult(Integer id, String status) {
        HazardSourceAbnormalLog old=hazardSourceAbnormalLogMapper.findHazardSourceAbnormalLogById(id);
        old.setStatus(status);
        hazardSourceAbnormalLogMapper.save(old);
        return new Result("0","保存成功",null);
    }
}

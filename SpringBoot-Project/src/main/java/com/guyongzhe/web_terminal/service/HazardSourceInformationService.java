package com.guyongzhe.web_terminal.service;

import com.guyongzhe.web_terminal.common.Result;
import com.guyongzhe.web_terminal.entity.HazardSourceAbnormalLog;

import java.util.List;

public interface HazardSourceInformationService {
    public List<HazardSourceAbnormalLog> findLogs(Integer hid,String status);
    public List<HazardSourceAbnormalLog> findAll();
    public Result updateResult(Integer id,String status);
}

package com.guyongzhe.web_terminal.service;

import com.guyongzhe.web_terminal.common.Result;
import com.guyongzhe.web_terminal.entity.InspectPoint;
import com.guyongzhe.web_terminal.entity.InspectPointAbnormalLog;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface InspectPointInformationService {
    public List<InspectPointAbnormalLog> findAll();
    public List<InspectPointAbnormalLog> findLogs(Integer pid, Integer eid, String status);
    public Result updateResult(Integer id,String status);
}

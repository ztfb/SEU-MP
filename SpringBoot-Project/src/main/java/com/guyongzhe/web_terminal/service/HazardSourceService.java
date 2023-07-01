package com.guyongzhe.web_terminal.service;

import com.guyongzhe.web_terminal.common.Result;
import com.guyongzhe.web_terminal.entity.HazardSource;

import java.util.List;

public interface HazardSourceService {
    public Result add(HazardSource hazardSource);
    public Result delete(Integer id);
    public Result update(HazardSource hazardSource);
    public List<HazardSource> find(String type, String area, String number);
    public HazardSource findById(Integer id);
    public List<HazardSource> findAll();
}

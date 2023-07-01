package com.guyongzhe.web_terminal.service;

import com.guyongzhe.web_terminal.common.Result;
import com.guyongzhe.web_terminal.entity.InspectPoint;

import java.util.List;

public interface InspectPointService {
    public Result add(InspectPoint inspectPoint);
    public Result delete(Integer id);
    public Result update(InspectPoint inspectPoint);
    public InspectPoint findById(Integer id);
    public List<InspectPoint> find(String type, String area, String number);
    public List<InspectPoint> findAll();
}

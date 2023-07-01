package com.guyongzhe.web_terminal.service;

import com.guyongzhe.web_terminal.common.Result;
import com.guyongzhe.web_terminal.entity.InspectClass;

import java.util.List;

public interface InspectClassService {
    public Result add(InspectClass inspectClass);
    public Result delete(String className);
    public Result update(InspectClass inspectClass);
    public InspectClass find(String className);
    public List<InspectClass> findAll();
}

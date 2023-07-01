package com.guyongzhe.web_terminal.service;

import com.guyongzhe.web_terminal.common.Result;
import com.guyongzhe.web_terminal.entity.InspectEmployee;
import com.guyongzhe.web_terminal.entity.InspectEmployeePosition;

import javax.persistence.criteria.CriteriaBuilder;
import javax.swing.plaf.SpinnerUI;
import java.util.List;

public interface InspectEmployeeService {
    public Result add(InspectEmployee inspectEmployee);
    public Result delete(Integer id);
    public Result update(InspectEmployee inspectEmployee);
    public InspectEmployee findById(Integer id);
    public List<InspectEmployee> find(String jobNumber,String name);
    public List<InspectEmployee> find2(String jobNumber,String name,String sex);
    public List<InspectEmployee> find3(String jobNumber,String name,String sex,String className);
    public List<InspectEmployee> finds(String className);
    public List<InspectEmployee> findAll();
    public List<InspectEmployeePosition> findAllp();
    public List<InspectEmployee> findPage(Integer currentPageIndex, Integer pageSize);
}

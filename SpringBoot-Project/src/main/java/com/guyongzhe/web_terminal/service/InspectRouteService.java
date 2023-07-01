package com.guyongzhe.web_terminal.service;

import com.guyongzhe.web_terminal.common.Result;
import com.guyongzhe.web_terminal.entity.InspectPoint;
import com.guyongzhe.web_terminal.entity.InspectRoute;

import java.util.List;

public interface InspectRouteService {
    public Result add(String routeName);
    public Result delete(String routeName);
    public Result update1(String routeName,String pointNumber);
    public Result update2(String routeName,String pointNumber);
    public List<InspectPoint> find(String routeName);
    public List<InspectRoute> findAll();
}

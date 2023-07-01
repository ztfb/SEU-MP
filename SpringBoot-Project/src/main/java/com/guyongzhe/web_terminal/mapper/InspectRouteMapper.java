package com.guyongzhe.web_terminal.mapper;

import com.guyongzhe.web_terminal.entity.InspectRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InspectRouteMapper extends JpaRepository<InspectRoute,Integer> {
    public InspectRoute findInspectRouteById(Integer id);
    public InspectRoute findInspectRouteByRouteName(String routeName);
}

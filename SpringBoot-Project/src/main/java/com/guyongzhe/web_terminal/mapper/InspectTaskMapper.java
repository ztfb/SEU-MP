package com.guyongzhe.web_terminal.mapper;

import com.guyongzhe.web_terminal.entity.InspectTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InspectTaskMapper extends JpaRepository<InspectTask,Integer> {
    public InspectTask findInspectTaskByInspectRouteName(String routeName);
    public InspectTask findInspectTaskByInspectRouteId(Integer id);
}

package com.guyongzhe.web_terminal.mapper;

import com.guyongzhe.web_terminal.entity.InspectEmployeePosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InspectEmployeePositionMapper extends JpaRepository<InspectEmployeePosition,Integer> {
    public InspectEmployeePosition findInspectEmployeePositionByInspectEmployeeId(Integer id);
}

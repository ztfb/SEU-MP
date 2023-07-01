package com.guyongzhe.web_terminal.mapper;

import com.guyongzhe.web_terminal.entity.HazardSourceAbnormalLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HazardSourceAbnormalLogMapper extends JpaRepository<HazardSourceAbnormalLog,Integer> {
    public List<HazardSourceAbnormalLog> findHazardSourceAbnormalLogsByHazardSourceId(Integer id);
    public List<HazardSourceAbnormalLog> findHazardSourceAbnormalLogsByStatus(String status);
    public List<HazardSourceAbnormalLog> findHazardSourceAbnormalLogsByHazardSourceIdAndStatus(Integer id,String status);
    public HazardSourceAbnormalLog findHazardSourceAbnormalLogById(Integer id);
}

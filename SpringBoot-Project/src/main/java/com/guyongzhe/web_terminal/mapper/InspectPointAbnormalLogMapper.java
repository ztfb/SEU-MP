package com.guyongzhe.web_terminal.mapper;

import com.guyongzhe.web_terminal.entity.InspectPointAbnormalLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InspectPointAbnormalLogMapper extends JpaRepository<InspectPointAbnormalLog,Integer> {
    public InspectPointAbnormalLog findInspectPointAbnormalLogById(Integer id);
    public List<InspectPointAbnormalLog> findInspectPointAbnormalLogsByInspectEmployeeId(Integer eid);
    public List<InspectPointAbnormalLog> findInspectPointAbnormalLogsByInspectPointId(Integer pid);
    public List<InspectPointAbnormalLog> findInspectPointAbnormalLogsByStatus(String status);
    public List<InspectPointAbnormalLog> findInspectPointAbnormalLogsByInspectPointIdAndInspectEmployeeId(Integer pid,Integer eid);
    public List<InspectPointAbnormalLog> findInspectPointAbnormalLogsByInspectPointIdAndStatus(Integer pid,String status);
    public List<InspectPointAbnormalLog> findInspectPointAbnormalLogsByInspectEmployeeIdAndStatus(Integer eid,String status);
    public List<InspectPointAbnormalLog> findInspectPointAbnormalLogsByInspectPointIdAndInspectEmployeeIdAndStatus(Integer pid,Integer eid,String status);
}

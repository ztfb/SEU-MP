package com.guyongzhe.web_terminal.mapper;

import com.guyongzhe.web_terminal.entity.InspectPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Repository
public interface InspectPointMapper extends JpaRepository<InspectPoint,Integer> {
    public InspectPoint findInspectPointById(Integer id);
    public List<InspectPoint> findInspectPointsByType(String type);
    public List<InspectPoint> findInspectPointsByArea(String area);
    public InspectPoint findInspectPointByNumber(String number);
    public List<InspectPoint> findInspectPointsByTypeAndArea(String type,String area);
    public List<InspectPoint> findInspectPointsByTypeAndNumber(String type,String number);
    public List<InspectPoint> findInspectPointsByAreaAndNumber(String area,String number);
    public List<InspectPoint> findInspectPointsByTypeAndAreaAndNumber(String type,String area,String number);
    public List<InspectPoint> findInspectPointsByInspectRouteId(Integer id);
}

package com.guyongzhe.web_terminal.mapper;

import com.guyongzhe.web_terminal.entity.HazardSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Repository
public interface HazardSourceMapper extends JpaRepository<HazardSource,Integer> {
    public HazardSource findHazardSourceById(Integer id);
    public List<HazardSource> findHazardSourcesByType(String type);
    public List<HazardSource> findHazardSourcesByArea(String area);
    public HazardSource findHazardSourceByNumber(String number);
    public List<HazardSource> findHazardSourcesByTypeAndArea(String type,String area);
    public List<HazardSource> findHazardSourcesByTypeAndNumber(String type,String number);
    public List<HazardSource> findHazardSourcesByAreaAndNumber(String area,String number);
    public List<HazardSource> findHazardSourcesByTypeAndNumberAndArea(String type,String number,String area);
}

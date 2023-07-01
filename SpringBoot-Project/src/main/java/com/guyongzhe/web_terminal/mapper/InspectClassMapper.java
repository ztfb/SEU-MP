package com.guyongzhe.web_terminal.mapper;

import com.guyongzhe.web_terminal.entity.InspectClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InspectClassMapper extends JpaRepository<InspectClass,Integer> {
    public InspectClass findInspectClassByClassName(String className);
}

package com.guyongzhe.web_terminal.mapper;

import com.guyongzhe.web_terminal.entity.StatisticsData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticsDataMapper extends JpaRepository<StatisticsData,Integer> {
}

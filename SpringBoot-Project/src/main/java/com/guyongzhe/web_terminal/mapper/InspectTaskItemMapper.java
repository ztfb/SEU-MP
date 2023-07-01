package com.guyongzhe.web_terminal.mapper;

import com.guyongzhe.web_terminal.entity.InspectTaskItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.List;

@Repository
public interface InspectTaskItemMapper extends JpaRepository<InspectTaskItem,Integer> {
    public List<InspectTaskItem> findInspectTaskItemsByInspectTaskId(Integer id);
    public List<InspectTaskItem> findInspectTaskItemsByInspectClassName(String className);
    public InspectTaskItem findInspectTaskItemByStartTimeAndEndTime(Time s,Time e);
}

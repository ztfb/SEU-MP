package com.guyongzhe.web_terminal.service.impl;

import com.baomidou.mybatisplus.extension.api.R;
import com.guyongzhe.web_terminal.common.Result;
import com.guyongzhe.web_terminal.entity.InspectClass;
import com.guyongzhe.web_terminal.entity.InspectTask;
import com.guyongzhe.web_terminal.entity.InspectTaskItem;
import com.guyongzhe.web_terminal.mapper.InspectClassMapper;
import com.guyongzhe.web_terminal.mapper.InspectTaskItemMapper;
import com.guyongzhe.web_terminal.mapper.InspectTaskMapper;
import com.guyongzhe.web_terminal.service.InspectTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class InspectTaskServiceImpl implements InspectTaskService {
    @Autowired
    InspectTaskMapper inspectTaskMapper;
    @Autowired
    InspectTaskItemMapper inspectTaskItemMapper;
    @Autowired
    InspectClassMapper inspectClassMapper;
    @Override
    public Result addItem(InspectTaskItem inspectTaskItem) {
        inspectTaskItem.setFinish(0);//设置默认值
        inspectTaskItemMapper.save(inspectTaskItem);
        return new Result("0","保存成功",null);
    }

    @Override
    public Result updateTask(String routeName, String className) {
        InspectTask inspectTask=inspectTaskMapper.findInspectTaskByInspectRouteName(routeName);
        InspectClass inspectClass=inspectClassMapper.findInspectClassByClassName(className);
        inspectTask.setInspectClassName(className);
        if(!Objects.isNull(inspectClass)) inspectTask.setInspectClassId(inspectClass.getId());
        else inspectTask.setInspectClassId(0);
        inspectTaskMapper.save(inspectTask);
        return new Result("0","保存成功",null);
    }

    @Override
    public List<InspectTask> findTask() {
        return inspectTaskMapper.findAll();//查询全部的任务
    }

    @Override
    public List<InspectTaskItem> findItem(String className) {
        return inspectTaskItemMapper.findInspectTaskItemsByInspectClassName(className);
    }
    @Override
    public List<InspectTaskItem> findItems() {
        return inspectTaskItemMapper.findAll();
    }

    @Override
    public Result updateItem(InspectTaskItem inspectTaskItem) {
        inspectTaskItemMapper.save(inspectTaskItem);
        return new Result("0","保存成功",null);
    }

    @Override
    public Result deleteItem(InspectTaskItem inspectTaskItem) {
        InspectTaskItem temp = inspectTaskItemMapper.findInspectTaskItemByStartTimeAndEndTime(
                inspectTaskItem.getStartTime(),inspectTaskItem.getEndTime()
        );
        inspectTaskItemMapper.deleteById(temp.getId());
        return new Result("0","删除成功",null);
    }
}

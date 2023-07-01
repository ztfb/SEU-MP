package com.guyongzhe.web_terminal.service;

import com.guyongzhe.web_terminal.common.Result;
import com.guyongzhe.web_terminal.entity.InspectTask;
import com.guyongzhe.web_terminal.entity.InspectTaskItem;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface InspectTaskService {
    public Result addItem(@RequestBody InspectTaskItem inspectTaskItem);
    public Result updateTask(String routeName,String className);
    public List<InspectTask> findTask();
    public List<InspectTaskItem> findItem(String className);
    public List<InspectTaskItem> findItems();
    public Result updateItem(InspectTaskItem inspectTaskItem);
    public Result deleteItem(InspectTaskItem inspectTaskItem);
}

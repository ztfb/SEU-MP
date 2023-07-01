package com.guyongzhe.web_terminal.service;

import com.guyongzhe.web_terminal.common.Result;
import com.guyongzhe.web_terminal.entity.AdminInformation;
import com.guyongzhe.web_terminal.entity.AdminRegister;
import com.guyongzhe.web_terminal.entity.AdminRegisterCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public interface AdminService {
    public Result register(AdminRegister adminRegister);
    public String registerTest(String userName,String content);//注册验证
    //开启事务
    @Transactional(rollbackFor=Exception.class)
    public boolean registerSuccess(String userName,String password,String nickName);//注册事务
    public Result login(String userName,String password);
    public Result logout();
}

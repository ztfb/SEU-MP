package com.guyongzhe.web_terminal.service.impl;

import com.guyongzhe.web_terminal.common.Result;
import com.guyongzhe.web_terminal.entity.AdminInformation;
import com.guyongzhe.web_terminal.entity.AdminLogin;
import com.guyongzhe.web_terminal.entity.AdminRegister;
import com.guyongzhe.web_terminal.entity.AdminRegisterCode;
import com.guyongzhe.web_terminal.mapper.AdminInformationMapper;
import com.guyongzhe.web_terminal.mapper.AdminRegisterCodeMapper;
import com.guyongzhe.web_terminal.service.AdminService;
import com.guyongzhe.web_terminal.utils.JwtUtil;
import com.guyongzhe.web_terminal.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Objects;
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private AdminRegisterCodeMapper adminRegisterCodeMapper;
    @Autowired
    private AdminInformationMapper adminInformationMapper;
    @Value(value ="1")
    private int codeId;//注册码的id
    @Override
    public Result register(AdminRegister adminRegister) {
        String testResult=registerTest(adminRegister.getAdminInformation().getUserName(),adminRegister.getContent());
        if(testResult.equals("1"))return new Result("1","注册码无效",null);
        else if(testResult.equals("2"))return new Result("1","该用户名已被注册",null);
        else if(registerSuccess(adminRegister.getAdminInformation().getUserName(),adminRegister.getAdminInformation().getPassword()
                ,adminRegister.getAdminInformation().getNickName())==true)return new Result("0","注册成功",null);
        return new Result("1","注册失败",null);//事务没有执行完成，导致注册失败
    }

    @Override
    public String registerTest(String userName,String content) {
        AdminRegisterCode adminRegisterCode=adminRegisterCodeMapper.findAdminRegisterCodeByContent(content);
        if(Objects.isNull(adminRegisterCode)){//如果没有找到注册码，返回“1”
            return "1";
        }
        AdminInformation adminInformation=adminInformationMapper.findAdminInformationByUserName(userName);
        if(!Objects.isNull(adminInformation)){//如果找到用户名，返回“2”
            return "2";
        }
        codeId=adminRegisterCode.getId();//设置注册码id
        //如果注册码可用，且该用户名未被注册，则返回“0”
        return "0";
    }
    @Override
    public boolean registerSuccess(String userName, String password, String nickName) {
        adminRegisterCodeMapper.deleteById(codeId);//删除相应的注册码
        AdminInformation adminInformation=new AdminInformation();
        adminInformation.setUserName(userName);
        BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        String p=passwordEncoder.encode(password);//进行加密操作
        adminInformation.setPassword(p);
        adminInformation.setNickName(nickName);
        adminInformationMapper.save(adminInformation);//保存用户信息
        return true;//如果可以执行完毕，就返回true
    }

    @Override
    public Result login(String userName,String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName,password);
        try{
            Authentication authenticate = authenticationManager.authenticate(authenticationToken);
            //没有抛出异常
            //使用userid生成token
            AdminLogin adminLogin = (AdminLogin) authenticate.getPrincipal();
            String userId = Long.toString(adminLogin.getAdminInformation().getId());
            String jwt = JwtUtil.createJWT(userId);
            //authenticate存入redis
            redisCache.setCacheObject(userId,adminLogin);
            //把token响应给前端
            HashMap<String,String> map = new HashMap<>();
            map.put("token",jwt);
            return new Result("0","登录成功",map);
        }catch (AuthenticationException e){
            return new Result("1","用户名或密码错误",null);
        }
    }

    @Override
    public Result logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AdminLogin adminLogin = (AdminLogin) authentication.getPrincipal();
        String userid = Long.toString(adminLogin.getAdminInformation().getId());
        redisCache.deleteObject(userid);
        return new Result("0","退出成功",null);
    }
}

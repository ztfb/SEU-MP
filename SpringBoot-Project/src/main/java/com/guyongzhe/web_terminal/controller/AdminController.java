package com.guyongzhe.web_terminal.controller;

import com.guyongzhe.web_terminal.common.Result;
import com.guyongzhe.web_terminal.entity.AdminRegister;
import com.guyongzhe.web_terminal.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/admin")
@CrossOrigin
public class AdminController {
    @Autowired
    private AdminService adminService;
    @PostMapping("/register")
    public Result register(@RequestBody AdminRegister adminRegister){
        return adminService.register(adminRegister);
    }
    @GetMapping("/login")
    public Result login(String userName,String password){
        return adminService.login(userName,password);
    }
    @DeleteMapping("/logout")
    public Result logout(){
        return adminService.logout();
    }
}

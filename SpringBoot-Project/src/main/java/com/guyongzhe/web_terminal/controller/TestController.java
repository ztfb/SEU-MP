package com.guyongzhe.web_terminal.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//测试专用控制器
@RestController
@CrossOrigin
public class TestController {
    @GetMapping("/test")
    public String  test(){
        return "hello";
    }
}

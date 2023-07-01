package com.guyongzhe.web_terminal;

import com.guyongzhe.web_terminal.entity.AdminInformation;
import com.guyongzhe.web_terminal.entity.AdminRegisterCode;
import com.guyongzhe.web_terminal.mapper.AdminInformationMapper;
import com.guyongzhe.web_terminal.mapper.AdminRegisterCodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;

@EnableTransactionManagement
@SpringBootApplication
public class WebTerminalApplication {

    @Autowired
    private static AdminInformationMapper adminInformationMapper;
    public static void main(String[] args) {
        SpringApplication.run(WebTerminalApplication.class, args);
    }

}

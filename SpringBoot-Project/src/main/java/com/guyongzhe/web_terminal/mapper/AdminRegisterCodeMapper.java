package com.guyongzhe.web_terminal.mapper;

import com.guyongzhe.web_terminal.entity.AdminRegisterCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRegisterCodeMapper extends JpaRepository<AdminRegisterCode,Integer> {
    public AdminRegisterCode findAdminRegisterCodeByContent(String content);//根据内容查找注册码
}

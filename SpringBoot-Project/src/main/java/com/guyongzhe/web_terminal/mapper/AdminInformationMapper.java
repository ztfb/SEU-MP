package com.guyongzhe.web_terminal.mapper;

import com.guyongzhe.web_terminal.entity.AdminInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminInformationMapper extends JpaRepository<AdminInformation,Integer> {
    public AdminInformation findAdminInformationByUserName(String username);
}

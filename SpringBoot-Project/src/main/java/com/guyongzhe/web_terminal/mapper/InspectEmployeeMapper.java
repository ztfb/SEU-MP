package com.guyongzhe.web_terminal.mapper;

import com.guyongzhe.web_terminal.entity.InspectEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InspectEmployeeMapper extends JpaRepository<InspectEmployee,Integer> {
    public InspectEmployee findInspectEmployeeById(Integer id);
    public List<InspectEmployee> findInspectEmployeesByName(String name);
    public InspectEmployee findInspectEmployeeByJobNumber(String jobNumber);
    public List<InspectEmployee> findInspectEmployeesBySex(String sex);
    public InspectEmployee findInspectEmployeeByJobNumberAndName(String jobNumber,String name);
    public InspectEmployee findInspectEmployeeByJobNumberAndSex(String jobNumber,String sex);
    public List<InspectEmployee> findInspectEmployeesByNameAndSex(String name,String sex);
    public InspectEmployee findInspectEmployeeByNameAndJobNumberAndSex(String name,String jobNumber,String sex);
    public InspectEmployee findInspectEmployeeByUserName(String userName);
    public List<InspectEmployee> findInspectEmployeesByClassName(String className);
}

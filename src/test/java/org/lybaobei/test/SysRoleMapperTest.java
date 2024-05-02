package org.lybaobei.test;


import org.junit.jupiter.api.Test;
import org.lybaobei.entity.SystemRole;
import org.lybaobei.mapper.SysRoleMapper;
import org.lybaobei.service.SysRoleServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
public class SysRoleMapperTest {

    
    
    @Autowired
    private SysRoleMapper sysRoleMapper;
    
    @Test
    public void testList(){
        List<SystemRole> systemRoles = sysRoleMapper.selectList(null);
        systemRoles.forEach(System.out::println);
    }

    @Test
    public void testAdd(){
        SystemRole systemRole = new SystemRole();
        systemRole.setRoleName("超级管理员");
        systemRole.setRoleStatus(1);
        systemRole.setSeq(1);
        systemRole.setRoleDesc("测试管理员");
        sysRoleMapper.insert(systemRole);
        
    }
    
    @Test
    public void testUpdate(){
        SystemRole systemRole = sysRoleMapper.selectById(1);
        systemRole.setRoleDesc("测试更新444");
        sysRoleMapper.updateById(systemRole);
    }
}

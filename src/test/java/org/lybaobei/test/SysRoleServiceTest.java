package org.lybaobei.test;

import org.junit.jupiter.api.Test;
import org.lybaobei.entity.SystemRole;
import org.lybaobei.service.SysRoleServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author nommpp
 * @date 2024/5/3 0003
 */
@SpringBootTest
public class SysRoleServiceTest {
    
    @Autowired
    private SysRoleServices sysRoleServices;
    
    @Test
    public void findAll(){
        List<SystemRole> list = sysRoleServices.list();
        list.forEach(System.out::println);
    }
    
    @Test
    public void testAdd(){
        SystemRole systemRole = new SystemRole();
        systemRole.setRoleName("普通管理员");
        systemRole.setRoleStatus(1);
        systemRole.setSeq(2);
        systemRole.setRoleDesc("普通管理");
        sysRoleServices.save(systemRole);
    }
    
    @Test
    public void testUpdate(){
        SystemRole systemRole = sysRoleServices.getById(2);
        systemRole.setRoleDesc("普通测试2");
        sysRoleServices.updateById(systemRole);
    }
}

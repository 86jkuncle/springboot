package org.lybaobei.service;



import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.lybaobei.dto.AssginRoleDTO;
import org.lybaobei.dto.PageDTO;
import org.lybaobei.entity.SystemRole;

import java.util.Map;


public interface SysRoleServices extends IService<SystemRole> {
    IPage<SystemRole> listEffective(PageDTO pageDTO, SystemRole systemRole);
    
    void removeRoleById(String roleId);
    
    Map<String, Object> getRolesByUserId(String userId);
    
    void doAssign(AssginRoleDTO assginRoleDTO);
}

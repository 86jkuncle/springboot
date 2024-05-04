package org.lybaobei.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.lybaobei.dto.PageDTO;
import org.lybaobei.entity.SystemOrg;

import java.util.List;

/**
 * @author nommpp
 * @date 2024/5/3 0003
 */
public interface SysOrgService extends IService<SystemOrg> {
    
    IPage<SystemOrg> listEffective(PageDTO page, String orgName);
    
    List<SystemOrg> listParent();
    
    void removeOrgById(Integer orgId);
    
    List<SystemOrg> listAllEffective();
}

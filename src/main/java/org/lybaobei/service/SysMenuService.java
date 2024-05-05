package org.lybaobei.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.lybaobei.dto.AssginMenuDTO;
import org.lybaobei.dto.PageDTO;
import org.lybaobei.entity.SystemMenu;
import org.lybaobei.vo.RouterVO;

import java.util.List;

/**
 * @author nommpp
 * @date 2024/5/3 0003
 */
public interface SysMenuService extends IService<SystemMenu> {
    List<SystemMenu> findNodes();
    
    IPage<SystemMenu> listEffective(PageDTO pageDTO, SystemMenu systemMenu);
    
    List<SystemMenu> listParent();
    
    void removeByMenuId(Integer id);
    
    List<SystemMenu> findMenuByRoleId(Integer roleId);
    
    void doAssign(AssginMenuDTO assginMenuDTO);
    
    List<RouterVO> getUserMenuByUserId(String userId);
    
    List<String> getUserButtonPermsList(String userId);
}

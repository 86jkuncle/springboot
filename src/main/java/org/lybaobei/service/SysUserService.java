package org.lybaobei.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.lybaobei.dto.PageDTO;
import org.lybaobei.entity.SystemUser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author nommpp
 * @date 2024/5/3 0003
 */

public interface SysUserService extends IService<SystemUser> {
    
    IPage<SystemUser> listEffective(PageDTO pageDTO, SystemUser systemUser);
    
    void saveUser(SystemUser user);
    
    void removeUserById(String userId);
    
    void changeUserStatus(String userId, Integer status);
    
    SystemUser getByName(String userName);
    
    Map<String,Object> getUserInfo(String userId);

    void incrementLockCnt(String userName);

    void resetLockCnt(String userName);
}

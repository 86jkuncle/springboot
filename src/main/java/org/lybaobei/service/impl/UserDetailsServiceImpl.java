package org.lybaobei.service.impl;

import org.lybaobei.common.Constants;
import org.lybaobei.custom.CusotmUser;
import org.lybaobei.entity.SystemUser;
import org.lybaobei.service.SysUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;

/**
 * @author nommpp
 * @date 2024/5/5 0005
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private SysUserService sysUserService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SystemUser byName = sysUserService.getByName(username);

        if(byName == null){
            throw new UsernameNotFoundException("用户不存在");
        }

        if(byName.getUserStatus().equals(Constants.UserStatus.INVALID)){
            throw new RuntimeException("用户已被删除");
        }

        if(byName.getUserStatus().equals(Constants.UserStatus.LOCKED)){
            throw new RuntimeException("用户已被锁定");
        }

        return new CusotmUser(byName, Collections.EMPTY_LIST);
    }
}

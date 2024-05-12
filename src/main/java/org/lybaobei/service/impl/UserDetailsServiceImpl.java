package org.lybaobei.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.lybaobei.common.Constants;
import org.lybaobei.custom.CusotmUser;
import org.lybaobei.entity.SystemUser;
import org.lybaobei.service.SysMenuService;
import org.lybaobei.service.SysUserService;
import org.lybaobei.vo.RouterVO;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

    @Resource
    private SysMenuService menuService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SystemUser loginUser = sysUserService.getByName(username);

        if(loginUser == null){
            throw new UsernameNotFoundException("用户不存在");
        }

        if(loginUser.getUserStatus().equals(Constants.UserStatus.INVALID)){
            return new CusotmUser(loginUser, false,true,
                true,true,Collections.EMPTY_LIST);
        }

        if(loginUser.getUserStatus().equals(Constants.UserStatus.LOCKED)
            || loginUser.getLockCnt() == Constants.UserStatus.LOCKCNT){
            return new CusotmUser(loginUser, true,true,
                true,false,Collections.EMPTY_LIST);
        }

        //根据用户id查询用户按钮权限
        List<String> userButtonPermsList =
            menuService.getUserButtonPermsList(loginUser.getUserId());

        //转换成security要求的格式
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        userButtonPermsList.forEach(item->{
            authorities.add(new SimpleGrantedAuthority(item));
        });
        return new CusotmUser(loginUser, true,true,
            true,true,authorities);
    }

}

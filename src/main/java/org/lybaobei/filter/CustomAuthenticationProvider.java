package org.lybaobei.filter;

import javax.annotation.Resource;
import org.lybaobei.custom.CustomMd5Password;
import org.lybaobei.service.SysUserService;
import org.lybaobei.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.stereotype.Component;

/**
* @Description java类的作用
* @Author mtxst
* @Date 2024/5/11 22:54 $
*/
@Component
public class CustomAuthenticationProvider extends DaoAuthenticationProvider {



    @Autowired
    private SysUserService userService;
    @Autowired
    @Override
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        super.setUserDetailsService(userDetailsService);
    }

    @Autowired
    @Override
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        super.setPasswordEncoder(passwordEncoder);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws
        AuthenticationException {
        try {
            //调用上层验证逻辑
            Authentication auth = super.authenticate(authentication);
            //如果验证通过登录成功则重置尝试次数， 否则抛出异常 //TODO 每次登录都走这里
            userService.resetLockCnt(authentication.getName());
            return auth;
        }catch (BadCredentialsException e) {
            //如果验证不通过，则更新尝试次数，当超过次数以后抛出账号锁定异常
            userService.incrementLockCnt(authentication.getName());

            throw e;

        } catch (LockedException e){
            //该用户已经被锁定，则进入这个异常

            throw e;
        }catch(UsernameNotFoundException e){
            throw e;
        }
    }
}

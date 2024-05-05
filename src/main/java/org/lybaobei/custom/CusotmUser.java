package org.lybaobei.custom;

import org.lybaobei.entity.SystemUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @author nommpp
 * @date 2024/5/5 0005
 */
public class CusotmUser extends User {

    private SystemUser systemUser;


    public CusotmUser(SystemUser systemUser, Collection<? extends GrantedAuthority> authorities) {
        super(systemUser.getUserName(), systemUser.getPwd(), authorities);
        this.systemUser = systemUser;
    }

    public SystemUser getSystemUser() {
        return this.systemUser;
    }

    public void setSystemUser(SystemUser systemUser) {
        this.systemUser = systemUser;
    }
}

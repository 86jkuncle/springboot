package org.lybaobei.custom;

import java.util.Collections;
import org.lybaobei.entity.SystemUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import org.springframework.util.Assert;

/**
 * @author nommpp
 * @date 2024/5/5 0005
 */
public class CusotmUser extends User {

    private SystemUser systemUser;


    public CusotmUser(SystemUser systemUser, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(systemUser.getUserName(),systemUser.getPwd(),enabled,accountNonExpired,credentialsNonExpired,accountNonLocked,authorities);
        this.systemUser = systemUser;
    }

    public SystemUser getSystemUser() {
        return this.systemUser;
    }

    public void setSystemUser(SystemUser systemUser) {
        this.systemUser = systemUser;
    }
}

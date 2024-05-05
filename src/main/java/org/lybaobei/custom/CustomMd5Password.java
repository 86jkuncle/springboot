package org.lybaobei.custom;

import cn.hutool.crypto.digest.DigestUtil;
import org.lybaobei.utils.SecUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author nommpp
 * @date 2024/5/5 0005
 */
@Component
public class CustomMd5Password implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
//        String salt = SecUtil.getSalt();
//        return SecUtil.password(rawPassword.toString(),salt);
        return DigestUtil.md5Hex(rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.equals(DigestUtil.md5Hex(rawPassword.toString()));
        //SecUtil.equals(rawPassword, user.getSalt(), encodedPassword);
        //return false;
    }
}

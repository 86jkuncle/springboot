package org.lybaobei.utils;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.DigestUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author nommpp
 * @date 2024/5/4 0004
 */
public class SecUtil {
    public static String password(String password, String salt) {
        return SecureUtil.hmacMd5(password + salt).digestHex(salt);
    }
    
    public static boolean equals(String password, String salt, String dbPassword) {
        return StringUtils.equals(password(password, salt), dbPassword);
    }
    
    public static String getSalt() {
        return RandomUtil.randomString(11);
    }
    
    public static void main(String[] args) {
        System.out.println(DigestUtil.md5Hex("123456"));
    }
    
}

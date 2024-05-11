package org.lybaobei.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;
import org.lybaobei.custom.CusotmUser;
import org.lybaobei.entity.SystemUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author nommpp
 * @date 2024/5/3 0003
 */
public class MyBatisMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CusotmUser cusotmUser = (CusotmUser) authentication.getPrincipal();
        SystemUser user = cusotmUser.getSystemUser();
        this.strictInsertFill(metaObject,"createTime", Date.class,new Date());
        this.strictInsertFill(metaObject,"createUserId",String.class, user.getUserId());
        
    }
    
    @Override
    public void updateFill(MetaObject metaObject) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CusotmUser cusotmUser = (CusotmUser) authentication.getPrincipal();
        SystemUser user = cusotmUser.getSystemUser();
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("updateUserId",user.getUserId(),metaObject);
        //this.strictUpdateFill(metaObject,"updateTime",Date.class,new Date());
        //this.strictUpdateFill(metaObject,"updateUserId",String.class,"111");
    }
}

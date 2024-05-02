package org.lybaobei.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 * @author nommpp
 * @date 2024/5/3 0003
 */
public class MyBatisMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject,"createTime", Date.class,new Date());
        this.strictInsertFill(metaObject,"createUserId",String.class,"111");
        
    }
    
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("updateUserId","222",metaObject);
        //this.strictUpdateFill(metaObject,"updateTime",Date.class,new Date());
        //this.strictUpdateFill(metaObject,"updateUserId",String.class,"111");
    }
}

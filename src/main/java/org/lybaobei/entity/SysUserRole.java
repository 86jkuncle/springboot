package org.lybaobei.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * @author nommpp
 * @date 2024/5/4 0004
 */
@TableName("system_user_role")
@Data
public class SysUserRole {
   
    private String userId;

    private Integer roleId;
    @TableField(fill = FieldFill.INSERT)
    private String createUserId;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
}

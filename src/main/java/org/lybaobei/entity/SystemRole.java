package org.lybaobei.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@TableName("system_role")
@Data
public class SystemRole {
    @TableId(type = IdType.AUTO)
    private Integer roleId;

    private String roleName;
    
    private Integer roleStatus;
    
    private Integer seq;
    
    @TableField(fill = FieldFill.INSERT)
    private String createUserId;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.UPDATE)
    private String updateUserId;
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;
    private String roleDesc;
}

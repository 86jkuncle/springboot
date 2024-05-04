package org.lybaobei.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author nommpp
 * @date 2024/5/4 0004
 */
@TableName("system_role_menu")
@Data
public class SysRoleMenu {
    
    private Integer roleId;
    private Integer menuId;
    
    @TableField(fill = FieldFill.INSERT)
    private String createUserId;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
}

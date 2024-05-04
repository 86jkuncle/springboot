package org.lybaobei.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.lybaobei.common.Constants;

import java.util.Date;
import java.util.List;

/**
 * @author nommpp
 * @date 2024/5/3 0003
 */
@TableName("system_menu")
@Data
public class SystemMenu {
    
    @TableId(type = IdType.AUTO)
    private Integer menuId;
    private Integer parentId;
    private String menuName;
    private Integer menuStatus = Constants.MenuStatus.NORMAL;
    private Integer menuType;
    private Integer menuSource;
    private String menuAddr;
    private String component;
    private String perms;
    private String menuIcon;
    private Integer seq;
    @TableField(fill = FieldFill.INSERT)
    private String createUserId;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.UPDATE)
    private String updateUserId;
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;
    private String menuDesc;
    
    @TableField(exist = false)
    private List<SystemMenu> children;
    
    @TableField(exist = false)
    private Boolean isSelected;
    
    @TableField(exist = false)
    private Boolean parent;
}

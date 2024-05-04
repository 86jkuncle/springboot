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
@TableName("system_org")
@Data
public class SystemOrg {
    @TableId(type = IdType.AUTO)
    private Integer orgId;
    private Integer parentId;
    private String orgName;
    private Integer orgStatus = Constants.OrgStatus.NORMAL;
    private Integer seq;
    @TableField(fill = FieldFill.INSERT)
    private String createUserId;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.UPDATE)
    private String updateUserId;
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;
    private String orgDesc;
    
    @TableField(exist = false)
    private List<SystemOrg> children;
    
    @TableField(exist = false)
    private Boolean isSelected;
    
}

package org.lybaobei.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.lybaobei.common.Constants;

import java.util.Date;

/**
 * @author nommpp
 * @date 2024/5/3 0003
 */
@TableName("system_user")
@Data
public class SystemUser {
    
    @TableId
    private String userId;
    private Integer orgId;
    private String userName;
    private Integer type;
    private String phone;
    private String pwd;
    private String salt;
    private Integer userStatus = Constants.UserStatus.NORMAL;
    private Integer lockCnt;
    @TableField(fill = FieldFill.INSERT)
    private String createUserId;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.UPDATE)
    private String updateUserId;
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;
    private String userDesc;
}

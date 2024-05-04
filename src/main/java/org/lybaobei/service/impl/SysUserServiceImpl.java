package org.lybaobei.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lybaobei.common.Constants;
import org.lybaobei.dto.PageDTO;
import org.lybaobei.entity.SystemUser;
import org.lybaobei.mapper.SysUserMapper;
import org.lybaobei.service.SysUserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author nommpp
 * @date 2024/5/3 0003
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SystemUser>
    implements SysUserService {
    @Override
    public IPage<SystemUser> listEffective(PageDTO pageDTO, SystemUser systemUser) {
        Page<SystemUser> pageParam = new Page<>(pageDTO.getPage(),pageDTO.getLimit());
        QueryWrapper<SystemUser> wrapper = new QueryWrapper<>();
        wrapper.lt("user_status",Constants.UserStatus.INVALID);
        if(systemUser.getOrgId() !=null && systemUser.getOrgId() != 0){
            wrapper.eq("org_id",systemUser.getOrgId());
        }
        if(systemUser.getUserName() != null && systemUser.getUserName() != ""){
            wrapper.like("user_name",systemUser.getUserName());
        }
        if(systemUser.getType() != null && systemUser.getType() != 0){
            wrapper.eq("type",systemUser.getType());
        }
        if(systemUser.getPhone() != null && systemUser.getPhone() != ""){
            wrapper.like("phone",systemUser.getPhone());
        }
        return baseMapper.selectPage(pageParam,wrapper);
    }
    
    @Override
    public void saveUser(SystemUser user) {
        user.setUserId(UUID.randomUUID().toString().replace("-",""));
        baseMapper.insert(user);
    }
    
    @Override
    public void removeUserById(String userId) {
        SystemUser user = baseMapper.selectById(userId);
        user.setUserStatus(Constants.UserStatus.INVALID);
        baseMapper.updateById(user);
    }
    
    @Override
    public void changeUserStatus(String userId, Integer status) {
        SystemUser user = baseMapper.selectById(userId);
        user.setUserStatus(status);
        baseMapper.updateById(user);
    }
}

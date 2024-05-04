package org.lybaobei.service.impl;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lybaobei.common.Constants;
import org.lybaobei.dto.AssginRoleDTO;
import org.lybaobei.dto.PageDTO;
import org.lybaobei.entity.SysUserRole;
import org.lybaobei.entity.SystemRole;
import org.lybaobei.entity.SystemUser;
import org.lybaobei.mapper.SysRoleMapper;
import org.lybaobei.mapper.SysUserRoleMapper;
import org.lybaobei.service.SysRoleServices;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SysRoleServicesImpl extends ServiceImpl<SysRoleMapper,SystemRole>
        implements SysRoleServices {
    
    @Resource
    private SysUserRoleMapper sysUserRoleMapper;
    
    @Override
    public IPage<SystemRole> listEffective(PageDTO pageDTO, SystemRole systemRole) {
        Page<SystemRole> pageParam = new Page<>(pageDTO.getPage(),pageDTO.getLimit());
        QueryWrapper<SystemRole> wrapper = new QueryWrapper<>();
        wrapper.eq("role_status", Constants.RoleStatus.NORMAL);
        if(systemRole.getRoleName() !=null && systemRole.getRoleName() != ""){
            wrapper.like("role_name",systemRole.getRoleName());
        }
        
        return baseMapper.selectPage(pageParam,wrapper);
    }
    
    @Override
    public void removeRoleById(String roleId) {
        SystemRole role = baseMapper.selectById(roleId);
        role.setRoleStatus(Constants.RoleStatus.INVALID);
        baseMapper.updateById(role);
    }
    
    @Override
    public Map<String, Object> getRolesByUserId(String userId) {
        QueryWrapper<SystemRole> wrapper = new QueryWrapper<>();
        wrapper.eq("role_status", Constants.RoleStatus.NORMAL);
        List<SystemRole> roles = baseMapper.selectList(wrapper);
        
        QueryWrapper<SysUserRole> userRoleWrapper = new QueryWrapper<>();
        userRoleWrapper.eq("user_id",userId);
        List<SysUserRole> sysUserRoles = sysUserRoleMapper.selectList(userRoleWrapper);
        
        List<Integer> userRoleList = sysUserRoles.stream()
                .map(SysUserRole::getRoleId).collect(Collectors.toList());
        
        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("allRoles",roles);
        returnMap.put("userRoleIds",userRoleList);
        return returnMap;
    }
    
    @Override
    public void doAssign(AssginRoleDTO assginRoleDTO) {
        QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",assginRoleDTO.getUserId());
        sysUserRoleMapper.delete(wrapper);
        
        assginRoleDTO.getRoleIdList().forEach(item->{
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(assginRoleDTO.getUserId());
            sysUserRole.setRoleId(item);
            sysUserRoleMapper.insert(sysUserRole);
        });
    }
}

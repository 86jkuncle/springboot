package org.lybaobei.service.impl;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lybaobei.common.Constants;
import org.lybaobei.dto.PageDTO;
import org.lybaobei.entity.SystemMenu;
import org.lybaobei.entity.SystemUser;
import org.lybaobei.exception.APIException;
import org.lybaobei.mapper.SysUserMapper;
import org.lybaobei.service.SysMenuService;
import org.lybaobei.service.SysUserService;
import org.lybaobei.utils.SecUtil;
import org.lybaobei.vo.RouterVO;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author nommpp
 * @date 2024/5/3 0003
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SystemUser>
    implements SysUserService {
    
    @Resource
    private SysMenuService sysMenuService;
    
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
        //String salt = SecUtil.getSalt();
        //user.setPwd(SecUtil.password(user.getPwd(),salt));
        //user.setSalt(salt);
        user.setPwd(DigestUtil.md5Hex(user.getPwd()));
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
    
    @Override
    public SystemUser getByName(String userName) {
        QueryWrapper<SystemUser> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name",userName);
        wrapper.lt("user_status",Constants.UserStatus.INVALID);
        return baseMapper.selectOne(wrapper);
    }
    
    @Override
    public Map<String, Object> getUserInfo(String userId) {
        SystemUser user = baseMapper.selectById(userId);
        if(user == null){
            throw new APIException("用户不存在");
            
        }
        
        List<RouterVO> userMenuList = sysMenuService.getUserMenuByUserId(userId);
        
        List<String> permsList = sysMenuService.getUserButtonPermsList(userId);
        
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("roles","[admin]");
        resultMap.put("avatar","http://xxx.com/x.jpg");
        resultMap.put("name",user.getUserName());
        resultMap.put("routes",userMenuList);
        resultMap.put("buttons",permsList);
        
        return resultMap;
    }

    @Override
    public void incrementLockCnt(String userName) {
        SystemUser user = getByName(userName);
        if (user != null) {
            if(user.getLockCnt() == Constants.UserStatus.LOCKCNT){
                throw new LockedException("用户账号已被锁定，请联系管理员解锁");
            }

            user.setLockCnt(user.getLockCnt() + 1);
            updateById(user);
        }else{
            throw new UsernameNotFoundException("用户名或密码错误");
        }
    }

    @Override
    public void resetLockCnt(String userName) {
        SystemUser user = getByName(userName);
        user.setLockCnt(0);
        updateById(user);
    }
}

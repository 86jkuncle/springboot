package org.lybaobei.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lybaobei.common.Constants;
import org.lybaobei.dto.AssginMenuDTO;
import org.lybaobei.dto.PageDTO;
import org.lybaobei.entity.SysRoleMenu;
import org.lybaobei.entity.SystemMenu;
import org.lybaobei.entity.SystemOrg;
import org.lybaobei.exception.APIException;
import org.lybaobei.mapper.SysMenuMapper;
import org.lybaobei.mapper.SysRoleMenuMapper;
import org.lybaobei.service.SysMenuService;
import org.lybaobei.utils.TreeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author nommpp
 * @date 2024/5/3 0003
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SystemMenu>
    implements SysMenuService {
    
    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;
    
    @Override
    public List<SystemMenu> findNodes() {
        return null;
    }
    
    @Override
    public IPage<SystemMenu> listEffective(PageDTO page, SystemMenu systemMenu) {
        Page<SystemMenu> pageParam = new Page<>(page.getPage(),page.getLimit());
        QueryWrapper<SystemMenu> wrapper = new QueryWrapper<>();
        if(systemMenu.getMenuName() !=null && systemMenu.getMenuName() != ""){
            wrapper.like("menu_name",systemMenu.getMenuName());
        }
        wrapper.eq("menu_status", Constants.MenuStatus.NORMAL);
        IPage<SystemMenu> list = baseMapper.selectPage(pageParam,wrapper);
        List<SystemMenu> menuList = list.getRecords();
        list.setRecords(TreeUtil.buildMenuTree(menuList));
        return list;
    }
    
    @Override
    public List<SystemMenu> listParent() {
        QueryWrapper<SystemMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("menu_status", Constants.MenuStatus.NORMAL);
        return baseMapper.selectList(wrapper);
    }
    
    @Override
    public void removeByMenuId(Integer id) {
        QueryWrapper<SystemMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",id);
        Long aLong = baseMapper.selectCount(wrapper);
        if(aLong > 0){
            throw new APIException(500,"请先删除子菜单");
        }
    
        SystemMenu systemMenu = baseMapper.selectById(id);
        systemMenu.setMenuStatus(Constants.MenuStatus.INVALID);
        baseMapper.updateById(systemMenu);
        
    }
    
    @Override
    public List<SystemMenu> findMenuByRoleId(Integer roleId) {
        QueryWrapper<SystemMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("menu_status",Constants.MenuStatus.NORMAL);
        List<SystemMenu> allMenus = baseMapper.selectList(wrapper);
        
        QueryWrapper<SysRoleMenu> roleMenuWrapper = new QueryWrapper<>();
        roleMenuWrapper.eq("role_id",roleId);
        
        List<SysRoleMenu> roleMenus = sysRoleMenuMapper.selectList(roleMenuWrapper);
        List<Integer> roleMenuIds = roleMenus.stream()
                .map(SysRoleMenu::getMenuId).collect(Collectors.toList());
        
        allMenus.forEach(item->{
            if (roleMenuIds.contains(item.getMenuId())) {
                item.setIsSelected(true);
            }else{
                item.setIsSelected(false);
            }
            
            if(item.getParentId() != 0){
                item.setParent(true);
            }else{
                item.setParent(false);
            }
        });
        
        return TreeUtil.buildMenuTree(allMenus);
    }
    
    @Override
    public void doAssign(AssginMenuDTO assginMenuDTO) {
        QueryWrapper<SysRoleMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id",assginMenuDTO.getRoleId());
        sysRoleMenuMapper.delete(wrapper);
    
        assginMenuDTO.getMenuIdList().forEach(item->{
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setRoleId(assginMenuDTO.getRoleId());
            sysRoleMenu.setMenuId(item);
            sysRoleMenuMapper.insert(sysRoleMenu);
        });
    }
}

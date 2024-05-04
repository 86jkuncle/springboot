package org.lybaobei.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lybaobei.common.Constants;
import org.lybaobei.dto.PageDTO;
import org.lybaobei.entity.SystemOrg;
import org.lybaobei.enumpkg.ResultCodeEnum;
import org.lybaobei.exception.APIException;
import org.lybaobei.mapper.SysOrgMapper;
import org.lybaobei.service.SysOrgService;
import org.lybaobei.utils.TreeUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author nommpp
 * @date 2024/5/3 0003
 */
@Service
public class SysOrgServiceImpl extends ServiceImpl<SysOrgMapper, SystemOrg>
    implements SysOrgService {
    @Override
    public IPage<SystemOrg> listEffective(PageDTO page, String orgName) {
        Page<SystemOrg> pageParam = new Page<>(page.getPage(),page.getLimit());
        QueryWrapper<SystemOrg> wrapper = new QueryWrapper<>();
        if(orgName !=null && orgName != ""){
            wrapper.like("org_name",orgName);
        }
        wrapper.eq("org_status",Constants.OrgStatus.NORMAL);
        IPage<SystemOrg> list = baseMapper.selectPage(pageParam,wrapper);
        List<SystemOrg> orgList = list.getRecords();
        list.setRecords(TreeUtil.buildOrgTree(orgList));
        return list;
    }
    
    @Override
    public List<SystemOrg> listParent() {
        QueryWrapper<SystemOrg> wrapper = new QueryWrapper<>();
        wrapper.eq("org_status",Constants.OrgStatus.NORMAL);
        wrapper.eq("parent_id",0);
        return baseMapper.selectList(wrapper);
    }
    
    @Override
    public void removeOrgById(Integer orgId) {
        QueryWrapper<SystemOrg> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",orgId);
        Long aLong = baseMapper.selectCount(wrapper);
        if(aLong > 0){
            throw new APIException(500,"请先删除子机构");
        }
        
        SystemOrg systemOrg = baseMapper.selectById(orgId);
        systemOrg.setOrgStatus(Constants.OrgStatus.INVALID);
        baseMapper.updateById(systemOrg);
    
    }
    
    @Override
    public List<SystemOrg> listAllEffective() {
        QueryWrapper<SystemOrg> wrapper = new QueryWrapper<>();
        wrapper.eq("org_status",Constants.OrgStatus.NORMAL);
        return baseMapper.selectList(wrapper);
    }
}

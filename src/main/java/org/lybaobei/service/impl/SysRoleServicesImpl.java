package org.lybaobei.service.impl;



import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lybaobei.entity.SystemRole;
import org.lybaobei.mapper.SysRoleMapper;
import org.lybaobei.service.SysRoleServices;

import org.springframework.stereotype.Service;

@Service
public class SysRoleServicesImpl extends ServiceImpl<SysRoleMapper,SystemRole>
        implements SysRoleServices {
}

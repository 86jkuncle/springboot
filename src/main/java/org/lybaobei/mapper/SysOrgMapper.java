package org.lybaobei.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.lybaobei.dto.PageDTO;
import org.lybaobei.entity.SystemOrg;

import java.util.List;

/**
 * @author nommpp
 * @date 2024/5/3 0003
 */
@Mapper
public interface SysOrgMapper extends BaseMapper<SystemOrg> {

}

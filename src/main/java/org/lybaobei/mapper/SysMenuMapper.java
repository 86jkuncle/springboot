package org.lybaobei.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.lybaobei.entity.SystemMenu;

import java.util.List;

/**
 * @author nommpp
 * @date 2024/5/3 0003
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SystemMenu> {
    List<SystemMenu> findMenuListByUserId(@Param("userId") String userId);
}

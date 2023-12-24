package org.lybaobei.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.lybaobei.entity.Users;

@Mapper
public interface UserMapper extends BaseMapper<Users> {
}

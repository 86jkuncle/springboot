package org.lybaobei.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.lybaobei.entity.User;


@Mapper
public interface UserMapper {
	User findByUserName(String userName);
}

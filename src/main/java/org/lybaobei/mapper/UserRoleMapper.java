package org.lybaobei.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.lybaobei.entity.Role;

@Mapper
public interface UserRoleMapper {
	
	List<Role> findByUserName(String userName);
}

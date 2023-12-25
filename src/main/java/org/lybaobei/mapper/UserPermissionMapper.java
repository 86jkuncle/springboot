package org.lybaobei.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.lybaobei.entity.Permission;


@Mapper
public interface UserPermissionMapper {
	
	List<Permission> findByUserName(String userName);
}

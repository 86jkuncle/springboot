package org.lybaobei.dto;

import lombok.Data;

import java.util.List;

/**
 * @author nommpp
 * @date 2024/5/4 0004
 */
@Data
public class AssginRoleDTO {
    
    private String userId;
    
    private List<Integer> roleIdList;
}

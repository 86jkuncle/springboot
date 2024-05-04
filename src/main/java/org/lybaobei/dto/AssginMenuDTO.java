package org.lybaobei.dto;

import lombok.Data;

import java.util.List;

/**
 * @author nommpp
 * @date 2024/5/4 0004
 */
@Data
public class AssginMenuDTO {
    
    private Integer roleId;
    
    private List<Integer> menuIdList;
}

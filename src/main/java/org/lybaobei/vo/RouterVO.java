package org.lybaobei.vo;

import lombok.Data;

import java.util.List;

/**
 * @author nommpp
 * @date 2024/5/4 0004
 */
@Data
public class RouterVO {
    private String path;
    private boolean hidden;
    private String component;
    private boolean alwaysShow;
    private MetaVO meta;
    private List<RouterVO> children;

}

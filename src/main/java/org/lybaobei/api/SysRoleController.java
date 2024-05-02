package org.lybaobei.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.lybaobei.service.SysRoleServices;
import org.lybaobei.vo.ResultVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author nommpp
 * @date 2024/5/3 0003
 */
@Api(tags = "用户角色管理")
@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {
    
    @Resource
    private SysRoleServices sysRoleServices;
    
    @ApiOperation("查询所有角色")
    @GetMapping("/findAll")
    public ResultVO findAll(){
        return ResultVO.success(sysRoleServices.list());
    }
}

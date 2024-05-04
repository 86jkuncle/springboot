package org.lybaobei.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.lybaobei.dto.AssginRoleDTO;
import org.lybaobei.dto.PageDTO;
import org.lybaobei.entity.SystemRole;
import org.lybaobei.entity.SystemUser;
import org.lybaobei.service.SysRoleServices;
import org.lybaobei.vo.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author nommpp
 * @date 2024/5/3 0003
 */
@Api(tags = "角色管理")
@RestController
@RequestMapping("/admin/system/sysrole")
public class SysRoleController {
    
    @Resource
    private SysRoleServices sysRoleServices;
    
    @ApiOperation("查询所有角色")
    @GetMapping("/list")
    public Result list(PageDTO pageDTO, SystemRole systemRole){
        return Result.success(sysRoleServices.listEffective(pageDTO, systemRole));
    }
    
    @ApiOperation("新增角色")
    @PostMapping("/add")
    public Result add(@RequestBody SystemRole systemRole){
        sysRoleServices.save(systemRole);
        return Result.success();
    }
    
    @ApiOperation("根据ID查询角色")
    @GetMapping("/findByRoleId/{id}")
    public Result findbyOrgId(@PathVariable(value = "id") String roleId){
        return Result.success(sysRoleServices.getById(roleId));
    }
    
    @ApiOperation("修改角色")
    @PostMapping("/edit")
    public Result edit(@RequestBody SystemRole systemRole){
        sysRoleServices.updateById(systemRole);
        return Result.success();
    }
    
    @ApiOperation("删除角色")
    @DeleteMapping("/del/{id}")
    public Result del(@PathVariable(value = "id") String roleId){
        sysRoleServices.removeRoleById(roleId);
        return Result.success();
    }
    
    @ApiOperation("根据用户获得角色数据")
    @GetMapping("/toAssign/{userId}")
    public Result toAssign(@PathVariable String userId){
        Map<String,Object> roleMap = sysRoleServices.getRolesByUserId(userId);
        return Result.success(roleMap);
    }
    
    @ApiOperation("为用户分配角色")
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginRoleDTO assginRoleDTO){
        sysRoleServices.doAssign(assginRoleDTO);
        return Result.success();
    }
}

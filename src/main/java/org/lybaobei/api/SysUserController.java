package org.lybaobei.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.lybaobei.dto.PageDTO;
import org.lybaobei.entity.SystemUser;
import org.lybaobei.service.SysUserService;
import org.lybaobei.vo.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author nommpp
 * @date 2024/5/3 0003
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/admin/system/sysuser")
public class SysUserController {
    
    @Resource
    private SysUserService sysUserService;
    
    @ApiOperation("获得所有用户")
    @GetMapping("/list")
    public Result list(PageDTO pageDTO, SystemUser systemUser){
        return Result.success(sysUserService.listEffective(pageDTO, systemUser));
    }
    
    
    @ApiOperation("新增用户")
    @PostMapping("/add")
    public Result add(@RequestBody SystemUser systemUser){
        sysUserService.saveUser(systemUser);
        return Result.success();
    }
    
    @ApiOperation("根据ID查询用户")
    @GetMapping("/findByUserId/{id}")
    public Result findbyOrgId(@PathVariable(value = "id") String userId){
        return Result.success(sysUserService.getById(userId));
    }
    
    @ApiOperation("修改用户")
    @PostMapping("/edit")
    public Result edit(@RequestBody SystemUser systemUser){
        sysUserService.updateById(systemUser);
        return Result.success();
    }
    
    @ApiOperation("删除用户")
    @DeleteMapping("/del/{id}")
    public Result del(@PathVariable(value = "id") String userId){
        sysUserService.removeUserById(userId);
        return Result.success();
    }
    
    @ApiOperation("更新用户状态")
    @GetMapping("/status/{id}/{status}")
    public Result userStatus(@PathVariable(value = "id") String userId,@PathVariable("status") Integer status){
        sysUserService.changeUserStatus(userId,status);
        return Result.success();
    }
}

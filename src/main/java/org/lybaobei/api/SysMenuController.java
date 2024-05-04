package org.lybaobei.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.lybaobei.dto.AssginMenuDTO;
import org.lybaobei.dto.PageDTO;
import org.lybaobei.entity.SystemMenu;
import org.lybaobei.service.SysMenuService;
import org.lybaobei.vo.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author nommpp
 * @date 2024/5/3 0003
 */
@Api(tags = "菜单管理")
@RestController
@RequestMapping("/admin/system/sysmenu")
public class SysMenuController {
    
    @Resource
    private SysMenuService sysMenuService;
    
    @ApiOperation("获得所有菜单")
    @GetMapping("/listAll")
    public Result listAll(){
        return Result.success(sysMenuService.findNodes());
    }
    
    @ApiOperation("分页获得所有有效菜单")
    @GetMapping("/list")
    public Result list(PageDTO pageDTO, SystemMenu systemMenu){
        return Result.success(sysMenuService.listEffective(pageDTO,systemMenu));
    }
    
    @ApiOperation("获得所有有效的父菜单")
    @GetMapping("/listParent")
    public Result listParent(){
        return Result.success(sysMenuService.listParent());
    }
    
    @ApiOperation("添加菜单")
    @PostMapping("/add")
    public Result add(@RequestBody SystemMenu systemMenu){
        sysMenuService.save(systemMenu);
        return Result.success();
    }
    
    @ApiOperation("根据ID查询")
    @GetMapping("/findById/{id}")
    public Result findById(@PathVariable("id") Integer id){
        return Result.success(sysMenuService.getById(id));
    }
    
    @ApiOperation("修改菜单")
    @PostMapping("/edit")
    public Result edit(@RequestBody SystemMenu systemMenu){
        sysMenuService.updateById(systemMenu);
        return Result.success();
    }
    
    @ApiOperation("删除菜单")
    @DeleteMapping("/del/{id}")
    public Result del(@PathVariable("id") Integer id){
        sysMenuService.removeByMenuId(id);
        return Result.success();
    }
    
    @ApiOperation("根据角色获取菜单")
    @GetMapping("/toAssign/{roleId}")
    public Result toAssign(@PathVariable Integer roleId){
        List<SystemMenu> list = sysMenuService.findMenuByRoleId(roleId);
        return Result.success(list);
    }
    
    @ApiOperation("给角色分配菜单")
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginMenuDTO assginMenuDTO){
        sysMenuService.doAssign(assginMenuDTO);
        return Result.success();
    }
}

package org.lybaobei.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.lybaobei.dto.PageDTO;
import org.lybaobei.entity.SystemOrg;
import org.lybaobei.service.SysOrgService;
import org.lybaobei.vo.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author nommpp
 * @date 2024/5/3 0003
 */
@Api(tags = "机构管理")
@RestController
@RequestMapping("/admin/system/sysorg")
public class SysOrgController {
    
    @Resource
    private SysOrgService sysOrgService;
    
    @ApiOperation("获得所有有效的机构")
    @GetMapping("/listAll")
    public Result listAll(){
        return Result.success(sysOrgService.listAllEffective());
    }
    
    @ApiOperation("分页获得所有有效的机构")
    @GetMapping("/list")
    public Result list(PageDTO pageDTO,String orgName){
        return Result.success(sysOrgService.listEffective(pageDTO,orgName));
    }
    
    @ApiOperation("获得所有有效的父机构")
    @GetMapping("/listParent")
    public Result listParent(){
        return Result.success(sysOrgService.listParent());
    }
    
    @ApiOperation("新增机构")
    @PostMapping("/add")
    public Result add(@RequestBody SystemOrg systemOrg){
        sysOrgService.save(systemOrg);
        return Result.success();
    }
    
    @ApiOperation("根据ID查询机构")
    @GetMapping("/findByOrgId/{id}")
    public Result findbyOrgId(@PathVariable(value = "id") Integer orgId){
        return Result.success(sysOrgService.getById(orgId));
    }
    
    @ApiOperation("修改机构")
    @PostMapping("/edit")
    public Result edit(@RequestBody SystemOrg systemOrg){
        sysOrgService.updateById(systemOrg);
        return Result.success();
    }
    
    @ApiOperation("删除机构")
    @DeleteMapping("/del/{id}")
    public Result del(@PathVariable(value = "id") Integer orgId){
        sysOrgService.removeOrgById(orgId);
        return Result.success();
    }
}

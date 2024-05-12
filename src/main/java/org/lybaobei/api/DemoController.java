package org.lybaobei.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.security.Principal;
import java.util.List;
import org.lybaobei.common.Constants;
import org.lybaobei.entity.SystemRole;
import org.lybaobei.entity.SystemUser;
import org.lybaobei.service.RedisOperator;
import org.lybaobei.service.SysMenuService;
import org.lybaobei.service.SysRoleServices;
import org.lybaobei.service.SysUserService;
import org.lybaobei.utils.RedisUtil;
import org.lybaobei.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "测试接口管理")
@RestController
@RequestMapping("/demo")
public class DemoController {

    private final SysRoleServices userServices;

    private final RedisOperator redisOperator;

    @Autowired
    private SysUserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    public DemoController(SysRoleServices userServices, RedisOperator redisOperator){
        this.userServices = userServices;
        this.redisOperator = redisOperator;
    }

    @ApiOperation("获得登录用户信息")
    @GetMapping("/principal")
    public Result getPrincipal(Principal principal){
        return Result.success(principal);
    }

    @ApiOperation("使用redistemp")
    @GetMapping("/redistemp")
    public Result useRedisTemp(){
        SystemUser user = userService.getById("abcde");
//        redisOperator.setObject("user", user, 100000);
        redisTemplate.opsForValue().set("user",user);
        return Result.success();
    }

    @ApiOperation("使用redisutil")
    @GetMapping("/redisutil")
    public Result useRedisUtil(){
        SystemUser user = userService.getById("abcde");
//        redisOperator.setObject("user", user, 100000);
        redisUtil.set("aaa", user);
        return Result.success();
    }

    @GetMapping("hello")
    public String hello(Model model){
        model.addAttribute("name","张三");
        return "hello";
    }

    @GetMapping("users")
    public Result<List<SystemRole>> getUsers(){
        return Result.fail();
    }

    @GetMapping("setredis")
    public Result<String> set(String key, String value){
        redisOperator.set(key, value);
        return Result.success("OK");
    }

    @GetMapping("getredis")
    public Result<String> get(String key){
        String s = redisOperator.get(key);
        return Result.success(s);
    }
}

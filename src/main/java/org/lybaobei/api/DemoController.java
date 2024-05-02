package org.lybaobei.api;

import java.util.List;
import org.lybaobei.entity.SystemRole;
import org.lybaobei.service.RedisOperator;
import org.lybaobei.service.SysRoleServices;
import org.lybaobei.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {

    private final SysRoleServices userServices;

    private final RedisOperator redisOperator;

    @Autowired
    public DemoController(SysRoleServices userServices, RedisOperator redisOperator){
        this.userServices = userServices;
        this.redisOperator = redisOperator;
    }

    @GetMapping("hello")
    public String hello(Model model){
        model.addAttribute("name","张三");
        return "hello";
    }

    @GetMapping("users")
    public ResultVO<List<SystemRole>> getUsers(){
        return ResultVO.fail();
    }

    @GetMapping("setredis")
    public ResultVO<String> set(String key,String value){
        redisOperator.set(key, value);
        return ResultVO.success("OK");
    }

    @GetMapping("getredis")
    public ResultVO<String> get(String key){
        String s = redisOperator.get(key);
        return ResultVO.success(s);
    }
}

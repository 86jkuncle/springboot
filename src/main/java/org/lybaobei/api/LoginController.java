package org.lybaobei.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.lybaobei.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author nommpp
 * @date 2024/5/3 0003
 */
@Api(tags = "登录接口")
@RestController
@RequestMapping("/admin/system/user")
public class LoginController {

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public Result login(){
        Map<String,Object> map = new HashMap<>();
        map.put("token","admin token");
        return Result.success(map);
    }
    
    @ApiOperation("获取用户信息")
    @GetMapping("/info")
    public Result info(){
        Map<String,Object> map = new HashMap<>();
        map.put("roles","[admin]");
        map.put("inroduction","i am a super admin");
        map.put("avatar","https://image.baidu.com/search/detail?ct=503316480&z=&tn=baiduimagedetail&ipn=d&word=%E5%A4%B4%E5%83%8F&step_word=&lid=9310553192009751330&ie=utf-8&in=&cl=2&lm=-1&st=-1&hd=undefined&latest=undefined&copyright=undefined&cs=1867027838,1592670021&os=410737491,47290902&simid=1867027838,1592670021&pn=0&rn=1&di=46137345&ln=1943&fr=&fmq=1714681235524_R&ic=0&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&is=0,0&istype=2&ist=&jit=&bdtype=11&spn=0&pi=0&gsm=1e&objurl=https%3A%2F%2Fq1.itc.cn%2Fq_70%2Fimages03%2F20240320%2Ffcf023d835c54f78bac6c7efc98fbb4c.jpeg&rpstart=0&rpnum=0&adpicid=0&nojc=undefined");
        map.put("name","super admin");
        return Result.success(map);
    }
}

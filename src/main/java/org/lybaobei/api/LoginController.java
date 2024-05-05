package org.lybaobei.api;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.lybaobei.common.Constants;
import org.lybaobei.dto.LoginDTO;
import org.lybaobei.entity.SystemUser;
import org.lybaobei.enumpkg.ResultCodeEnum;
import org.lybaobei.exception.APIException;
import org.lybaobei.service.SysUserService;
import org.lybaobei.utils.JWTUtil;
import org.lybaobei.utils.SecUtil;
import org.lybaobei.vo.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author nommpp
 * @date 2024/5/3 0003
 */
@Api(tags = "登录接口")
@ApiSupport(author = "409578863@qq.com")
@RestController
@RequestMapping("/admin/system/user")
public class LoginController {
    
    @Resource
    private SysUserService sysUserService;

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public Result login(@RequestBody LoginDTO loginDTO){
        SystemUser user = sysUserService.getByName(loginDTO.getUserName());
        if(user == null){
            return Result.fail(ResultCodeEnum.UNAUTHORIZED);
        }

        boolean pwd = SecUtil.equals(loginDTO.getPwd(), user.getSalt(), user.getPwd());
        if(!pwd){
            throw new APIException(400,"用户不存在");
        }

        if(user.getUserStatus().equals(Constants.UserStatus.LOCKED)){
            return Result.fail(400,"用户被锁定");
        }

        String token = JWTUtil.createToken(user.getUserId(), user.getUserName());
    
        Map<String,Object> map = new HashMap<>();
        //String token = "eyJ6aXAiOiJHWklQIiwiYWxnIjoiSFMyNTYifQ.H4sIAAAAAAAAAKtWKi5NUrJScgwN8dANDXYNUtJRSq0oULIyNDc0MzYwNrM01VEqLU4t8kwBqjIxTjI0TzQwsDAySDUxSLS0NExLTbIwMjJMNTNNsjRIVIKo9UvMTQWqft7X_WTXFKVaANPbvZFkAAAA.r9i7EARS9HAcLj3JWuYzjEbICfbz3XAzsw6xo1NlzRM";
        map.put("token",token);
        return Result.success(map);
    }
    
    @ApiOperation("获取用户信息")
    @GetMapping("/info")
    public Result info(HttpServletRequest request){
        String token = request.getHeader("token");

        if(StringUtils.isBlank(token)){
            throw new APIException("用户未登录");
        }

        String userId = JWTUtil.getUserId(token);
        Map<String,Object> resultMap = sysUserService.getUserInfo(userId);
//        Map<String,Object> resultMap = new HashMap<>();
//        resultMap.put("roles","[admin]");
//        resultMap.put("avatar","http://xxx.com/x.jpg");
//        resultMap.put("name","111");
       
        return Result.success(resultMap);
    }
}

package org.lybaobei.expHandler;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthorizedException;
import org.lybaobei.enumpkg.ResultCodeEnum;
import org.lybaobei.exception.APIException;
import org.lybaobei.vo.ResultVO;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.thymeleaf.exceptions.TemplateInputException;

@RestControllerAdvice
public class GlobalExceptionHandler {

//    此异常无法用这种方法捕获,详情
//    https://blog.csdn.net/weixin_39524183/article/details/111002999
//    @ExceptionHandler(TemplateInputException.class)
//    public <T> ResultVO<T> error(TemplateInputException e) {
//        return ResultVO.fail(ResultCodeEnum.INTERNAL_SERVER_ERROR);
//    }

    @ExceptionHandler(APIException.class)
    public  ResultVO error(APIException e){
        return ResultVO.fail(e.getStatus(),e.getMessage());
    }
    @ExceptionHandler(UnauthorizedException.class)
    public ResultVO error(UnauthorizedException e){
        return ResultVO.fail(ResultCodeEnum.FORBIDDEN);
    }

//    账户不存在
    @ExceptionHandler(UnknownAccountException.class)
    public ResultVO error(UnknownAccountException e){
        return ResultVO.fail(ResultCodeEnum.UNAUTHORIZED);
    }

    //    密码错误
    @ExceptionHandler(IncorrectCredentialsException.class)
    public ResultVO error(IncorrectCredentialsException e){
        return ResultVO.fail(ResultCodeEnum.UNAUTHORIZED);
    }

//    账户被锁定
    @ExceptionHandler(LockedAccountException.class)
    public ResultVO error(LockedAccountException e){
        return ResultVO.fail(ResultCodeEnum.LOCKEDACCOUNT);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResultVO error(AuthenticationException e){
        return ResultVO.fail(ResultCodeEnum.AUTHENTICATIONFAIL);
    }


    @ExceptionHandler(Exception.class)
    public  ResultVO error(Exception e){
        e.printStackTrace();
        return ResultVO.fail();
    }


}

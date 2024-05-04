package org.lybaobei.expHandler;

import org.lybaobei.exception.APIException;
import org.lybaobei.vo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

//    此异常无法用这种方法捕获,详情
//    https://blog.csdn.net/weixin_39524183/article/details/111002999
//    @ExceptionHandler(TemplateInputException.class)
//    public <T> Result<T> error(TemplateInputException e) {
//        return Result.fail(ResultCodeEnum.INTERNAL_SERVER_ERROR);
//    }

    @ExceptionHandler(APIException.class)
    public Result error(APIException e){
        return Result.fail(e.getStatus(),e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result error(Exception e){
        return Result.fail();
    }


}

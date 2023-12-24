package org.lybaobei.expHandler;

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

    @ExceptionHandler(Exception.class)
    public  ResultVO error(Exception e){
        return ResultVO.fail();
    }


}

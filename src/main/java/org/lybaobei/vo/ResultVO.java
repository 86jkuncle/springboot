package org.lybaobei.vo;

import org.lybaobei.enumpkg.ResultCodeEnum;

public class ResultVO<T> {
    private final int status;
    private final String message;

    private final T data;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    private ResultVO(int status, String message) {
        this.status = status;
        this.message = message;
        this.data = null;
    }

    private ResultVO(ResultCodeEnum resultCodeEnum, T data) {
        this.status = resultCodeEnum.getStatus();
        this.message = resultCodeEnum.getMessage();
        this.data = data;
    }

    private ResultVO(ResultCodeEnum resultCodeEnum){
        this.status = resultCodeEnum.getStatus();
        this.message = resultCodeEnum.getMessage();
        this.data = null;
    }

    public static <T> ResultVO<T> success(T data){
        return new ResultVO<>(ResultCodeEnum.SUCCESS,data);
    }

    public static ResultVO fail(ResultCodeEnum resultCodeEnum){
        return new ResultVO<>(resultCodeEnum);
    }

    public static  ResultVO fail(){
        return new ResultVO<>(ResultCodeEnum.UNKNOWN_ERROR);
    }

    public static  ResultVO fail(int status,String message){
        return new ResultVO<>(status,message);
    }

}

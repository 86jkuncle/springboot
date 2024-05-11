package org.lybaobei.vo;

import org.lybaobei.enumpkg.ResultCodeEnum;

public class Result<T> {
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

    private Result(int status, String message) {
        this.status = status;
        this.message = message;
        this.data = null;
    }

    private Result(ResultCodeEnum resultCodeEnum, T data) {
        this.status = resultCodeEnum.getStatus();
        this.message = resultCodeEnum.getMessage();
        this.data = data;
    }

    private Result(ResultCodeEnum resultCodeEnum){
        this.status = resultCodeEnum.getStatus();
        this.message = resultCodeEnum.getMessage();
        this.data = null;
    }
    
    public static <T> Result<T> success(){
        return new Result<>(ResultCodeEnum.SUCCESS,null);
    }

    public static <T> Result<T> success(T data){
        return new Result<>(ResultCodeEnum.SUCCESS,data);
    }

    public static Result success(ResultCodeEnum resultCodeEnum){
        return new Result<>(resultCodeEnum);
    }

    public static Result fail(ResultCodeEnum resultCodeEnum){
        return new Result<>(resultCodeEnum);
    }


    public static Result fail(){
        return new Result<>(ResultCodeEnum.UNKNOWN_ERROR);
    }

    public static Result fail(int status, String message){
        return new Result<>(status,message);
    }

}

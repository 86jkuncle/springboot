package org.lybaobei.exception;

import org.lybaobei.enumpkg.ResultCodeEnum;

public class APIException extends RuntimeException{
    private final int status;

    public int getStatus() {
        return status;
    }
    
    public APIException(String message){
        super(message);
        this.status = 400;
        
    }

    public APIException(int status, String message){
        super(message);
        this.status = status;
    }

    public APIException(ResultCodeEnum resultCodeEnum){
        super(resultCodeEnum.getMessage());
        this.status = resultCodeEnum.getStatus();
    }
}

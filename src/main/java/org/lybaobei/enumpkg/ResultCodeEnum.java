package org.lybaobei.enumpkg;
public enum ResultCodeEnum {

    SUCCESS(200,"OK"),

    BAD_REQUEST(400,"服务器不理解客户端的请求，未做任何处理"),
    UNAUTHORIZED(401,"用户未提供身份验证凭据"),

    FORBIDDEN(403,"不具有访问资源所需的权限"),

    NOT_FOUND(404,"所请求的资源不存在，或不可用"),

    METHOD_NOT_ALLOWED(405,"HTTP方法不在权限之内"),

    GONE(410,"所请求的资源已从这个地址转移，不再可用"),

    UNSUPPORTED_MEDIA_TYPE(415,"客户端要求的返回格式不支持"),
    UNPROCESSABLE_ENTITY(422,"客户端上传的附件无法处理"),
    TOO_MANY_REQUESTS(429,"客户端的请求次数超过限额"),
    INTERNAL_SERVER_ERROR(500,"客户端请求有效，服务器处理时发生了意外"),
    SERVICE_UNAVAILABLE(503,"服务器正在维护"),
    UNKNOWN_ERROR(500,"未知错误"),
    ;
    private final int status;
    private final String message;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    ResultCodeEnum(int status, String message) {
        this.status = status;
        this.message = message;
    }
}

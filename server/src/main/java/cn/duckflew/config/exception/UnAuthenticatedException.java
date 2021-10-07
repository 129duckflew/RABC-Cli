package cn.duckflew.config.exception;

public class UnAuthenticatedException extends RuntimeException
{
    public UnAuthenticatedException() {
        super("未登录");
    }
}

package cn.duckflew.config.exception;

/**
 * 权限不足的异常
 */
public class UnAuthorizationException extends RuntimeException
{
    public UnAuthorizationException() {
        super("用户无此接口权限");
    }
}

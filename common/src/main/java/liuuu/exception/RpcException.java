package liuuu.exception;

import liuuu.enums.RpcResponseCodeEnum;

/**
 * 处理Rcp运行时的异常
 */
public class RpcException extends RuntimeException {
    public RpcException(RpcResponseCodeEnum rpcResponseCodeEnum, String detail) {
        super(rpcResponseCodeEnum.getMessage() + ": " + detail);
    }

    public RpcException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcException(RpcResponseCodeEnum rpcResponseCodeEnum) {
        super(rpcResponseCodeEnum.getMessage());
    }

}

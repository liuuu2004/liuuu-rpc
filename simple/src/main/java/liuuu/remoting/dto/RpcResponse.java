package liuuu.remoting.dto;

import lombok.*;

import java.io.Serializable;
import liuuu.enums.RpcResponseCodeEnum;

/**
 * Rpc响应实体类，将调用结果返回客户端时传输此类
 * @param <T>
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Setter
@Getter
public class RpcResponse<T> implements Serializable {
    private static final long serialVersionUid = 715745410605631233L;

    private String requestId;

    // response code
    private Integer code;

    // response message
    private String message;

    // response body
    private T data;

    /**
     * 远程调用成功
     * @param data
     * @param requestId
     * @return
     * @param <T>
     */
    public static <T> RpcResponse success(T data, String requestId) {
        // 设置返回值的各项参数
        RpcResponse<T> response = new RpcResponse<>();
        response.setCode(RpcResponseCodeEnum.SUCCESS.getCode());
        response.setMessage(RpcResponseCodeEnum.SUCCESS.getMessage());
        response.setRequestId(requestId);
        if (data != null) {
            response.setData(data);
        }
        return response;
    }

    /**
     * 远程调用失败
     * @param data
     * @param requestId
     * @return
     * @param <T>
     */
    public static <T> RpcResponse fail(T data, String requestId) {
        // 设置返回值的各项参数
        RpcResponse<T> response = new RpcResponse<>();
        response.setCode(RpcResponseCodeEnum.FAIL.getCode());
        response.setMessage(RpcResponseCodeEnum.FAIL.getMessage());
        response.setRequestId(requestId);
        if (data != null) {
            response.setData(data);
        }
        return response;
    }
}

package liuuu.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RpcErrorMessageEnum {

    CLIENT_CONNECT_SERVER_FAILURE("客户端连接失败"),
    SERVER_INVOCATION_FAILURE("服务器调用失败"),
    SERVICE_CAN_NOT_BE_FOUND("无法找到指定的服务"),
    SERVICE_NOT_IMPLEMENT_ANY_INTERFACE("注册的服务器没有实现任何接口"),
    REQUEST_NOT_MATCH_RESPONSE("返回结果与请求不符");

    private final String message;
}

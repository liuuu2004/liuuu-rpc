package liuuu.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RpcRequestTransportEnum {

    SOCKET("socket"),
    NETTY("netty");

    private final String protocol;
}

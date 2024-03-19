package liuuu.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RpcConfigEnum {

    ZK_ADDRESS("rpc.zookeeper.address"),
    RPC_CONFIG_PATH("rpc.properties");

    private final String value;
}

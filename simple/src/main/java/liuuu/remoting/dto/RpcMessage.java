package liuuu.remoting.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class RpcMessage {
    // Rpc消息类型
    private byte messageType;

    // 序列化类型
    private byte serializationType;

    // 数据压缩类型
    private byte compressType;

    // 请求Id
    private int requestId;

    // 请求数据
    private Object data;
}

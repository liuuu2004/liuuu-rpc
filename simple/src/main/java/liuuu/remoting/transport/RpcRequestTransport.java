package liuuu.remoting.transport;


import liuuu.extension.SPI;
import liuuu.remoting.dto.RpcRequest;

/**
 * 传输Rpc请求的顶层接口
 */
@SPI
public interface RpcRequestTransport {
    /**
     * 向服务器发送Rpc请求并获取结果
     * @param rpcRequest
     * @return
     */
    Object sendRpcRequest(RpcRequest rpcRequest);
}

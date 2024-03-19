package liuuu.registry;

import liuuu.remoting.dto.RpcRequest;

import java.net.InetSocketAddress;


/**
 * 查找服务
 */
public interface ServiceDiscovery {

    /**
     * 根据rpc服务的名字查找服务
     * @param rpcRequest
     * @return
     */
    InetSocketAddress lookupService(RpcRequest rpcRequest);
}

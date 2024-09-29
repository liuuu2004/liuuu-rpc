package liuuu.registry;

import liuuu.extension.SPI;
import liuuu.remoting.dto.RpcRequest;

import java.net.InetSocketAddress;


/**
 * 查找服务
 */
@SPI
public interface ServiceDiscovery {

    /**
     * 根据rpc服务的名字查找服务
     * @param rpcRequest  Rpc服务请求
     * @return
     */
    InetSocketAddress lookupService(RpcRequest rpcRequest);
}

package liuuu.registry.zk;

import liuuu.loadbalance.LoadBalance;
import liuuu.registry.ServiceDiscovery;
import liuuu.remoting.dto.RpcRequest;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * 基于Zookeeper实现的注册发现
 */

@Slf4j
public class ZkServiceDiscoveryImpl implements ServiceDiscovery {

    private final LoadBalance loadBalance;

    @Override
    public InetSocketAddress lookupService(RpcRequest rpcRequest) {
        return null;
    }
}

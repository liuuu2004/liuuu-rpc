package liuuu.registry.zk;

import liuuu.enums.LoadBalanceEnum;
import liuuu.enums.RpcErrorMessageEnum;
import liuuu.exception.RpcException;
import liuuu.extension.ExtensionLoader;
import liuuu.loadbalance.LoadBalance;
import liuuu.registry.ServiceDiscovery;
import liuuu.registry.zk.util.CuratorUtils;
import liuuu.remoting.dto.RpcRequest;
import liuuu.utils.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * 基于Zookeeper实现的注册发现
 */

@Slf4j
public class ZkServiceDiscoveryImpl implements ServiceDiscovery {

    private final LoadBalance loadBalance;

    public ZkServiceDiscoveryImpl() {
        this.loadBalance = ExtensionLoader.getExtensionLoader(LoadBalance.class).getExtension(LoadBalanceEnum.LOAD_BALANCE.getName());
    }

    /**
     * 实现服务查找方法
     * @param rpcRequest  Rpc服务请求
     * @return
     */
    @Override
    public InetSocketAddress lookupService(RpcRequest rpcRequest) {
        String rpcServiceName = rpcRequest.getRpcServiceName();
        CuratorFramework zkClient = CuratorUtils.getZkClient();
        List<String> serviceUrlList = CuratorUtils.getChildrenNodes(zkClient, rpcServiceName);
        if (CollectionUtil.isEmpty(serviceUrlList)) {
            throw new RpcException(RpcErrorMessageEnum.SERVICE_CAN_NOT_BE_FOUND, rpcServiceName);
        }

        log.debug("---SERVICE URL LIST---");
        for (String s : serviceUrlList) {
            log.debug("---Service URL to be selected: [{}]---", s);
        }
        // 负载均衡
        String targetServiceUrl = loadBalance.selectServiceAddress(serviceUrlList, rpcRequest);
        log.info("Successfully found the service address:[{}]", targetServiceUrl);
        String[] socketAddressArray = targetServiceUrl.split(":");
        String host = socketAddressArray[0];
        int port = Integer.parseInt(socketAddressArray[1]);
//        port = 9998;//        host = "169.254.26.3";
        return new InetSocketAddress(host, port);
    }
}

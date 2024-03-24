package liuuu.registry.zk;

import liuuu.registry.ServiceRegistry;
import liuuu.registry.zk.util.CuratorUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;

import java.net.InetSocketAddress;

/**
 * 基于Zookeeper实现注册服务
 */
@Slf4j
public class ZkServiceRegistry implements ServiceRegistry {
    @Override
    public void registryService(String rpcServiceName, InetSocketAddress inetSocketAddress) {
        String rpcServicePath = CuratorUtils.ZK_REGISTRY_ROOT_PATH + "/" + rpcServiceName + inetSocketAddress.toString();
        CuratorFramework zkClient = CuratorUtils.getZkClient();
        CuratorUtils.createPersistentNode(zkClient, rpcServicePath);
    }
}

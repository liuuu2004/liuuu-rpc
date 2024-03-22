package liuuu.remoting.transport.netty.server;

import com.sun.jmx.remote.protocol.rmi.ServerProvider;
import liuuu.config.RpcServiceConfig;
import liuuu.factory.SingletonFactory;
import liuuu.provider.ServiceProvider;
import liuuu.provider.impl.ZkServiceProviderImpl;
import lombok.SneakyThrows;

public class NettyRpcServer {
    public static final int PORT = 9090;
    private final ServiceProvider serviceProvider = SingletonFactory.getInstance(ZkServiceProviderImpl.class);
    public void registerService(RpcServiceConfig rpcServiceConfig) {
        serviceProvider.publishService(rpcServiceConfig);
    }

    @SneakyThrows
    public void run() {

    }
}

package liuuu.remoting.transport.socket;

import jdk.internal.module.ServicesCatalog;
import liuuu.config.RpcServiceConfig;
import liuuu.factory.SingletonFactory;
import liuuu.provider.ServiceProvider;
import liuuu.provider.impl.ZkServiceProviderImpl;
import liuuu.utils.concurrent.threadpool.ThreadPoolFactoryUtil;

import javax.sound.sampled.Port;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;

public class SocketRpcServer {
    private final ExecutorService threadPool;
    private final ServiceProvider serviceProvider;

    public SocketRpcServer() {
        threadPool = ThreadPoolFactoryUtil.createCustomThreadPoolIfAbsent("socket-server-rpc-pool");
        serviceProvider = SingletonFactory.getInstance(ZkServiceProviderImpl.class);
    }

    public void registerService(RpcServiceConfig rpcServiceConfig) {
        serviceProvider.publishService(rpcServiceConfig);
    }

    public void start() throws IOException {
        try (ServerSocket server = new ServerSocket()) {
            String host = InetAddress.getLocalHost().getHostAddress();
            server.bind(new InetSocketAddress(host, PORT));
            CustomShutdownHook.get
        }
    }
}

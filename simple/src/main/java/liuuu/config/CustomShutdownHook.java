package liuuu.config;


import liuuu.remoting.transport.netty.server.NettyRpcServer;
import liuuu.utils.concurrent.threadpool.ThreadPoolFactoryUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

@Slf4j
public class CustomShutdownHook {
    private static final CustomShutdownHook CUSTOM_SHUTDOWN_HOOK = new CustomShutdownHook();
    public static CustomShutdownHook getCustomShutdownHook() {
        return CUSTOM_SHUTDOWN_HOOK;
    }

    public void clearAll() {
        log.info("Clear all...");
        Runtime.getRuntime().addShutdownHook(new Thread( ()-> {
            try {
                InetSocketAddress inetSocketAddress = new InetSocketAddress(InetAddress.getLocalHost().getHostAddress(), NettyRpcServer.PORT);
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            }
            ThreadPoolFactoryUtil.shutDownAllThreadPool();
        }));
    }
}

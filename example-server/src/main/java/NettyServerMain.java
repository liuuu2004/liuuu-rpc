import liuuu.HelloService;
import liuuu.annotation.RpcScan;
import liuuu.config.RpcServiceConfig;
import liuuu.remoting.transport.netty.server.NettyRpcServer;
import liuuu.serviceImpl.HelloServiceImpl2;
import liuuu.services.sort.QuickSortService;
import liuuu.services.sort.quicksortimpl.QuickSortImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@RpcScan(basePackage = {"liuuu"})
public class NettyServerMain {
    public static void main(String[] args) {
        // Register service via annotation
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(NettyServerMain.class);
        NettyRpcServer nettyRpcServer = (NettyRpcServer) applicationContext.getBean("nettyRpcServer");
        // Register service manually
//        HelloService helloService2 = new HelloServiceImpl2();
//        RpcServiceConfig rpcServiceConfig = RpcServiceConfig.builder()
//                .group("test1").version("version1").service(helloService2).build();
//        nettyRpcServer.registerService(rpcServiceConfig);
        QuickSortService quickSortService = new QuickSortImpl();
        RpcServiceConfig rpcServiceConfig = RpcServiceConfig.builder()
                        .group("test1").version("version1").service(quickSortService).build();
        nettyRpcServer.registerService(rpcServiceConfig);
        nettyRpcServer.start();
    }
}

import liuuu.HelloService;
import liuuu.config.RpcServiceConfig;
import liuuu.remoting.transport.socket.SocketRpcServer;
import liuuu.serviceImpl.HelloServiceImpl;

public class SocketServerMain {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        SocketRpcServer socketRpcServer = new SocketRpcServer();
        RpcServiceConfig rpcServiceConfig = new RpcServiceConfig();
        rpcServiceConfig.setService(helloService);
        socketRpcServer.registerService(rpcServiceConfig);
        socketRpcServer.start();
    }
}

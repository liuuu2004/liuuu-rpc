package liuuu.remoting.transport.socket;

import liuuu.enums.ServiceDiscoveryEnum;
import liuuu.exception.RpcException;
import liuuu.extension.ExtensionLoader;
import liuuu.registry.ServiceDiscovery;
import liuuu.remoting.dto.RpcRequest;
import liuuu.remoting.transport.RpcRequestTransport;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;


@AllArgsConstructor
@Slf4j
public class SocketRpcClient implements RpcRequestTransport {
    private final ServiceDiscovery serviceDiscovery;
    public SocketRpcClient() {
        this.serviceDiscovery = ExtensionLoader.getExtensionLoader(ServiceDiscovery.class).getExtension(ServiceDiscoveryEnum.ZK.getName());
    }

    @Override
    public Object sendRpcRequest(RpcRequest rpcRequest) {
        InetSocketAddress inetSocketAddress = serviceDiscovery.lookupService(rpcRequest);
        try(Socket socket = new Socket()) {
            socket.connect(inetSocketAddress);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(rpcRequest);
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            return objectInputStream.readObject();
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RpcException("服务调用失败", e);
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage());
            throw new RpcException("服务调用失败", e);
        }
    }
}

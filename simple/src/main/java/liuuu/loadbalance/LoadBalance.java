package liuuu.loadbalance;


import liuuu.extension.SPI;
import liuuu.remoting.dto.RpcRequest;
import liuuu.remoting.dto.RpcResponse;

import java.util.List;

/**
 * 负载均衡策略接口
 */
@SPI
public interface LoadBalance {

    /**
     * 从现有的服务地址列表中选择服务
     * @param serviceUrlList  服务地址列表
     * @param rpcRequest  Rpc服务请求(待处理对象)
     * @return
     */
    String selectServiceAddress(List<String> serviceUrlList, RpcRequest rpcRequest);
}

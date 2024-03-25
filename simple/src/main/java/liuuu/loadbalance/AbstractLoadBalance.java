package liuuu.loadbalance;


import liuuu.remoting.dto.RpcRequest;
import liuuu.utils.CollectionUtil;

import java.util.List;

/**
 * 负载均衡策略接口的抽象类
 */
public abstract class AbstractLoadBalance implements LoadBalance {

    @Override
    public String selectServiceAddress(List<String> serviceAddress, RpcRequest rpcRequest){
        if (CollectionUtil.isEmpty(serviceAddress)) {
            return null;
        }
        if (serviceAddress.size() == 1) {
            return serviceAddress.get(0);
        }
        return doSelect(serviceAddress, rpcRequest);
    }

    protected abstract String doSelect(List<String> serviceAddress, RpcRequest rpcRequest);

}

package liuuu.loadbalance.loadbalancer;

import liuuu.loadbalance.AbstractLoadBalance;
import liuuu.remoting.dto.RpcRequest;

import java.util.List;
import java.util.Random;

public class RandomLoadBalance extends AbstractLoadBalance {
    @Override
    protected String doSelect(List<String> serviceAddress, RpcRequest rpcRequest) {
        Random random = new Random();
        return serviceAddress.get(random.nextInt(serviceAddress.size()));
    }
}

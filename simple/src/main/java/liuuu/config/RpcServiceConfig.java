package liuuu.config;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class RpcServiceConfig {
    // 服务版本号
    private String version = "";

    // 服务所属的团体
    private String group = "";

    // 服务类
    private Object service;

    /**
     * 获取Rpc服务名，包含类信息、团体名、版本号
     * @return
     */
    public String getRpcServiceName() {
        return this.getServiceName() + this.getGroup() + this.getVersion();
    }

    /**
     * 获取服务名，包含服务类的类名、接口名、类的规范名称
     * @return
     */
    public String getServiceName() {
        return this.getClass().getInterfaces()[0].getCanonicalName();
    }
}

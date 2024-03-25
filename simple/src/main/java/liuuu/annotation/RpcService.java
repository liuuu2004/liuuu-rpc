package liuuu.annotation;


import java.lang.annotation.*;


/**
 * 注册服务
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Inherited
public @interface RpcService {

    /**
     * 服务的版本信息
     * @return
     */
    String version() default "";

    /**
     * 服务的分组信息
     * @return
     */
    String group() default "";
}

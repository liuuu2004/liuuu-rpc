package liuuu.annotation;


import java.lang.annotation.*;


/**
 * 消费服务
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Inherited
public @interface RpcReference {

    /**
     * 服务版本信息
     * @return
     */
    String version() default "";

    /**
     * 服务分组信息
     * @return
     */
    String group() default "";
}

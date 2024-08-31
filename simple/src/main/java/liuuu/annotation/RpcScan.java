package liuuu.annotation;

import org.springframework.context.annotation.Import;
import liuuu.spring.CustomScannerRegister;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Import(CustomScannerRegister.class)
@Documented
public @interface RpcScan {
    String[] basePackage();
}

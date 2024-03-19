package liuuu.extension;

import com.sun.tools.javac.code.Attribute;
import lombok.ToString;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SPI {
}

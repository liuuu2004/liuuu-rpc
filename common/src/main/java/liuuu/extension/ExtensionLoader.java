package liuuu.extension;


import lombok.extern.slf4j.Slf4j;

/**
 * 参考 dubbo spi: https://dubbo.apache.org/zh-cn/docs/source_code_guide/dubbo-spi.html
 */

@Slf4j
public final class ExtensionLoader<T> {
    private ExtensionLoader(Class<?> type){
        this.type = type;
    }
}

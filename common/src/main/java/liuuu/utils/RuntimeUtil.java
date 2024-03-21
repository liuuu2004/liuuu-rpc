package liuuu.utils;

/**
 * 获取CPU核心数
 */
public class RuntimeUtil {
    public static int cpus() {
        return Runtime.getRuntime().availableProcessors();
    }
}

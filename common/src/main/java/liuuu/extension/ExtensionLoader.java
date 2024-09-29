package liuuu.extension;


import liuuu.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.C;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.rmi.server.LogStream.log;

/**
 * 参考 dubbo spi: https://dubbo.apache.org/zh-cn/docs/source_code_guide/dubbo-spi.html
 */

@Slf4j
public final class ExtensionLoader<T> {
    private static final String SERVICE_DICTIONARY = "META_INF/extensions/";
    private static final Map<Class<?>, ExtensionLoader<?>> EXTENSION_LOADERS = new ConcurrentHashMap<>();
    private static final Map<Class<?>, Object> EXTENSION_INSTANCES = new ConcurrentHashMap<>();
    private final Class<?> type;
    private final Map<String, Holder<Object>> cachedInstances = new ConcurrentHashMap<>();
    private final Holder<Map<String, Class<?>>> cachedClasses = new Holder<>();
    private ExtensionLoader(Class<?> type) {
        this.type = type;
    }

    /**
     * 从EXTENSION_LOADERS中获取所需type的ExtensionLoader
     * @param type
     * @return
     * @param <S>
     */
    public static <S> ExtensionLoader<S> getExtensionLoader(Class<S> type) {
        if (type == null) {
            throw new IllegalArgumentException("Extension type should not be null");
        }
        if (!type.isInterface()) {
            throw new IllegalArgumentException("Extension type must be an interface");
        }
        if (type.getAnnotation(SPI.class) == null) {
            throw new IllegalArgumentException("Extension must be annotated by @SPI");
        }
        // 尝试从cache中获取ExtensionLoader
        ExtensionLoader<S> extensionLoader = (ExtensionLoader<S>) EXTENSION_LOADERS.get(type);
        // 若不存在，则创建一个所需的ExtensionLoader
        if (extensionLoader == null) {
            EXTENSION_LOADERS.putIfAbsent(type, new ExtensionLoader<S>(type));
            // 保存到cache中
            extensionLoader = (ExtensionLoader<S>) EXTENSION_LOADERS.get(type);
        }
        return extensionLoader;
    }

    /**
     * 从EXTENSION_INSTANCES中获取所需name的Extension
     * @param name
     * @return
     */
    public T getExtension(String name) {
        // 判断name是否满足不为空、不为空白、不含空格
        if (StringUtil.isBlank(name)) {
            throw new IllegalArgumentException("Extension name should not be null or empty");
        }
        // 首先尝试从cache中获取
        Holder<Object> holder = cachedInstances.get(name);

        // 若不存在则创建一个满足条件的
        if (holder == null) {
            cachedInstances.putIfAbsent(name, new Holder<>());
            holder = cachedInstances.get(name);
        }

        Object instance = holder.get();
        // 双重锁获取实例，确保线程安全
        if (instance == null) {
            synchronized (holder) {
                instance = holder.get();
                if (instance == null) {
                    instance = createExtensions(name);
                    holder.set(instance);
                }
            }
        }
        return (T) instance;
    }

    /**
     * 根据name创建对应的实例
     * @param name
     * @return
     */
    private T createExtensions(String name) {
        // 加载所有T类型的Extension类
        Class<?> clazz = getExtensionClasses().get(name);
        if (clazz == null) {
            throw new RuntimeException("No such extensions of name " + name);
        }
        T instance = (T) EXTENSION_INSTANCES.get(clazz);
        if (instance == null) {
            try {
                EXTENSION_INSTANCES.putIfAbsent(clazz, clazz.newInstance());
                instance = (T) EXTENSION_INSTANCES.get(clazz);
            } catch (InstantiationException e) {
                log(e.getMessage());
            } catch (IllegalAccessException e) {
                log(e.getMessage());
            }
        }
        return instance;
    }

    private Map<String, Class<?>> getExtensionClasses() {
        // 首先从cache中加载Extension类
        Map<String, Class<?>> classes = cachedClasses.get();

        // 双重锁确保线程安全
        if (classes == null) {
            synchronized (cachedClasses) {
                classes = cachedClasses.get();
                if (classes == null) {
                    classes = new HashMap<>();
                    // 从dictionary中加载所有的Extension
                    loadDictionary(classes);
                    cachedClasses.set(classes);
                }
            }
        }
        return classes;
    }

    private void loadDictionary(Map<String, Class<?>> classes) {
        String filename = ExtensionLoader.SERVICE_DICTIONARY + type.getName();
        try {
            Enumeration<URL> urls;
            ClassLoader classLoader = ExtensionLoader.class.getClassLoader();
            urls = classLoader.getResources(filename);
            if (urls != null) {
                while (urls.hasMoreElements()) {
                    URL resourcesUrl = urls.nextElement();
                    loadResource(classes, classLoader, resourcesUrl);
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void loadResource(Map<String, Class<?>> classes, ClassLoader classLoader, URL resourcesUrl) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resourcesUrl.openStream(), UTF_8))) {
            String line;
            // 读取每一行
            while ((line = reader.readLine()) != null) {
                // 获取下标
                final int ci =line.indexOf('#');
                if (ci >= 0) {
                    // 忽略#后面的内容
                    line = line.substring(0, ci);
                }
                line = line.trim();
                if (line.length() > 0) {
                    try {
                        final int ei = line.indexOf('=');
                        String name = line.substring(0, ei).trim();
                        String clazzName = line.substring(ei + 1).trim();
                        // 确保name和clazzName都不为空
                        if (name.length() > 0 && clazzName.length() > 0 ) {
                            Class<?> clazz = classLoader.loadClass(clazzName);
                            classes.put(name, clazz);
                        }
                    } catch (ClassNotFoundException e) {
                        log.error(e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

}

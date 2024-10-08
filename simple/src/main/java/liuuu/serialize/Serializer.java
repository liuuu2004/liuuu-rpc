package liuuu.serialize;

import liuuu.extension.SPI;

/**
 * 序列化方法的接口
 */

@SPI
public interface Serializer {

    /**
     * 要被序列化的对象
     * @param object  将要被转化成二进制数据流通过网络传输的对象
     * @return
     */
    byte[] serialize(Object object);

    /**
     * 反序列化方法接口
     * @param bytes  已经被序列化的二进制数据
     * @param clazz  目标类
     * @return
     * @param <T>
     */
    <T> T deserialize(byte[] bytes, Class<T> clazz);

}

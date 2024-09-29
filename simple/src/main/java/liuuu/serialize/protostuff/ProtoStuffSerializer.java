package liuuu.serialize.protostuff;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import liuuu.serialize.Serializer;

public class ProtoStuffSerializer implements Serializer {

    private static final LinkedBuffer BUFFER = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);

    @Override
    public byte[] serialize(Object object) {
        Class<?> clazz = object.getClass();
        Schema schema = RuntimeSchema .getSchema(clazz);
        byte[] bytes;
        try {
            bytes = ProtostuffIOUtil.toByteArray(object, schema, BUFFER);
        } finally {
            BUFFER.clear();
        }
        return bytes;
    }

    /**
     * schema被用来定义类的结构和序列化/反序列化规则
     * @param bytes  已经被序列化的二进制数据
     * @param clazz  目标类
     * @return
     * @param <T>
     */
    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        Schema<T> schema = RuntimeSchema.getSchema(clazz);
        T object = schema.newMessage();
        ProtostuffIOUtil.mergeFrom(bytes, object, schema);
        return object;
    }
}

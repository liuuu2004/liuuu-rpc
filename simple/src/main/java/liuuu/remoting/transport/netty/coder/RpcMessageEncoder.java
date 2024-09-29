package liuuu.remoting.transport.netty.coder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import liuuu.compress.Compress;
import liuuu.enums.CompressTypeEnum;
import liuuu.enums.SerializationTypeEnum;
import liuuu.extension.ExtensionLoader;
import liuuu.remoting.constants.RpcConstants;
import liuuu.remoting.dto.RpcMessage;
import io.netty.handler.codec.MessageToByteEncoder;
import liuuu.serialize.Serializer;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;


@Slf4j
public class RpcMessageEncoder extends MessageToByteEncoder<RpcMessage> {
    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger(0);

    /**
     *
     * @param channelHandlerContext  上下文对象
     * @param rpcMessage  待编码的消息对象
     * @param byteBuf  编码后的字节缓冲区
     * @throws Exception
     */
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, RpcMessage rpcMessage, ByteBuf byteBuf) throws Exception {
        try {
            byteBuf.writeBytes(RpcConstants.MAGIC_NUMBER);
            byteBuf.writeByte(RpcConstants.VERSION);

            // 为写入值留下空间
            byteBuf.writerIndex(byteBuf.writerIndex() + 4);

            byte messageType = rpcMessage.getMessageType();
            byteBuf.writeByte(messageType);
            byteBuf.writeByte(rpcMessage.getSerializationType());
            byteBuf.writeByte(CompressTypeEnum.GZIP.getCode());
            byteBuf.writeInt(ATOMIC_INTEGER.getAndIncrement());

            // 构建消息的完整长度
            byte[] bodyBytes = null;
            int fullLength = RpcConstants.HEAD_LENGTH;

            // 若不是heartbeat类型的消息
            if (messageType != RpcConstants.HEARTBEAT_REQUEST_TYPE && messageType != RpcConstants.HEARTBEAT_RESPONSE_TYPE) {
                // 将消息体序列化
                String serializedName = SerializationTypeEnum.getName(rpcMessage.getSerializationType());
                log.info("Serialization name: [{}]", serializedName);
                Serializer serializer = ExtensionLoader.getExtensionLoader(Serializer.class).getExtension(serializedName);
                bodyBytes = serializer.serialize(rpcMessage.getData());

                // 压缩bytes
                String compressName = CompressTypeEnum.getName(rpcMessage.getCompressType());
                Compress compress = ExtensionLoader.getExtensionLoader(Compress.class).getExtension(compressName);
                bodyBytes = compress.compress(bodyBytes);
                fullLength += bodyBytes.length;
            }

            if (bodyBytes != null) {
                byteBuf.writeBytes(bodyBytes);
            }
            int writeIndex = byteBuf.writerIndex();
            byteBuf.writerIndex(writeIndex - fullLength + RpcConstants.MAGIC_NUMBER.length + 1);
            byteBuf.writeInt(fullLength);
            byteBuf.writerIndex(writeIndex);
        } catch (Exception e) {
            log.error("Encode request error!", e);
        }
    }
}

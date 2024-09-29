package liuuu.remoting.transport.netty.coder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import liuuu.compress.Compress;
import liuuu.enums.CompressTypeEnum;
import liuuu.enums.SerializationTypeEnum;
import liuuu.extension.ExtensionLoader;
import liuuu.remoting.constants.RpcConstants;
import liuuu.remoting.dto.RpcMessage;
import liuuu.remoting.dto.RpcRequest;
import liuuu.remoting.dto.RpcResponse;
import liuuu.serialize.Serializer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RpcMessageDecoder extends LengthFieldBasedFrameDecoder {
    public RpcMessageDecoder() {
        this(RpcConstants.MAX_FRAME_LENGTH, 5, 4, -9, 0);
    }

    public RpcMessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength,
                             int lengthAdjustment, int initialByteToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialByteToStrip);
    }

    @Override
    protected Object decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        log.info("Begin decoding...");
        Object decoded = super.decode(channelHandlerContext, byteBuf);
        if (decoded instanceof ByteBuf) {
            ByteBuf frame = (ByteBuf) decoded;
            if (frame.readableBytes() >= RpcConstants.TOTAL_LENGTH) {
                try {
                    return decodeFrame(frame);
                } catch (Exception e) {
                    log.error("Decode frame error!", e);
                    throw e;
                } finally {
                    frame.release();
                }
            }
        }
        return decoded;
    }

    private Object decodeFrame(ByteBuf frame) {
        // 依序读取frame信息
        checkMagicNumber(frame);
        checkVersion(frame);
        int fullLength = frame.readInt();

        // 创建Rpc消息类
        byte messageType = frame.readByte();
        byte serializationType = frame.readByte();
        byte compressionType = frame.readByte();
        int requestId = frame.readInt();
        RpcMessage rpcMessage = RpcMessage.builder()
                .serializationType(serializationType)
                .messageType(messageType)
                .requestId(requestId)
                .build();
        if (messageType == RpcConstants.HEARTBEAT_REQUEST_TYPE) {
            rpcMessage.setData(RpcConstants.PING);
            return rpcMessage;
        }
        if (messageType == RpcConstants.HEARTBEAT_RESPONSE_TYPE) {
            rpcMessage.setData(RpcConstants.PONG);
            return rpcMessage;
        }
        int bodyLength = fullLength - RpcConstants.HEAD_LENGTH;
        if (bodyLength > 0) {
            byte[] bs = new byte[bodyLength];
            frame.readBytes(bs);

            // 将字节文件解压缩
            String compressName = CompressTypeEnum.getName(compressionType);
            Compress compress = ExtensionLoader.getExtensionLoader(Compress.class).getExtension(compressName);
            bs = compress.decompress(bs);

            // 将对象反序列化
            String deserializationName = SerializationTypeEnum.getName(rpcMessage.getSerializationType());
            log.info("codec name: [{}] ", deserializationName);
            Serializer serializer = ExtensionLoader.getExtensionLoader(Serializer.class).getExtension(deserializationName);
            if (messageType == RpcConstants.REQUEST_TYPE) {
                RpcRequest tempValue = serializer.deserialize(bs, RpcRequest.class);
                rpcMessage.setData(tempValue);
            }
            else {
                RpcResponse tempValue = serializer.deserialize(bs, RpcResponse.class);
                rpcMessage.setData(tempValue);
            }
        }
        return rpcMessage;
    }

    private void checkMagicNumber(ByteBuf frame) {
        int len = RpcConstants.MAGIC_NUMBER.length;
        byte[] temp = new byte[len];
        frame.readBytes(temp);
        for (int i = 0; i < len; i++) {
            if (temp[i] != RpcConstants.MAGIC_NUMBER[i]) {
                throw new IllegalArgumentException("magic number类型未知");
            }
        }
    }

    private void checkVersion(ByteBuf frame) {
        byte version = frame.readByte();
        if (version != RpcConstants.VERSION) {
            throw new RuntimeException("版本不适配");
        }
    }
}

package org.erlandhu.netty.protobuf.code;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ProtobufFrameEncoder extends MessageToByteEncoder<ByteBuf> {
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) throws Exception {
        int bodyLen = msg.readableBytes();
        byte[] bytes = int2bytes(bodyLen + 4);
        out.ensureWritable(bytes.length + bodyLen);
        out.writeBytes(bytes);
        out.writeBytes(msg, msg.readerIndex(), bodyLen);
    }

    public static byte[] int2bytes(int intData) {
        if (intData <= 99999999) {
            byte[] resultByte = new byte[4];

            for (int i = 3; i >= 0; --i) {
                if (intData > 0) {
                    resultByte[i] = (byte) (intData % 100 + 28);
                    intData /= 100;
                } else {
                    resultByte[i] = 28;
                }
            }

            return resultByte;
        } else {
            return null;
        }
    }

}

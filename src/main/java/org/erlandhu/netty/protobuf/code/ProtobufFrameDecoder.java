package org.erlandhu.netty.protobuf.code;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class ProtobufFrameDecoder extends ByteToMessageDecoder {
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        in.markReaderIndex();
        if (in.isReadable() && in.readableBytes() > 4) {
            byte[] headBytes = new byte[4];
            in.readBytes(headBytes);
            int length = bytes2int(headBytes);
            if (in.readableBytes() < length) {
                in.resetReaderIndex();
                return;
            }
            ByteBuf byteBuf = in.readBytes(length);
            out.add(byteBuf);
        } else {
            in.resetReaderIndex();
        }
    }


    public static int bytes2int(byte[] data) {
        int wei = 1000000;
        int result = 0;
        if (data.length != 4) {
            return -1;
        } else {
            for (int i = 0; i < 4; ++i) {
                if (data[i] > 28) {
                    switch (i) {
                        case 1:
                            wei = 10000;
                            break;
                        case 2:
                            wei = 100;
                            break;
                        case 3:
                            wei = 1;
                    }

                    result += (data[i] - 28) * wei;
                }
            }

            return result;
        }
    }
}

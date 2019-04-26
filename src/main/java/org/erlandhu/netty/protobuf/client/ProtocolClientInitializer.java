package org.erlandhu.netty.protobuf.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import org.erlandhu.netty.protobuf.code.ProtobufFrameDecoder;
import org.erlandhu.netty.protobuf.code.ProtobufFrameEncoder;
import org.erlandhu.netty.protobuf.console.ClientConsole;

public class ProtocolClientInitializer extends ChannelInitializer<SocketChannel> {
    private ClientConsole clientConsole;

    public ProtocolClientInitializer(ClientConsole clientConsole) {
        this.clientConsole = clientConsole;
    }

    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new ProtobufFrameDecoder(),
                // 解码器解码(解码粘包处理)
                new ProtobufEncoder(),
                // 编码器编码(编码粘包处理)
                new ProtobufFrameEncoder(),
                // 业务逻辑处理
                new ProtobufEncoder(),
                // 业务逻辑处理(注意:每条连接用同一个channelHandler对象)
                new ProtocolClientHandler(clientConsole));
    }
}

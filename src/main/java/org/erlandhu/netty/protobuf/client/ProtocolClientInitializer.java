package org.erlandhu.netty.protobuf.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import org.erlandhu.netty.protobuf.code.ProtobufFrameDecoder;
import org.erlandhu.netty.protobuf.code.ProtobufFrameEncoder;

public class ProtocolClientInitializer extends ChannelInitializer<SocketChannel> {
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new ProtobufFrameDecoder(), new ProtobufEncoder(), new ProtobufFrameEncoder(), new ProtobufEncoder(), new ProtocolClientHandler());
    }
}

package org.erlandhu.netty.protobuf.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.erlandhu.netty.protobuf.cmd.Cmd;
import org.erlandhu.netty.protobuf.code.ProtobufFrameDecoder;
import org.erlandhu.netty.protobuf.code.ProtobufFrameEncoder;

public class ProtocolServerInitializer extends ChannelInitializer<SocketChannel> {

    private NioEventLoopGroup businessGroup = new NioEventLoopGroup();

    public ProtocolServerInitializer() {

    }

    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();
        p.addLast(this.businessGroup,
                new ProtobufFrameDecoder(), new ProtobufDecoder(Cmd.CmdData.getDefaultInstance()),
                new ProtobufFrameEncoder(), new ProtobufEncoder(),
                new IdleStateHandler(600, 0, 0),
                new ProtocolServerHandler());
    }
}

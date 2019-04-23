package org.erlandhu.netty.protobuf.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.erlandhu.netty.protobuf.cmd.Cmd.CmdData;

public class ProtocolServerHandler extends SimpleChannelInboundHandler<CmdData> {
    protected void channelRead0(ChannelHandlerContext ctx, CmdData msg) throws Exception {
        System.out.println(msg.getData());
    }
}

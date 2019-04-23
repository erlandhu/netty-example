package org.erlandhu.netty.protobuf.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.erlandhu.netty.protobuf.cmd.Cmd.CmdData;

public class ProtocolClientHandler extends SimpleChannelInboundHandler<CmdData> {


    protected void channelRead0(ChannelHandlerContext ctx, CmdData msg) throws Exception {
        System.out.println(msg.getMessage().getMessageId());
    }
}

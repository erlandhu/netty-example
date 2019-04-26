package org.erlandhu.netty.protobuf.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.erlandhu.netty.protobuf.cmd.Cmd.CmdData;
import org.erlandhu.netty.protobuf.console.ClientConsole;

public class ProtocolClientHandler extends SimpleChannelInboundHandler<CmdData> {

    private ClientConsole clientConsole;

    public ProtocolClientHandler(ClientConsole clientConsole) {
        super(false);
        this.clientConsole = clientConsole;
    }

    /**
     * 接收数据
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CmdData msg) throws Exception {
        System.out.println(msg.getMessage().getMessageId());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        clientConsole.onConnect();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ctx.close();
        ctx.channel().close();
        clientConsole.onDisconnect();
    }
}

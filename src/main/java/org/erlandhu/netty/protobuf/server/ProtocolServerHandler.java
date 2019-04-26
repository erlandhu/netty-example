package org.erlandhu.netty.protobuf.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.erlandhu.netty.protobuf.cmd.Cmd.CmdData;

public class ProtocolServerHandler extends SimpleChannelInboundHandler<CmdData> {
    protected void channelRead0(ChannelHandlerContext ctx, CmdData msg) throws Exception {
        System.out.println(msg.getData());
    }

    /**
     * 通道行为
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    /**
     * 连接通道不活跃接口
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ctx.close();
        ctx.channel().close();
        super.channelInactive(ctx);
        /*
         * if(dos!=null){ dos.close(); logger.info("压力测试数据录制完成！"); }
         */
    }

    /**
     * 关闭通道
     */
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        ctx.close();
        //ctx.channel().close();
        super.channelUnregistered(ctx);
    }

    /**
     * 错误获取
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // ctx.close();
        // System.out.println("exceptionCaught..............ctx.channel()="+ctx.channel().getClass());
        // cause.printStackTrace();
    }

    /**
     * 用户事件触发接口
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent && ((IdleStateEvent) evt).state() == IdleState.READER_IDLE) {
            //System.out.println("IdleState.READER_IDLE==============================" + new Date());
            super.userEventTriggered(ctx, evt);
        }
    }
}


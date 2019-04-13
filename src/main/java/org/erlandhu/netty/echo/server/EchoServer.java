package org.erlandhu.netty.echo.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.erlandhu.netty.echo.server.handler.EchoServerHandler;

import java.net.InetSocketAddress;

/**
 * 启动类
 * 设置端口, 监听连接请求
 */
public class EchoServer {
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws InterruptedException {
        new EchoServer(8999).start();
    }

    private void start() throws InterruptedException {
            NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    // 指定用NIO的传输Channel
                    .channel(NioServerSocketChannel.class)
                    // 设置socket地址, 端口
                    .localAddress(new InetSocketAddress(port))
                    // 添加EchoServerHandler到Channel的ChannelPipeline
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(
                                    new EchoServerHandler()
                            );
                        }
                    });
            // 绑定的服务器; sync()等待服务区关闭
            ChannelFuture f = b.bind().sync();
            System.out.println(EchoServer.class.getName() + "started and listen on" + f.channel().localAddress());
            f.channel().closeFuture().sync();
        } finally {
            // 释放所有资源
            group.shutdownGracefully().sync();
        }
    }
}

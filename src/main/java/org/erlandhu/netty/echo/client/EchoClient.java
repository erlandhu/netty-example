package org.erlandhu.netty.echo.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.erlandhu.netty.echo.client.handler.EchoClientHandler;

import java.net.InetSocketAddress;

/**
 *
 */
public class EchoClient {
    private final String host;
    private final int port;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public static void main(String[] args) throws InterruptedException {
        new EchoClient("127.0.0.1", 8999).start();
    }

    public void start() throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            // 指定 EventLoopGroup 来处理客户端事件
            b.group(group)
                    // 使用的 channel 类型是一个用于 NIO 传输
                    .channel(NioSocketChannel.class)
                    // 设置服务器的 InetSocketAddress
                    .remoteAddress(new InetSocketAddress(host, port))
                    // 当建立一个连接和一个新的通道时，创建添加到 EchoClientHandler 实例 到 channel pipeline
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new EchoClientHandler());
                        }
                    });
            // 连接到远程;等待连接完成
            ChannelFuture f = b.connect().sync();
            // 阻塞直到 Channel 关闭
            f.channel().closeFuture().sync();
        } finally {
            // 关闭线程池和释放所有资源
            group.shutdownGracefully().sync();
        }
    }
}

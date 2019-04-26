package org.erlandhu.netty.protobuf.client;

import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.Message;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.erlandhu.netty.protobuf.console.ClientConsole;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

public class ProtocolClient {
    private Channel channel;
    private String host;
    private int port;
    private EventLoopGroup group;
    private String address;
    Bootstrap bootstrap;
    private boolean isConnect = false;
    private boolean isReconnect = true;

    public ProtocolClient(String host, int port) {
        this.host = host;
        this.port = port;
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();

        bootstrap.group(group).channel(NioSocketChannel.class)
                // 设置服务器的 InetSocketAddress
                .remoteAddress(new InetSocketAddress(host, port))
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 50000)
                .handler(new ProtocolClientInitializer(new ClientConsole(this)));
        // 连接到远程;等待连接完成
        try {
            ChannelFuture f = bootstrap.connect().sync();
            channel = f.channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 写入并发送
     *
     * @param data
     */
    public void write(Object data) {
        channel.writeAndFlush(data);
    }

    /**
     * 写入并发送protobuf数据
     *
     * @param message
     */
    public void write(Message message) {
        channel.writeAndFlush(message);
    }

    /**
     * 写入并发送protobuf builder数据
     *
     * @param builder
     */
    public void write(GeneratedMessage.Builder<?> builder) {
        channel.writeAndFlush(builder.build());
    }

    public void reconnect() {
        isConnect = false;
        if (isReconnect) {
            connect();
        }
    }

    private void connect() {
        if (this.isConnect) {
            return;
        }
        bootstrap.connect(host, port).addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    if (channel != null && channel.isOpen()) {
                        channel.close();
                    }
                    channel = future.channel();
                    isConnect = true;
                } else {
                    future.channel().eventLoop().schedule(new Runnable() {
                        public void run() {
                            connect();
                        }
                    }, 1, TimeUnit.SECONDS);
                }
            }
        }).awaitUninterruptibly();
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public EventLoopGroup getGroup() {
        return group;
    }

    public void setGroup(EventLoopGroup group) {
        this.group = group;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Bootstrap getBootstrap() {
        return bootstrap;
    }

    public void setBootstrap(Bootstrap bootstrap) {
        this.bootstrap = bootstrap;
    }


    public void connectSuccess() {
        // connect success
    }
}

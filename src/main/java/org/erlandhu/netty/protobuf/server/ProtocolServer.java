package org.erlandhu.netty.protobuf.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class ProtocolServer {
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private ServerBootstrap bootstrap;

    private ProtocolServerInitializer protocolServerInitializer;
    private Channel channel;

    public ProtocolServer() {
    }

    public void start(int port) {
        bootstrap = new ServerBootstrap();
        /**
         * bossGroup线程池用来接受客户端的连接请求,创建Channel,推荐将bossGroup的线程数量设置成1
         */
        bossGroup = new NioEventLoopGroup();
        /**
         * workerGroup线程池用来处理boss线程池里面的连接的数据(即处理Channel中的数据接受和发送)
         * 提供吞吐量:1增到worker线程数量;2减少减少worker线程的处理占用时间
         */
        workerGroup = new NioEventLoopGroup();
        this.protocolServerInitializer = this.protocolServerInitializer == null ? new ProtocolServerInitializer() : this.protocolServerInitializer;
        try {
            bootstrap.group(bossGroup, workerGroup)
                    /**
                     * 采用Nio非阻塞模式,多条线程对应多个连接，每条连接串行在一条线程内。
                     * [也可以设置为旧IO(OioServerSocketChannel.class)阻塞模式,旧IO是一个连接对应一条线程]
                     */
                    .channel(NioServerSocketChannel.class)
                    .childHandler(protocolServerInitializer)
                    /**
                     * 默认选项为AdaptiveRecvByteBufAllocator。容量动态调整的接收缓冲区分配器，
                     * 它会根据之前Channel接收到的数据报大小进行计算，如果连续填充满接收缓冲区的可写空间，则动态扩展容量。
                     * 如果连续2次接收到的数据报都小于指定值，则收缩当前的容量，以节约内存。
                     * .option(ChannelOption.RCVBUF_ALLOCATOR, AdaptiveRecvByteBufAllocator.DEFAULT)
                     */

                    /**
                     * .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                     * 缓存方式的ByteBuf生成工具类。预生成了一个高性能的buffer池，分配策略则是结合了buddy allocation和slab allocation的jemalloc变种。
                     *
                     * .option(ChannelOption.ALLOCATOR, UnpooledByteBufAllocator.DEFAULT)
                     * 非缓存方式ByteBuf生成工具类，是ByteBufAllocator的一种简单实现，生成的方式是每次调用都new一个新的ByteBuf。提供了各种ByteBuf的实现方法。
                     *
                     * PooledByteBufAllocator比UnpooledByteBufAllocator内存消耗减少5倍，但4.0.x版本有内存溢出的bug
                     * netty4.0.x默认为UnpooledByteBufAllocator。netty4.1以后默认为PooledByteBufAllocator
                     * 现在[2015/8/13]netty4.1还在Beta版本,等出稳定版本后可以考虑修改为PooledByteBufAllocator
                     */

                    /**
                     * 允许使用长连接，允许同一客户端复用连接
                     */
                    .option(ChannelOption.SO_REUSEADDR, true)

                    .option(ChannelOption.TCP_NODELAY, true)

                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)

                    .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    /**
                     * 内核为此套接口排队的最大连接个数。
                     */
                    .option(ChannelOption.SO_BACKLOG, 3000);


            channel = bootstrap.bind(port).sync().channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}

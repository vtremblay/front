package org.front.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

@SuppressWarnings("squid:S1312")
public class Server {

    private static final Logger log = LoggerFactory.getLogger(Server.class);

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    public void listen(String address, int port) {

        if (isEventLoopRunning(bossGroup) || isEventLoopRunning(workerGroup)) {
            throw new IllegalStateException("Server is already listening");
        }

        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();

        try {

            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ProxyInitializer())
                    .childOption(ChannelOption.AUTO_READ, false)
                    .bind(address, port).sync().channel().closeFuture().sync();

        } catch (InterruptedException e) {

            log.error(e.getMessage(), e);

        } finally {

            stop();
        }
    }

    private static boolean isEventLoopRunning(EventLoopGroup eventLoopGroup) {
        return eventLoopGroup != null && !eventLoopGroup.isShutdown();
    }

    public void stop() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}

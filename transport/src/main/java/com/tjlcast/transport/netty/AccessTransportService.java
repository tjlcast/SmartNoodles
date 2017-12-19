package com.tjlcast.transport.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by tangjialiang on 2017/12/18.
 */


@Service("AccessTransportService")
@Slf4j
public class AccessTransportService {

    @Value("${accsess.bind_address}")
    private String host ;
    @Value("${accsess.bind_port}")
    private int port ;


    private Channel serverChannel;
    private EventLoopGroup bossGroup ;
    private EventLoopGroup workerGroup ;


    @PostConstruct
    public void init() throws Exception {
        log.info("***********************************begin***********************************") ;
        log.info("address {} port {}", host, port) ;

        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap() ;
        b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new AccessTransportServiceInitializer()) ;

        serverChannel = b.bind(host, port).sync().channel();

        log.info("AccessTransportService started!");
    }

    @PreDestroy
    public void shutdown() throws InterruptedException {
        try {
            serverChannel.close().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}

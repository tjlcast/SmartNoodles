package com.tjlcast.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by tangjialiang on 2017/12/19.
 *
 */

//@ComponentScan({"com.tjlcast.transport.netty"})
//@ComponentScan({"com.tjlcast.transport.akka"})
@SpringBootApplication
public class SNApplication {

    public static void main(String[] args) {
        SpringApplication.run(SNApplication.class, args);
    }
}

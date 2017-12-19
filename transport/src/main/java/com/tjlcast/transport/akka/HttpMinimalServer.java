package com.tjlcast.transport.akka;

import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.CompletionStage;

/**
 * Created by tangjialiang on 2017/12/19.
 */

@Service
@Slf4j
public class HttpMinimalServer extends AllDirectives {

    private CompletionStage<ServerBinding> binding ;
    private ActorSystem system ;

    @PostConstruct
    public void initHttpServer() {
        // boot up server using the route as defined below
        log.info("init akka http server");

        system = ActorSystem.create("routes");

        final Http http = Http.get(system);
        final ActorMaterializer materializer = ActorMaterializer.create(system);

        HttpMinimalServer app = new HttpMinimalServer();

        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = app.createRoute().flow(system, materializer);
        binding = http.bindAndHandle(routeFlow,
                ConnectHttp.toHost("localhost", 9090), materializer);



        log.info("akka http server started!") ;
    }

    private Route createRoute() {
        return route(
                path("hello", () ->
                        get(() ->
                                complete("<h1>Say hello to akka-http</h1>"))));
    }

    @PreDestroy
    public void shutdown() throws InterruptedException {
        binding
                .thenCompose(ServerBinding::unbind) // trigger unbinding from the port
                .thenAccept(unbound -> system.terminate()); // and shutdown when done
    }
}
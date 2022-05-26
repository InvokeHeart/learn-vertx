package org.stop.learnvertx;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;

import java.util.concurrent.TimeUnit;

public class Vertx2 {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);
        router.route(HttpMethod.GET, "/hello").blockingHandler(
                ctx -> {
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //vert.x-worker-thread-0 xixi
                    System.out.println(Thread.currentThread().getName()+" xixi");
                    HttpServerResponse response = ctx.response();
                    response.setChunked(true);
                    response.end("xixi");
                }
        );
        router.route("/hello2").handler(ctx -> {
            //vert.x-eventloop-thread-0 ahah
            System.out.println(Thread.currentThread().getName()+" ahah");
            ctx.end("haha");
        });
        server.requestHandler(router).listen(8888);
    }
}

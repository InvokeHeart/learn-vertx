package org.stop.learnvertx;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;

public class EventLoop {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        HttpServer httpServer = vertx.createHttpServer();
        Router router = Router.router(vertx);
        router.route(HttpMethod.GET, "/hello").handler(rc -> {
            rc.vertx().eventBus().send("hello.world", "this is hello from event Bus");
            System.out.println(Thread.currentThread().getName()+" !!!");
            rc.end("success");
        });

        httpServer.requestHandler(router).listen(9999);



    }


}

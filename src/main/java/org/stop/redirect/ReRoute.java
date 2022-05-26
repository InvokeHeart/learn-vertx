package org.stop.redirect;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;

public class ReRoute {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        Router router = Router.router(vertx);

        //转发
        //router.route("/").handler(rc->rc.reroute(HttpMethod.GET,"/static/index.html"));
        //重定向
        router.route("/").handler(rc -> rc.response().putHeader("Location", "/static/index.html").setStatusCode(302).end());
        router.route("/static/*").handler(StaticHandler.create());


        Future<HttpServer> future = Future.future(x -> {
            vertx.createHttpServer().requestHandler(router).listen(9999, x);
        });
        future.onSuccess(s -> System.out.println("startUp successful"));
    }
}

package org.stop.promise;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.Router;

public class VertxParam {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        Router router = Router.router(vertx);
        router.routeWithRegex("/([a-z0-9/]*)\\.?(txt|json|png|svg|)").handler(rc -> {
            System.out.println("here....");
            HttpServerRequest request = rc.request();
            String param0 = request.getParam("param0");
            String param1 = request.getParam("param1");
            System.out.println(param0);
            System.out.println(param1);
        });

        Handler<HttpServer> handler = http -> {
            System.out.println(http.actualPort() + " start...");
        };
        vertx.createHttpServer().requestHandler(router).listen(9999).onSuccess(handler);
    }
}

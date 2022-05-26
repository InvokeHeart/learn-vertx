package org.stop.learnvertx;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.Session;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;

import java.util.UUID;

public class VertxSession {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new MainVerticle());
        HttpServer httpServer = vertx.createHttpServer();
        Router router = Router.router(vertx);
        router.route(HttpMethod.GET, "/session").
                handler(SessionHandler.create(LocalSessionStore.create(vertx))).
                handler(rc -> {
                    Session session = rc.session();
                    System.out.println(session.id());
                    Object k1 = session.get("k1");
                    if (k1 != null) {
                        System.out.println(k1);
                    } else {
                        session.put("k1", "v1");
                    }
                    UUID uuid = UUID.randomUUID();
                    session.put("uuid", uuid);
                    System.out.println(uuid);
                    rc.next();
                }).handler(rc -> {
                    rc.response().end("success");
                    System.out.println(((String) rc.session().get("k1")));
                });

        httpServer.requestHandler(router).listen(9999).onSuccess(success -> {
            System.out.println(success.actualPort());
        }).onFailure(Throwable::printStackTrace);
    }
}

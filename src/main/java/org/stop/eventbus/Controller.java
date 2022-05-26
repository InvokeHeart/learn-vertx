package org.stop.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class Controller extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        Router router = Router.router(vertx);
        router.get("/query/:id").handler(this::doProcess);
        vertx.createHttpServer().requestHandler(router).listen(8082).
                onSuccess(server -> startPromise.complete()).onFailure(startPromise::fail);
    }
    private void doProcess(RoutingContext routingContext) {
        EventBus eventBus = routingContext.vertx().eventBus();
        String id = routingContext.pathParam("id");
        eventBus.<JsonArray>request(Key.QUERY,null, as -> {
            if (as.succeeded()) {
                handleResponse(as.result().body(), routingContext);
            }
        });
    }


    private void handleResponse(JsonArray body, RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();
        response.putHeader("Content-Type", "text/html;charset=utf-8");
        response.end(body.encodePrettily());
    }
}

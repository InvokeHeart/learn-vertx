package org.stop.config;

import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.handler.LoggerHandler;

public class ConfigVerticle extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        ConfigStoreOptions cfg =
                new ConfigStoreOptions().setType("file").setFormat("json").setConfig(new JsonObject().put("path",
                        "conf.json"));
        ConfigRetrieverOptions store = new ConfigRetrieverOptions().addStore(cfg);
        ConfigRetriever configRetriever = ConfigRetriever.create(vertx, store);
        configRetriever.getConfig(asResult -> {
            if (asResult.succeeded()) {
                JsonObject http = asResult.result().getJsonObject("http");
                Integer port = http.getInteger("port");
                vertx.createHttpServer().
                        requestHandler(request -> request.response().end("hello!! config verticle..."))
                        .listen(port);
                startPromise.complete();
                System.out.println("deploy success");
            } else {
                startPromise.fail(asResult.cause());
            }
        });

    }
}

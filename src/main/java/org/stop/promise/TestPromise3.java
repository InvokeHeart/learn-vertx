package org.stop.promise;

import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

public class TestPromise3 extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        doConfig().compose(this::storeConfig).onSuccess(System.out::println).onSuccess(x->startPromise.complete()).
                onFailure(x->{
                    System.out.println("on failure");
                    startPromise.fail(x.getCause());
                });

    }

    private Future<String> storeConfig(JsonObject entries) {
        JsonObject http = entries.getJsonObject("http");
        String port = http.getString("port");
        System.out.println("B...");
        return Future.succeededFuture(port);
    }

    private Future<JsonObject> doConfig() {
        ConfigStoreOptions cfg =
                new ConfigStoreOptions().setType("file").setFormat("json").setConfig(new JsonObject().put("path",
                        "conf.json"));
        ConfigRetrieverOptions store = new ConfigRetrieverOptions().addStore(cfg);
        ConfigRetriever configRetriever = ConfigRetriever.create(vertx, store);
        System.out.println("A...");
        int i=1/0;
        return configRetriever.getConfig();
    }

}

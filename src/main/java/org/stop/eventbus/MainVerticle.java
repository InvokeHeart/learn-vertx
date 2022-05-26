package org.stop.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;

import java.util.concurrent.CompletableFuture;

public class MainVerticle extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {


        CompletableFuture.runAsync(() -> {
                    //DeploymentOptions opts = new DeploymentOptions().setWorker(false).setInstances(4);
                    //vertx.deployVerticle("org.stop.eventbus.Controller",opts);
                    vertx.deployVerticle("org.stop.eventbus.Controller");
                }).
                thenRun(() -> {
                    DeploymentOptions options = new DeploymentOptions().setConfig(config());
                    //options.setWorker(true).setInstances(2);
                    vertx.deployVerticle("org.stop.eventbus.Dao", options);
                }).
                thenRun(startPromise::complete);


    }
}

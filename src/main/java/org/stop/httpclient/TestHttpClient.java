package org.stop.httpclient;

import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;

public class TestHttpClient {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        WebClient webClient = WebClient.create(vertx);
        webClient.get(443, "api.apiopen.top", "/getJoke").ssl(true).send().onSuccess(rs -> {
            //需要添加jackson-databind
            JokeMessage jokeMessage = rs.bodyAsJson(JokeMessage.class);
            System.out.println(jokeMessage.getCode());
            for (Content content : jokeMessage.getResult()) {
                System.out.println(content);
            }
        }).onFailure(Throwable::printStackTrace).onComplete(ar -> {
            vertx.close();
        });

    }
}

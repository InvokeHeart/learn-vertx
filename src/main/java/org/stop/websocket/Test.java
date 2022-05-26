package org.stop.websocket;

import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.ServerWebSocket;

public class Test {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        HttpServer httpServer = vertx.createHttpServer();

        httpServer.webSocketHandler(Test::doSocket);

        httpServer.listen(8081).onSuccess(s -> {
            System.out.println(s.actualPort());
        });
    }

    private static void doSocket(ServerWebSocket webSocket) {
        Promise<Integer> promise = Promise.promise();
        webSocket.setHandshake(promise.future());

        webSocket.handler(bf -> {
            String string = bf.getString(0, bf.length());
            System.out.println(string);
            webSocket.writeTextMessage(string);
        });
    }
}

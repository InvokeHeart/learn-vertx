package org.stop.websocket;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.AsyncFile;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

import java.nio.charset.StandardCharsets;
import java.util.Set;

public class upload {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        HttpServer httpServer = vertx.createHttpServer();
        Router router = Router.router(vertx);
        router.route(HttpMethod.POST, "/upload").handler(rc -> {
            HttpServerRequest request = rc.request();
            request.setExpectMultipart(true);
            request.uploadHandler(hsf -> {
                hsf.streamToFileSystem("./" + hsf.filename(), as -> {
                    if (as.succeeded()) {
                        System.out.println("success");
                    }
                });
            });
        });

        router.route(HttpMethod.GET, "/files").handler(rc -> {
            HttpServerResponse response = rc.response();
            vertx.fileSystem().readDir("./").onSuccess(xx -> {
                xx.forEach(x -> {
                    Buffer buffer;
                    response.setChunked(true);
                    buffer = Buffer.buffer();
                    buffer.appendBytes(("<a href=' " + x + "' " + "></a>").getBytes());
                });
                response.end();
            });
        });

        httpServer.requestHandler(router).listen(9999);
    }
}

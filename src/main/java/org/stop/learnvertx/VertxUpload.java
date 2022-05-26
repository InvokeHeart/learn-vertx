package org.stop.learnvertx;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.Router;

public class VertxUpload {
    public static void main(String[] args) {
        var vertx = Vertx.vertx();
        Router router = Router.router(vertx);
        router.route(HttpMethod.POST, "/upload").
                handler(rc -> {
                            HttpServerRequest request = rc.request();
                            request.setExpectMultipart(true);
                            request.uploadHandler(upload -> {
                                System.out.println(upload.filename());
                                upload.streamToFileSystem("file/" + upload.filename());
                                if (request.isEnded()) {
                                    rc.response().end("ok!");
                                }
                            });
                        }
                );


        vertx.createHttpServer().requestHandler(router).
                listen(9999).onSuccess(hs -> System.out.println("启动:" + hs.actualPort()));

    }


}

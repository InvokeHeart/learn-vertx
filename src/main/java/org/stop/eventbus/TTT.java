package org.stop.eventbus;

import io.vertx.core.Future;

import java.util.Locale;

public class TTT {
    public static void main(String[] args) {
        Future.succeededFuture("jack").compose(x -> Future.succeededFuture(x.toUpperCase(Locale.ROOT))).onSuccess(System.out::println);
    }
}

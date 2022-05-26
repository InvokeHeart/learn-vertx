package org.stop;

import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

public class TT {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.setTimer(1000L,x->{
            System.out.println(Thread.currentThread().getName()+"===>"+x+"==>"+LocalTime.now());
        });


        System.out.println("end...");
    }
}

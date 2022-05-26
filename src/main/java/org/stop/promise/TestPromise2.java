package org.stop.promise;

import io.vertx.core.Promise;

public class TestPromise2 {
    public static void main(String[] args) {
        Promise<String> success = Promise.promise();
        Promise<String> fail = Promise.promise();
        System.out.println(success);
        System.out.println(fail);

        success.complete("hello..");
        System.out.println(success);

        fail.fail("some failure...");
        System.out.println(fail);
    }
}

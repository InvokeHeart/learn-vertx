package org.stop.redis;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.redis.client.*;

import java.util.*;

public class TestRedis extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        RedisOptions opts = new RedisOptions().setType(RedisClientType.STANDALONE).setConnectionString("redis://192" +
                ".168.42.102:6379/1");
        Redis client = Redis.createClient(vertx, opts);
        RedisAPI redis = RedisAPI.api(client);
        client.connect(as -> {
            if (as.succeeded()) {
                redis.zrevrangebyscore(List.of("user55:_danmu","+inf","-inf")).onSuccess(System.out::println);

            }
        });

    }

}

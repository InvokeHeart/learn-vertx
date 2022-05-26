package org.stop.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.mysqlclient.MySQLClient;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.Tuple;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class Dao extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        // Pool options
        PoolOptions poolOptions = new PoolOptions()
                .setMaxSize(5).setMaxWaitQueueSize(-1);
        MySQLPool pool = MySQLPool.pool(vertx, MySQLOptionUtil.getMysqlOption(config()), poolOptions);

        vertx.eventBus().consumer(Key.QUERY, message -> {
            JsonArray jsonArray = new JsonArray();
            pool.getConnection().compose(sqlConnection -> sqlConnection.preparedQuery("select * from role")
                    .execute()
                    .onComplete(ar -> sqlConnection.close()).
                    onSuccess(rows -> {
                        System.out.println(Thread.currentThread().getName()+"===> dao ");
                        for (Row row : rows) {
                            JsonObject entries = new JsonObject();
                            entries.put("id", row.getInteger(0));
                            entries.put("name", row.getString(1));
                            entries.put("name_zh", row.getString(2));
                            jsonArray.add(entries);
                        }

                        message.reply(jsonArray);
                    }).onFailure(Throwable::printStackTrace));

        });

        startPromise.complete();


    }
}

package org.stop.mysql;

import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.Tuple;
import io.vertx.sqlclient.templates.SqlTemplate;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ConnectMySQL {
    public static void main(String[] args) {
        MySQLConnectOptions connectOptions = new MySQLConnectOptions()
                .setPort(3306)
                .setHost("192.168.42.102")
                .setDatabase("security")
                .setUser("root")
                .setPassword("882434");


        // Pool options
        PoolOptions poolOptions = new PoolOptions()
                .setMaxSize(5).setMaxWaitQueueSize(-1);

        // Create the client pool
        MySQLPool pool = MySQLPool.pool(connectOptions, poolOptions);
        pool.getConnection().compose(sqlConnection -> sqlConnection.query("select * from role").execute().onComplete(ar -> sqlConnection.close()).
                onSuccess(rows -> {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("all===>" + Thread.currentThread().getName());
                    for (Row row : rows) {
                        System.out.println(row.getInteger(0));
                        System.out.println(row.getString(1));
                        System.out.println(row.getString(2));
                    }
                }).onFailure(Throwable::printStackTrace));

        System.out.println("=====================");
        pool.getConnection().compose(sqlConnection -> SqlTemplate.forQuery(sqlConnection, "select * from role where " +
                        "id = #{id}").
                execute(Map.of("id", 1)).onComplete(ar -> sqlConnection.close()).onSuccess(rows -> {
                    //sqlTemplate
                    System.out.println("sqlTemplate...");
                    for (Row row : rows) {
                        System.out.println(row.getInteger(0));
                        System.out.println(row.getString(1));
                        System.out.println(row.getString(2));
                    }
                }));
    }
}

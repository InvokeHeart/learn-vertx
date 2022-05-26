package org.stop.eventbus;

import io.vertx.core.json.JsonObject;
import io.vertx.mysqlclient.MySQLConnectOptions;

public class MySQLOptionUtil {
    public static MySQLConnectOptions getMysqlOption(JsonObject config) {
        JsonObject mysql = config.getJsonObject("mysql");
        return new MySQLConnectOptions()
                .setPort(mysql.getInteger("port"))
                .setHost(mysql.getString("host"))
                .setDatabase(mysql.getString("database"))
                .setUser(mysql.getString("username"))
                .setPassword(mysql.getString("password"));
    }
}

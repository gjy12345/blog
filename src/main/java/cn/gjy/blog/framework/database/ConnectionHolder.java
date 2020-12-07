package cn.gjy.blog.framework.database;

import java.sql.Connection;

/**
 * @author gujianyang
 * @date 2020/11/26
 */
public class ConnectionHolder {

    private static ThreadLocal<Connection> connectionThreadLocal=new ThreadLocal<>();

    public static void setConnection(Connection connection){
        connectionThreadLocal.set(connection);
    }

    public static void removeConnection(){
        connectionThreadLocal.set(null);
    }

    public static Connection getConnection(){
        return connectionThreadLocal.get();
    }
}

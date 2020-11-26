package cn.gjy.framerwork.database;

import java.sql.Connection;

/**
 * @author gujianyang
 * @date 2020/11/26
 */
public class ConnectionTool {

    private static ThreadLocal<Connection> connectionThreadLocal=new ThreadLocal<>();

    public static void setConnection(Connection connection){
        connectionThreadLocal.set(connection);
    }

    public static void removeConnection(){
        connectionThreadLocal.set(null);
    }
}

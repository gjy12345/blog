package cn.gjy.blog.framework.database.datasource.impl;

import cn.gjy.blog.framework.database.datasource.QueueDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author gujianyang
 * @date 2020/12/1
 */
public class BaseQueueDataSourceImpl implements QueueDataSource {

    @Override
    public Connection getConnection() {
        return null;
    }

    @Override
    public void releaseConnect(Connection connection) {

    }

    @Override
    public void close() {

    }

    @Override
    public void deregisterDriver() {

    }

    protected Connection createConnection(String url,String username,String password) throws SQLException {
        if(url==null){
            throw new RuntimeException("请填写数据库url");
        }
        if(username==null||password==null)
            return DriverManager.getConnection(url);
        return DriverManager.getConnection(url,username,password);
    }

    protected String guessDriver(String url){
        if(url==null)
            throw new RuntimeException("url 不能为空");
        if(url.contains(":")){
            String[] arr=url.split(":");
            if(arr.length>2){
                switch (arr[1]){
                    case "mysql":
                        return "com.mysql.cj.jdbc.Driver";
                    case "oracle":
                        return "oracle.jdbc.driver.OracleDriver";
                    case "microsoft":
                        return "com.microsoft.jdbc.sqlserver.SQLServerDriver";
                }
            }
        }
        throw new RuntimeException("无法判断url所属数据库类型");
    }
}

package cn.gjy.blog.framework.database;

import cn.gjy.blog.framework.annotation.Component;
import cn.gjy.blog.framework.annotation.FindByClass;
import cn.gjy.blog.framework.annotation.InitObject;
import cn.gjy.blog.framework.database.datasource.QueueDataSource;
import cn.gjy.blog.framework.database.datasource.impl.QueueDataSourceImpl;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author gujianyang
 * @date 2020/11/28
 */
@Component
public class ConnectionUtil {

    @FindByClass(QueueDataSourceImpl.class)
    @InitObject
    private static QueueDataSource queueDataSource;

    public static Connection getConnection() throws SQLException {
        return queueDataSource.getConnection();
    }

    public static void setQueueDataSource(QueueDataSource queueDataSource) {
        ConnectionUtil.queueDataSource = queueDataSource;
    }

    public static void releaseConnect(Connection connection) {
        queueDataSource.releaseConnect(connection);
    }
}

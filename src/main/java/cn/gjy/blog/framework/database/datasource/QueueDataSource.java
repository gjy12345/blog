package cn.gjy.blog.framework.database.datasource;

import java.sql.Connection;

public interface QueueDataSource {

    Connection getConnection();

    void releaseConnect(Connection connection);

    void close();

    void deregisterDriver();
}

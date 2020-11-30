package cn.gjy.framerwork.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author gujianyang
 * @date 2020/11/28
 */
public class ConnectionUtil {

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test",
                "root", "956115488");
    }

    public static void releaseConnect(Connection connection) {
        try {
            connection.close();
            ConnectionHolder.setConnection(null);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}

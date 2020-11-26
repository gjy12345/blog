package cn.gjy.framerwork;

import cn.gjy.framerwork.database.BeanAssignment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * author:gujianyang
 * date:2020/11/23
 */
public class TestDb {

    public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test",
                "root", "956115488");
        ResultSet resultSet;//=connection.createStatement().executeQuery("select * from apk_log;");
////        List<Model> models = BeanAssignment.assignmentBeanList(resultSet, Model.class);
////        for (int i = 0; i < models.size(); i++) {
////            System.out.println(models.get(i));
////
////        }
//
        resultSet=connection.createStatement().executeQuery("select * from test order by id;");
        System.out.println(BeanAssignment.assignmentBean(resultSet, Model.class));
        connection.close();
//        System.out.println(CurdTool.insert("update test set sdk=? where id =?;",
//                connection,  12.2,1));
    }
}

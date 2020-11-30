package cn.gjy.framerwork;

import cn.gjy.framerwork.Invocation.DaoInvocationHandlerImpl;
import cn.gjy.framerwork.annotation.BindParam;
import cn.gjy.framerwork.annotation.Select;
import cn.gjy.framerwork.database.BeanAssignment;
import cn.gjy.framerwork.database.ConnectionHolder;
import cn.gjy.framerwork.database.ConnectionUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * author:gujianyang
 * date:2020/11/23
 */
public class TestDb {

    public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
//        Class.forName("com.mysql.cj.jdbc.Driver");
//        Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test",
//                "root", "956115488");
//        ResultSet resultSet;//=connection.createStatement().executeQuery("select * from apk_log;");
//////        List<Model> models = BeanAssignment.assignmentBeanList(resultSet, Model.class);
//////        for (int i = 0; i < models.size(); i++) {
//////            System.out.println(models.get(i));
//////
//////        }
////
//        resultSet=connection.createStatement().executeQuery("select * from test order by id;");
//        Model[] models = BeanAssignment.assignmentBeanArray(resultSet, Model.class);
//        for (int i = 0; i < models.length; i++) {
//            System.out.println(models[i]);
//        }
//        connection.close();
//        System.out.println(CurdTool.insert("update test set sdk=? where id =?;",
//                connection,  12.2,1));


        TestMapper mapper= new DaoInvocationHandlerImpl<TestMapper>(TestMapper.class).getProxy();
        ConnectionHolder.setConnection(ConnectionUtil.getConnection());
        System.out.println(mapper.selectModels());
        System.out.println(mapper.selectModelsByArgs(1));
        String[] strings = mapper.selectModelUserNameByArgs(1);
        System.out.println(mapper.selectModelUserNameByArgs1(1));
        for (int i = 0; i < strings.length; i++) {
            System.out.println(strings[i]);
        }
        ConnectionHolder.removeConnection();
    }

    public static interface TestMapper{

        @Select("select * from test;")
        List<Model> selectModels();

        @Select("select * from test where id=#{id}")
        List<Model> selectModelsByArgs(@BindParam("id")Integer id);

        @Select("select user_name from test where id=#{id}")
        String[] selectModelUserNameByArgs(@BindParam("id")Integer id);

        @Select("select user_name from test where id=#{id}")
        String selectModelUserNameByArgs1(@BindParam("id")Integer id);
    }
}

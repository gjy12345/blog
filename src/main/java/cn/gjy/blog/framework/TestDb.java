package cn.gjy.blog.framework;

import java.lang.reflect.Method;
import java.sql.SQLException;

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
        Method method;
//        TestMapper mapper= new DaoInvocationHandlerImpl<TestMapper>(TestMapper.class).getProxy();
//        ConnectionHolder.setConnection(ConnectionUtil.getConnection());
//        System.out.println(mapper.selectModels());
//        System.out.println(mapper.selectModelsByArgs(1));
//        String[] strings = mapper.selectModelUserNameByArgs(1);
//        System.out.println(mapper.selectModelUserNameByArgs1(1));
//        for (int i = 0; i < strings.length; i++) {
//            System.out.println(strings[i]);
//        }
//        ConnectionHolder.removeConnection();

    }

    public static interface TestMapper{


    }
}

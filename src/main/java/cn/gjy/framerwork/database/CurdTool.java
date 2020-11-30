package cn.gjy.framerwork.database;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Date;
import java.util.List;

/**
 * @author gujianyang
 * @date 2020/11/24
 * 增删查改工具类
 * 传递sql 值 自动绑定
 */
public class CurdTool {

    public static int insertList(String sql, Connection connection,List<Object[]> values) throws SQLException {
        int result=0;
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            Object[] objects;
            for (int i = 0; i < values.size(); i++) {
                objects=values.get(i);
                setPreparedStatementValues(preparedStatement,objects);
                result+=preparedStatement.executeUpdate();
            }
        }
        return result;
    }

    /**
     *
     * @param sql
     * @param connection
     * @param objects
     * @return 修改，插入，更新条数，如果有多条sql，只返回第一个结果。
     * @throws SQLException
     */
    public static int insert(String sql,Connection connection,Object... objects) throws SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            setPreparedStatementValues(preparedStatement,objects);
            return preparedStatement.executeUpdate();
        }
    }

    public static int update(String sql,Connection connection,Object... objects) throws SQLException {
        return insert(sql,connection,objects);
    }

    public static int delete(String sql,Connection connection,Object... objects) throws SQLException {
        return insert(sql,connection,objects);
    }

    /**
     * 获取多个结果集 返回一个 ArrayList
     * @param sql
     * @param connection
     * @param t
     * @param objects
     * @param <T>
     * @return
     * @throws SQLException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     */
    public static <T> List<T> selectList(String sql,Connection connection,Class<T> t,Object... objects) throws SQLException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        ResultSet resultSet = null;
        try(PreparedStatement preparedStatement=connection.prepareStatement(sql)) {
            setPreparedStatementValues(preparedStatement,objects);
            resultSet=preparedStatement.executeQuery();
            return BeanAssignment.assignmentBeanList(resultSet,t);
        }finally {
            if(resultSet!=null)
                resultSet.close();
        }
    }

    public static <T> T[] selectArray(String sql,Connection connection,Class<T> t,Object... objects) throws SQLException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        ResultSet resultSet = null;
        try(PreparedStatement preparedStatement=connection.prepareStatement(sql)) {
            setPreparedStatementValues(preparedStatement,objects);
            resultSet=preparedStatement.executeQuery();
            return BeanAssignment.assignmentBeanArray(resultSet,t);
        }finally {
            if(resultSet!=null)
                resultSet.close();
        }
    }

    /**
     * 获取单个结果集 如果有多个 只取第一个
     * @param sql
     * @param connection
     * @param t
     * @param objects
     * @param <T> 实体类
     * @return
     * @throws SQLException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     */
    public static <T> T selectOne(String sql,Connection connection,Class<T> t,Object... objects) throws SQLException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        ResultSet resultSet = null;
        try(PreparedStatement preparedStatement=connection.prepareStatement(sql)) {
            setPreparedStatementValues(preparedStatement,objects);
            resultSet=preparedStatement.executeQuery();
            return BeanAssignment.assignmentBean(resultSet,t);
        }finally {
            if(resultSet!=null)
                resultSet.close();
        }
    }

    private static void setPreparedStatementValues(PreparedStatement preparedStatement,Object[] objects) throws SQLException {
        Object o;
        for (int i = 0; i < objects.length; i++) {
            o=objects[i];
            if(o==null){
                preparedStatement.setNull(i+1, Types.NULL);
            }else if(o instanceof String){
                preparedStatement.setString(i+1,(String) o);
            }else if(o instanceof Date){
                preparedStatement.setDate(i+1, new java.sql.Date(((Date)o).getTime()));
            }else if(o instanceof Number){
                Number number= (Number) o;
                if(o instanceof Integer){
                    preparedStatement.setInt(i+1,number.intValue());
                }else if(o instanceof Double){
                    preparedStatement.setDouble(i+1,number.doubleValue());
                }else if(o instanceof Long){
                    preparedStatement.setLong(i+1,number.longValue());
                }else if(o instanceof Float){
                    preparedStatement.setFloat(i+1,number.floatValue());
                }else if(o instanceof Byte){
                    preparedStatement.setByte(i+1,number.byteValue());
                }else if(o instanceof Short){
                    preparedStatement.setShort(i+1,number.shortValue());
                }else if(o instanceof BigDecimal){
                    preparedStatement.setBigDecimal(i+1, BigDecimal.valueOf(number.doubleValue()));
                }
            }
        }
    }
}

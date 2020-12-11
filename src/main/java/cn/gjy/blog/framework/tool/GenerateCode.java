package cn.gjy.blog.framework.tool;

import cn.gjy.blog.framework.annotation.Alias;
import cn.gjy.blog.framework.database.ConnectionUtil;
import cn.gjy.blog.framework.database.TypeParseEngine;
import cn.gjy.blog.framework.database.datasource.impl.QueueDataSourceImpl;

import java.math.BigDecimal;
import java.sql.*;

/**
 * @Author gujianyang
 * @Date 2020/12/7
 * @Class GenerateCode
 */
public class GenerateCode {

    public static void main(String[] args) throws SQLException {
        QueueDataSourceImpl queueDataSource=new QueueDataSourceImpl();
        ConnectionUtil.setQueueDataSource(queueDataSource);
        Connection connection= ConnectionUtil.getConnection();
        String table="category";
        ResultSet query = connection.createStatement().executeQuery("select * from " + table);
        ResultSetMetaData metaData = query.getMetaData();
        int columnCount = metaData.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            String columnName = metaData.getColumnName(i);
            if(columnName.contains("_")){
                StringBuilder sb=new StringBuilder();
                boolean nextBig=false;
                char[] charArray = columnName.toCharArray();
                System.out.println("\t@Alias(\""+columnName+"\")");
                for (int j = 0; j < charArray.length; j++) {
                    if(charArray[j]=='_'){
                        nextBig=true;
                        continue;
                    }
                    if(nextBig){
                        if(charArray[j]>='a'&&charArray[j]<='z'){
                            sb.append((char)(charArray[j]-32));
                        }else
                            sb.append(charArray[j]);
                        nextBig=false;
                        continue;
                    }
                    sb.append(charArray[j]);
                }
                columnName=sb.toString();
            }
            switch (metaData.getColumnType(i)){
                case Types.INTEGER:
                case Types.SMALLINT:
                case Types.TINYINT:
                    System.out.println("\tprivate Integer "+columnName+";");
                    break;
                case Types.BIGINT:
                    System.out.println("\tprivate Long "+columnName+";");
                    break;
                case Types.FLOAT:
                case Types.DOUBLE:
                    System.out.println("\tprivate Double "+columnName+";");
                    break;
                case Types.NUMERIC:
                    System.out.println("\tprivate BigDecimal "+columnName+";");
                    break;
                case Types.LONGVARCHAR:
                case Types.LONGNVARCHAR:
                case Types.CHAR:
                case Types.VARCHAR:
                    System.out.println("\tprivate String "+columnName+";");
                    break;
                case Types.BIT:
                    System.out.println("\tprivate Byte "+columnName+";");
                    break;
                case Types.DATE:
                case Types.TIME:
                case Types.TIMESTAMP:
                    System.out.println("\tprivate Date "+columnName+";");
                    break;
                default:
                    System.out.println(columnName + " " + metaData.getColumnType(i));
                    break;
            }
        }
        queueDataSource.close();
    }
}

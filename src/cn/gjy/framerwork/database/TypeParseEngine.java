package cn.gjy.framerwork.database;

import cn.gjy.framerwork.annotation.DateStringFormat;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * author:gujianyang
 * date:2020/11/23
 * 根据数据库字段类型来转换
 */
public class TypeParseEngine {

    public static void parseNumber(int type,Object o, Field field, ResultSet resultSet,int index) throws SQLException, IllegalAccessException {
        Number number= (Number) resultSet.getObject(index);
        if(number==null)
            return;
        Class<?> fieldType = field.getType();
        if(fieldType==Integer.class||fieldType==int.class){
            field.set(o,number.intValue());
        }else if(fieldType==Double.class||fieldType==double.class){
            field.set(o,number.doubleValue());
        }else if(fieldType==Float.class||fieldType==float.class){
            field.set(o,number.floatValue());
        }else if(fieldType==Short.class||fieldType==short.class){
            field.set(o,number.shortValue());
        }else if(fieldType==Byte.class||fieldType==byte.class){
            field.set(o,number.byteValue());
        }else if(fieldType==Long.class||fieldType==long.class){
            field.set(o,number.longValue());
        }else if(fieldType==String.class){
            field.set(o,number.toString());
        }else if(fieldType==Date.class){
            Date date=new Date(number.longValue());
            field.set(o,date);
        }
    }

    public static void parseNumeric(int type,Object o, Field field, ResultSet resultSet,int index) throws SQLException, IllegalAccessException {
        BigDecimal bigDecimal=resultSet.getBigDecimal(index);
        if(bigDecimal==null)
            return;
        if(field.getType()==BigDecimal.class){
            field.set(o,bigDecimal);
        }else if(field.getType()==String.class){
            field.set(o,bigDecimal.toString());
        }else
            throw new RuntimeException("fieldType:"+field.getType().getName()+" sql:BigDecimal");
    }

    public static void parseString(int type,Object o, Field field, ResultSet resultSet,int index) throws SQLException, IllegalAccessException {
        if(field.getType()==String.class){
            field.set(o,resultSet.getString(index));
        }else
            throw new RuntimeException("parseString:"+field.getType().getName());
    }

    public static void parseBit(int type,Object o, Field field, ResultSet resultSet,int index) throws SQLException, IllegalAccessException {
        if(field.getType()==Boolean.class||field.getType()==boolean.class) {
            field.set(o, resultSet.getBoolean(index));
        }else {
            boolean b = resultSet.getBoolean(index);
            int a=b?1:0;
            if(field.getType()==Number.class){
                field.set(o,a);
            }else {
                throw new RuntimeException("parseBit:"+field.getType().getName());
            }
        }
    }

    private static ThreadLocal<Map<String, SimpleDateFormat>> threadLocal=new ThreadLocal<>();

    public static void parseData(int type,Object o, Field field, ResultSet resultSet,int index) throws SQLException, IllegalAccessException {
        Date date=resultSet.getDate(index);
        if(date==null)
            return;
        if(field.getType()==Date.class){
            field.set(o,date);
        }else if(field.getType()==String.class) {
            DateStringFormat format=field.getAnnotation(DateStringFormat.class);
            if(format!=null){
                Map<String, SimpleDateFormat> dateTimeFormatterMap=threadLocal.get();
                if(dateTimeFormatterMap==null){
                    dateTimeFormatterMap=new HashMap<>();
                    threadLocal.set(dateTimeFormatterMap);
                }
                SimpleDateFormat simpleDateFormat=dateTimeFormatterMap.get(format.value());
                if(simpleDateFormat==null){
                    simpleDateFormat=new SimpleDateFormat(format.value());
                    dateTimeFormatterMap.put(format.value(),simpleDateFormat);
                }
                field.set(o,simpleDateFormat.format(date));
            }else {
                field.set(o,resultSet.getDate(index).toString());
            }
        }
    }
}

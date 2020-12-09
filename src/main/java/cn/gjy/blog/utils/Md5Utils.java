package cn.gjy.blog.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author gujianyang
 * @Date 2020/12/9
 * @Class Md5Utils
 */
public class Md5Utils {
    public static String md5(String buffer) {
        String string ;
        char hexChar[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(buffer.getBytes());
            byte[] datas = md.digest(); //16个字节的长整数

            char[] str = new char[2 * 16];
            int k = 0;

            for (int i = 0; i < 16; i++) {
                byte b = datas[i];
                str[k++] = hexChar[b >>> 4 & 0xf];//高4位
                str[k++] = hexChar[b & 0xf];//低4位
            }
            string = new String(str);
        } catch (NoSuchAlgorithmException e) {
            return buffer;
        }
        return string;
    }
}

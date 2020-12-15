package cn.gjy.blog.framework.handle.impl;

import cn.gjy.blog.framework.annotation.Config;
import cn.gjy.blog.framework.handle.DataHandle;
import cn.gjy.blog.framework.log.SimpleLog;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

/**
 * @author gujianyang
 * @date 2020/11/30
 */
@Config(DataHandle.class)
public class FileDataHandle implements DataHandle {

    private static final SimpleLog log=SimpleLog.log(FileDataHandle.class);

    private static final int MAX_READ_SIZE=1000*1024*2;

    @Override
    public List<Class<?>> register() {
        return Collections.singletonList(File.class);
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, Method method, Object returnData) throws IOException, ServletException {
        if(returnData==null)
            return;
        File file= (File) returnData;
        if(!file.exists()||!file.isFile()){
            return;
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "inline;fileName=\""+ new String(file.getName().getBytes("utf-8"),"ISO8859-1") + "\"");
        OutputStream outputStream = response.getOutputStream();
        try(FileInputStream fileInputStream=new FileInputStream(file)) {
            byte[] arr;
//            log.v(fileInputStream.available()+"");
            if(fileInputStream.available()<=MAX_READ_SIZE){
                arr=new byte[fileInputStream.available()];
                fileInputStream.read(arr,0, arr.length);
                outputStream.write(arr,0,arr.length);
            }else {
                arr=new byte[MAX_READ_SIZE];
                int l;
                while ((l=fileInputStream.read(arr,0,arr.length))!=-1){
                    outputStream.write(arr,0,l);
                }
            }
            outputStream.flush();
        }
    }
}

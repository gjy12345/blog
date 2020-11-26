package cn.gjy.framerwork.tool;

import cn.gjy.Test;

import javax.servlet.ServletContext;
import java.io.File;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author gujianyang
 * @date 2020/11/25
 */
public class ClassTool {

    private static ServletContext servletContext;

    private static List<Class<?>> classes=null;

    public static List<Class<?>> loadProjectAllClasses() throws Exception {
        if(classes!=null)
            return classes;
        String path=null;
        if(isRunWithTomcat()){
            path=servletContext.getRealPath("/WEB-INF/classes");
        }
        if(path==null){
            path=ClassLoader.getSystemResource("").getPath();
        }
        if(path==null)
            return null;
        File file=new File(path);
        Set<String> paths=new HashSet<>();
        listFile(file,paths);
        Iterator<String> iterator = paths.iterator();
        String p;
        List<String> classesList=new ArrayList<>();
        while (iterator.hasNext()) {
            p=iterator.next();
            if(p.endsWith(".class")){
                int index=p.lastIndexOf(".class");
                String temp=p.substring(0,index)
                        .replace(file.getAbsolutePath(),"")
                        .replace(File.separator,".");
                if(temp.startsWith(".")&&temp.length()>1){
                    classesList.add(temp.substring(temp.indexOf(".")+1));
                }
            }
        }
        return loadClassList(classesList);
    }

    private static boolean isRunWithTomcat() {
        return servletContext!=null;
    }

    public static void setServletContext(ServletContext servletContext) {
        ClassTool.servletContext = servletContext;
    }

    private static List<Class<?>> loadClassList(List<String> classList) throws ClassNotFoundException {
        Class<?> c;
        List<Class<?>> classesList=new ArrayList<>();
        for (String s : classList) {
            //System.out.println(s);
            c=Class.forName(s);
            if(c.isInterface()){
                continue;
            }
            classesList.add(c);
            //System.out.println(c.getName());
        }
        ClassTool.classes=classesList;
        return classesList;
    }

    public static void listFile(File file, Set<String> paths){
        File[] files = file.listFiles();
        if(files!=null){
            for (File f : files) {
                if(f!=null&&f.isFile()){
                    paths.add(f.getAbsolutePath());
                }else if(f!=null&&f.isDirectory()){
                    listFile(f,paths);
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        loadProjectAllClasses();
    }
}

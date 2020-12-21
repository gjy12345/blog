package cn.gjy.blog.utils;

import cn.gjy.blog.model.CheckResult;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Author gujianyang
 * @Date 2020/12/9
 * @Class FileUtils
 */
public class FileUtils {

    //上传单一文件 返回保存文件名
    public static CheckResult<File> uploadSingleImage(HttpServletRequest request, File saveDir) throws Exception {
        return uploadSingleFile(request,saveDir,".jpg", ".jpeg", ".gif", ".png");
    }

    private static CheckResult<File> uploadSingleFile(HttpServletRequest request, File saveDir,
                                                      String... fileType) throws Exception{
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("UTF-8");
        upload.setFileSizeMax(1024*1024*10);
        List<FileItem> items = upload.parseRequest(request);
        int count=0,fileIndex=0;
        for (int i = 0; i < items.size(); i++) {
            if(!items.get(i).isFormField()){//判断为参数还是文件二进制
                fileIndex=i;
                count++;
            }
        }
        if(count>1)
            return CheckResult.createFailResult("一次只能上传一个文件!");
        if(count==0)
            return CheckResult.createFailResult("上传文件为空!");
        String name = items.get(fileIndex).getName();
        if(!name.contains("."))
            return CheckResult.createFailResult("上传文件类型不明确");
        String type=name.substring(name.lastIndexOf("."));
        for (int i = 0; i < fileType.length; i++) {
            if(fileType[i].equalsIgnoreCase(type)){
                break;
            }else if(i==fileType.length-1)
                return CheckResult.createFailResult("错误的上传格式");
        }
        File saveFile=new File(saveDir, UUID.randomUUID().toString());
        items.get(fileIndex).write(saveFile);
        return CheckResult.createSuccessResult(saveFile,"成功");
    }

    private static final String[] imageFileEnd={".jpg", ".jpeg", ".gif", ".png"};

    public static boolean isImageFile(String thumb) {
        if (thumb==null)
            return false;
        for (String s : imageFileEnd) {
            if(thumb.toLowerCase().endsWith(s)){
                return true;
            }
        }
        return false;
    }
}

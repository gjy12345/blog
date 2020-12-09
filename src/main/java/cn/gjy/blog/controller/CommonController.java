package cn.gjy.blog.controller;

import cn.gjy.blog.common.ContentString;
import cn.gjy.blog.framework.annotation.BindParam;
import cn.gjy.blog.framework.annotation.Controller;
import cn.gjy.blog.framework.annotation.ResponseBody;
import cn.gjy.blog.framework.annotation.Route;
import cn.gjy.blog.framework.config.FrameworkConfig;
import cn.gjy.blog.model.CheckResult;
import cn.gjy.blog.model.SysUser;
import cn.gjy.blog.utils.FileUtils;
import cn.gjy.blog.utils.VcodeUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author gujianyang
 * @Date 2020/12/8
 * @Class CommonController
 * 通用方法
 */
@Route("/common")
@Controller
public class CommonController {


    /**
     * 获取验证码
     * @param session
     * @param response
     * @return
     * @throws Exception
     */
    @Route("/vcode")
    public void getVerificationCode(HttpSession session, HttpServletResponse response) throws Exception {
        VcodeUtils vcodeUtils=new VcodeUtils();
        BufferedImage image = vcodeUtils.getImage();
        session.setAttribute(ContentString.V_CODE,vcodeUtils.getText());
        VcodeUtils.writeImage(image,response.getOutputStream());
    }

    //markdown编辑器上传图片
    @ResponseBody
    @Route(value = "/upload",method = Route.HttpMethod.POST)
    public Map<String,Object> uploadFile(HttpServletRequest request,
        @BindParam(value = ContentString.USER_SESSION_TAG,from = HttpSession.class) SysUser user) throws Exception {
        Map<String,Object> resultMap=new HashMap<>();
        if(user==null){
            resultMap.put("success",0);
            resultMap.put("message","请您先登录");
            return resultMap;
        }
        CheckResult<File> checkResult= FileUtils.uploadSingleImage(request,FrameworkConfig.baseSaveFileDir);
        if(checkResult.getResult()== CheckResult.State.FAIL){
            resultMap.put("success",0);
            resultMap.put("message",checkResult.getMsg());
        }else {
            resultMap.put("success",1);
            resultMap.put("message","上传成功!");
            resultMap.put("url",FrameworkConfig.contentPath+"/common/file?fileName="+checkResult.getData().getName());
        }
        return resultMap;
    }

    @Route("/file")
    public File getUploadFile(@BindParam("fileName") String fileName){
        if(fileName.contains("../"))
            return null;
        return new File(FrameworkConfig.baseSaveFileDir,fileName);
    }

    public static void main(String[] args) {

    }
}
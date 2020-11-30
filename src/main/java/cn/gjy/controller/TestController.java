package cn.gjy.controller;

import cn.gjy.framerwork.Invocation.TestService;
import cn.gjy.framerwork.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gujianyang
 * @date 2020/11/25
 */
@Controller
public class TestController {

    @InitObject
    private TestService testService;

    @Route("/test")
    public void test(@BindParam("arr") Integer[] arr,HttpServletResponse response) throws IOException {
        if(arr!=null&&arr.length>0){
            for (int i = 0; i < arr.length; i++) {
                response.getWriter().write("<br>"+arr[i]);
            }
        }
        response.getWriter().write("你好");
        return;
    }

    @Route(value = "/test1",method = {Route.HttpMethod.POST, Route.HttpMethod.GET})
    public void test1(HttpServletRequest request, HttpServletResponse response, @BindParam("key") String key
                , @BindParam("arr") String[] arr, @BindParam Model model) throws IOException {
        response.getWriter().write("1111111"+key);
        if(arr!=null&&arr.length>0){
            for (int i = 0; i < arr.length; i++) {
                response.getWriter().write("<br>"+arr[i]);
            }
        }
        response.getWriter().write("<br>"+model.toString());
        return;
    }

    @ResponseBody
    @Route(value = "/test2")
    public List<String> test2(){
        List<String> list=new ArrayList<>();
        list.add("1");
        list.add("2");
        return list;
    }
}

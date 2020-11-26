package cn.gjy.controller;

import cn.gjy.framerwork.annotation.BindParam;
import cn.gjy.framerwork.annotation.Controller;
import cn.gjy.framerwork.annotation.Route;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author gujianyang
 * @date 2020/11/25
 */
@Controller
public class TestController {


    @Route("/test")
    public void test(@BindParam("arr") Integer[] arr,HttpServletResponse response) throws IOException {
        if(arr!=null&&arr.length>0){
            for (int i = 0; i < arr.length; i++) {
                response.getWriter().write("<br>"+arr[i]);
            }
        }
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
}

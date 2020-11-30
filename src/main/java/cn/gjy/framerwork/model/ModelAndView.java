package cn.gjy.framerwork.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author gujianyang
 * @date 2020/11/29
 */
public class ModelAndView extends Model{

    private String view;

    public ModelAndView(HttpServletRequest request, HttpServletResponse response) {
        super(request,response);
    }

    public void setView(String view){
        this.view=view;
    }

    public String getView() {
        return view;
    }
}

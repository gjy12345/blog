package cn.gjy.blog.controller.blog;

import cn.gjy.blog.common.ContentString;
import cn.gjy.blog.framework.annotation.*;
import cn.gjy.blog.model.CheckResult;
import cn.gjy.blog.model.Comment;
import cn.gjy.blog.model.SysUser;
import cn.gjy.blog.model.TableData;
import cn.gjy.blog.service.CommentService;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Author gujianyang
 * @Date 2020/12/15
 * @Class CommentController
 * 文章评论
 */
@Controller
@Route("/article/common")
public class CommentController {

    @InitObject
    private CommentService commentService;

    @ResponseBody
    @Route("/list")
    public TableData<List<Comment>> getArticleComments(@BindParam("id") Integer id,
                                              @BindParam("page") Integer page){
        return commentService.getArticleComments(id,page);
    }


    @Route(value = "/action/comment",method = Route.HttpMethod.POST)
    @ResponseBody
    public CheckResult<Void> comment(@JsonRequestBody Comment comment,
                                     @BindParam(value = ContentString.USER_SESSION_TAG,
                                     from = HttpSession.class) SysUser user,
                                     @BindParam(value = ContentString.ADMIN_SESSION_TAG,
                                     from = HttpSession.class)SysUser admin){
        //评论
        return commentService.addNewComment(comment,user,admin);
    }

}

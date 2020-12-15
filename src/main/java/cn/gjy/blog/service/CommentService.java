package cn.gjy.blog.service;

import cn.gjy.blog.model.CheckResult;
import cn.gjy.blog.model.Comment;
import cn.gjy.blog.model.SysUser;
import cn.gjy.blog.model.TableData;

import java.util.List;

/**
 * @Author gujianyang
 * @Date 2020/12/15
 * @Class CommentService
 */
public interface CommentService {
    TableData<List<Comment>> getArticleComments(Integer id, Integer page);

    CheckResult<Void> addNewComment(Comment comment, SysUser user, SysUser admin);
}

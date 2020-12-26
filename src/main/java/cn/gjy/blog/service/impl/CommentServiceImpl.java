package cn.gjy.blog.service.impl;

import cn.gjy.blog.common.ContentString;
import cn.gjy.blog.dao.ArticleDao;
import cn.gjy.blog.dao.BlogDao;
import cn.gjy.blog.dao.CommentDao;
import cn.gjy.blog.framework.annotation.InitObject;
import cn.gjy.blog.framework.annotation.Service;
import cn.gjy.blog.framework.annotation.Transactional;
import cn.gjy.blog.framework.controller.HttpRequestUtil;
import cn.gjy.blog.framework.tool.XssTool;
import cn.gjy.blog.model.*;
import cn.gjy.blog.service.CommentService;
import cn.gjy.blog.utils.TimeUtils;

import java.util.List;

/**
 * @Author gujianyang
 * @Date 2020/12/15
 * @Class CommentServiceImpl
 */
@Service(CommentService.class)
public class CommentServiceImpl implements CommentService {

    @InitObject
    private CommentDao commentDao;

    @InitObject
    private BlogDao blogDao;

    @Override
    public TableData<List<Comment>> getArticleComments(Integer id, Integer page) {
        if(id==null)
            return new TableData<>();
        if(page==null){
            page=0;
        }else {
            page--;
        }
        TableData<List<Comment>> tableData=new TableData<>();
        tableData.setTotal(commentDao.selectArticleCommentsCount(id));
        tableData.setData(commentDao.selectArticleComments(id,(page*10),10));
        Integer userId=HttpRequestUtil.getUserId();
        tableData.getData().forEach(comment -> {
            if(comment.getUserId().equals(userId)){
                comment.setCanDelete(true);
            }
        });
        return tableData;
    }

    @Transactional
    @Override
    public CheckResult<Void> addNewComment(Comment comment, SysUser user, SysUser admin) {
        if(comment.getArticleId()==null)
            return CheckResult.createFailResult("参数错误!");
        if(comment.getContent().trim().length()==0){
            return CheckResult.createFailResult("评论不能为空");
        }else if(comment.getContent().trim().length()>200){
            return CheckResult.createFailResult("评论过长");
        }
        Article article=blogDao.selectBlogById(comment.getArticleId());
        if(article==null)
            return CheckResult.createFailResult("文章不存在");
        if(admin==null&&!article.getUserId().equals(user.getId())){
            if(article.getPublicityLevel().equals(Article.PubLevel.PRIVATE)){
                return CheckResult.createFailResult("找不到此文章");
            }else if(article.getPublicityLevel().equals(Article.PubLevel.PASSWORD)
                    &&!article.getPassword().equals(comment.getArticlePassword())){
                return CheckResult.createFailResult("无评价权限");
            }
            if(article.getComment().equals(Article.ArticleCommentState.DISABLE)){
                return CheckResult.createFailResult("博主已设置不能评论部此文章");
            }
        }
        if(user!=null){
            if(user.getId().equals(article.getUserId())){
                comment.setUserType(Comment.CommentUserType.AUTHOR);
            }else {
                comment.setUserType(Comment.CommentUserType.USER);
            }
            comment.setUserId(user.getId());
        }else {
            comment.setUserType(Comment.CommentUserType.ADMIN);
            comment.setUserId(admin.getId());
        }
        comment.setContent(XssTool.encode(comment.getContent().trim()));
        comment.setCreateTimeL(System.currentTimeMillis());
        comment.setCommonTime(TimeUtils.getSimpleDateFormat().format(comment.getCreateTimeL()));
        return commentDao.addNewComment(comment)==1?CheckResult.createSuccessResult(null,"评论成功!"):
                CheckResult.createFailResult("评论失败");
    }

}

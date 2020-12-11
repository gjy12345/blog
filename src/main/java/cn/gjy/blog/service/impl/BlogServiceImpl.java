package cn.gjy.blog.service.impl;

import cn.gjy.blog.common.ContentString;
import cn.gjy.blog.dao.BlogDao;
import cn.gjy.blog.dao.CommonDao;
import cn.gjy.blog.framework.annotation.InitObject;
import cn.gjy.blog.framework.annotation.Service;
import cn.gjy.blog.framework.annotation.Transactional;
import cn.gjy.blog.framework.log.SimpleLog;
import cn.gjy.blog.framework.tool.XssTool;
import cn.gjy.blog.model.*;
import cn.gjy.blog.service.BlogService;
import cn.gjy.blog.utils.BlogUtil;
import cn.gjy.blog.utils.StringUtils;
import cn.gjy.blog.utils.TimeUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;


/**
 * @Author gujianyang
 * @Date 2020/12/10
 * @Class BlogServiceImpl
 */
@Service(BlogService.class)
public class BlogServiceImpl implements BlogService{

    //描述长度
    private static final int DESCRIPTION_LENGTH=150;

    private static final SimpleLog log=SimpleLog.log(BlogServiceImpl.class);

    @InitObject
    private BlogDao blogDao;

    @InitObject
    private CommonDao commonDao;
    //发布博客
    @Transactional
    @Override
    public CheckResult<Integer> releaseNewBlog(Article article, SysUser sysUser,String ip) {
        CheckResult<Integer> result=initArticle(article,sysUser);
        if(result!=null)
            return result;
        article.setStatus(Article.ArticleState.RELEASE);
        article.setTimeStamp(System.currentTimeMillis());
        result = blogDao.releaseNewBlog(article) == 1 ?
                CheckResult.createSuccessResult(null, "发表成功!") :
                CheckResult.createFailResult("失败");
        //插入日志
        commonDao.insertLog(sysUser.getId(), SysOperation.OperationType.CREATE_NEW_BOLG,
                "发布博客:"+article.getTitle(),
                article.getCreateTime(),ip,"web");
        return result;
    }

    private static final int pageSize=10;

    @Override
    public TableData<List<Category>> getUserCategories(Integer page, SysUser sysUser, Category category) {
        if(page==null||page<=0)
            page=0;
        else
            page--;
        TableData<List<Category>> tableData=new TableData<>();
        category.setCreateUser(sysUser.getId());
        tableData.setTotal(blogDao.getUserBlogCategoriesCount(category));
        tableData.setData(blogDao.getUserBlogCategories(category,page,pageSize));
        tableData.getData().forEach(cate -> {
            cate.setBlogUseCount(blogDao.getBlogCountByCategory(cate.getId()));
        });
        return tableData;
    }

    @Override
    public CheckResult<Void> addUserCategory(SysUser sysUser, Category category,String ip) {
        if(StringUtils.isEmptyString(category.getName())){
            return CheckResult.createFailResult("分类名不能为空!");
        }
        int num=blogDao.selectUserCategoryByName(category.getName(),sysUser.getId());
        if(num>0)
            return CheckResult.createFailResult("分类名已存在!");
        if(category.getLock()==null)
            return CheckResult.createFailResult("锁定参数错误!");
        category.setCreateUser(sysUser.getId());
        category.setDescription(XssTool.encode(category.getDescription()));
        category.setName(XssTool.encode(category.getName()));
        category.setTimeStamp(System.currentTimeMillis());
        category.setCreateTime(TimeUtils.getSimpleDateFormat().format(new Date()));
        int result=blogDao.addNewCategory(category);
        if(result==1){
            commonDao.insertLog(sysUser.getId(),SysOperation.OperationType.ADD_NEW_CATEGORY,
                    "添加分类:"+category.getName(),category.getCreateTime(),ip,"web");
        }
        return result==1?CheckResult.createSuccessResult(null,"添加成功!"):
                CheckResult.createFailResult("添加失败");
    }

    //检测并格式化文章
    private CheckResult<Integer> initArticle(Article article,SysUser sysUser){
        if(article.getContent().trim().isEmpty()){
            return CheckResult.createFailResult("正文为空!");
        }else if(article.getTitle().trim().isEmpty()){
            return CheckResult.createFailResult("标题为空!");
        }else if(article.getDescription()!=null&&article.getDescription().trim().length()>DESCRIPTION_LENGTH){
            return CheckResult.createFailResult("描述过长");
        }else if(sysUser.getLock().equals(ContentString.LOCK)){
            return CheckResult.createFailResult("您的账号已被禁止发布博客,您可以先保存草稿并且练习管理员。");
        }else  if(article.getPublicityLevel()==null){
            return CheckResult.createFailResult("请设置博客访问权限");
        }
        try {
            article.setContent(BlogUtil.replaceUnsafeString(article.getContent()).trim());
        } catch (Exception e) {
            log.e(e.getMessage());
            return CheckResult.createFailResult("格式化博客发生了错误!请联系管理人员");
        }
        if(article.getComment()==null)
            article.setComment(Article.ArticleCommentState.ENABLE);
        else if(article.getComment()!=Article.ArticleCommentState.ENABLE&&
                article.getComment()!=Article.ArticleCommentState.DISABLE){
            return CheckResult.createFailResult("非法的评论管理参数");
        }
        article.setTitle(XssTool.encode(article.getTitle()));//过滤不安全的字符
        if(article.getDescription()==null){
            //截取
            if(article.getContent().length()<=DESCRIPTION_LENGTH){
                article.setDescription(article.getContent());
            }else {
                article.setDescription(article.getContent().substring(0,DESCRIPTION_LENGTH-3)
                        +"...");
            }
        }else {
            article.setDescription(XssTool.encode(article.getDescription()));
        }
        String nowTime = TimeUtils.getSimpleDateFormat().format(new Date());
        article.setCreateTime(nowTime);
        article.setUrl(UUID.randomUUID().toString().replace("-",""));
        //article.setType(); 检测类型
        if(article.getType()!=null){
            Category category=blogDao.selectUserCategoryById(article.getType());
            if(!category.getCreateUser().equals(sysUser.getId())){
                return CheckResult.createFailResult("此分类并不属于您.");
            }
        }
        if(article.getKeywords()==null||article.getKeywords().trim().isEmpty()){
            article.setKeywords("无");
        }
        if(article.getThumb()!=null&&(article.getThumb().trim().isEmpty()||article.getThumb().
                equalsIgnoreCase("undefined"))){
            article.setThumb(null);
        }
        article.setUserId(sysUser.getId());
        return null;
    }

}

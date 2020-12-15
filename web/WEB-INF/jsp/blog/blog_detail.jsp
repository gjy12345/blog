<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: gujianyang
  Date: 2020/12/11
  Time: 12:06 上午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/editormd/css/editormd.min.css"/>
<script src="${pageContext.request.contextPath}/static/editormd/js/editormd.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/editormd/css/editormd.preview.css" />
<div class="content-body">
    <div class="container">
        <div class="row">
            <main class="col-md-8">
                <article class="post post-1">
                    <header class="entry-header">
                        <h1 class="entry-title">${blog.title}</h1>
                        <div class="entry-meta">
                            <span class="post-category"><a href="#">${blog.typeName}</a></span>
                            <span class="post-date"><a href="#"><time class="entry-date"
                                                                      datetime="${blog.createTime}">${blog.createTime}</time></a></span>
                            <span class="post-author"><a href="#">${blog.userName}</a></span>
                            <span class="comments-link"><a href="#">${blog.common} 评论</a></span>
                            <span class="views-count"><a href="#">${blog.visit} 阅读</a></span>
                        </div>
                    </header>
                    <div id="test-markdown-view" style="padding-left: 0px;margin-right: 0px;padding-top: 0px">
                        <!-- Server-side output Markdown text -->
                        <textarea style="display:none;">${blog.markdown}</textarea>
                    </div>
                    <script src="${pageContext.request.contextPath}/static/editormd/lib/marked.min.js"></script>
                    <script src="${pageContext.request.contextPath}/static/editormd/lib/prettify.min.js"></script>
                    <script type="text/javascript">
                        $(function() {
                            let testView = editormd.markdownToHTML("test-markdown-view", {
                                // markdown : "[TOC]\n### Hello world!\n## Heading 2", // Also, you can dynamic set Markdown text
                                htmlDecode : false,  // Enable / disable HTML tag encode.
                                // htmlDecode : "style,script,iframe",  // Note: If enabled, you should filter some dangerous HTML tags for website security.
                            });
                        });
                    </script>
                </article>
<%--                <section class="comment-area" id="comment-area">--%>
<%--                    <hr>--%>
<%--                    <h3>发表评论</h3>--%>
<%--                    <div class="comment-form">--%>
<%--                        <div class="row">--%>
<%--                            <div class="col-md-12">--%>
<%--                                <label for="id_comment">评论：</label>--%>
<%--                                <textarea id="id_comment" class="text-area" required></textarea>--%>
<%--                                <c:if test="${blog.comment==0}">--%>
<%--                                    <button type="submit" class="btn btn-sm btn-primary" disabled>发表</button>--%>
<%--                                    <p style="color: red">作者已禁止评论</p>--%>
<%--                                </c:if>--%>
<%--                                <c:if test="${blog.comment==1}">--%>
<%--                                    <button type="submit" class="btn btn-sm btn-primary" >发表</button>--%>
<%--                                </c:if>--%>
<%--                            </div>--%>
<%--                        </div>    <!-- row -->--%>
<%--                    </div>--%>
<%--                </section>--%>
            </main>
            <aside class="col-md-4">
                <div class="widget widget-recent-posts">
                    <h3 class="widget-title">${blog.userName}的最新文章</h3>
                    <ul>
                        <c:forEach items="${recent_blog}" var="blog">
                            <li>
                                <a href="${pageContext.request.contextPath}/article/detail?url=${blog.url}">${blog.title}</a>
                                <div style="font-size: 6px;color: grey;float: right;">${blog.createTime}</div>
                            </li>
                        </c:forEach>
                    </ul>
                    <c:if test="${recent_blog.size()>0}">
                        <div style="text-align: center;">
                            <a href="">查看更多</a>
                        </div>
                    </c:if>
                </div>

                <div class="widget widget-category">
                    <h3 class="widget-title">分类</h3>
                    <ul>
                        <c:forEach items="${author_categories}" var="category">
                            <li>
                                <a href="#">${category.name}<span class="post-count">(${category.blogUseCount})</span></a>
                            </li>
                        </c:forEach>
                    </ul>
                    <c:if test="${author_categories.size()>0}">
                        <div style="text-align: center;">
                            <a href="">查看更多</a>
                        </div>
                    </c:if>
                </div>

                <div class="comment-list-panel">
                    <h4>评论列表，共 <span id="comment_count"></span> 条评论</h4>
                    <ul class="comment-list list-unstyled" id="comment_list">

                    </ul>
                    <div style="text-align: center;" id="look_more_comment">
                        <a href="javascript:void(0)" onclick="loadMore()">查看更多</a>
                    </div>
                    <div style="text-align: center;" id="no_comment">
                        <a href="">暂无评论，快来抢沙发吧!</a>
                    </div>
                </div>
<%--                <div class="rss">--%>
<%--                    <a href=""><span class="ion-social-rss-outline"></span> RSS 订阅</a>--%>
<%--                </div>--%>
                <section class="comment-area" id="comment-area">
                    <hr>
                    <h3>发表评论</h3>
                    <div class="comment-form">
                        <div class="row">
                            <div class="col-md-12">
                                <label for="id_comment">评论：</label>
                                <textarea id="id_comment" class="text-area" required></textarea>
                                <c:if test="${blog.comment==0}">
                                    <button type="submit" class="btn btn-sm btn-primary" disabled>发表</button>
                                    <p style="color: red">作者已禁止评论</p>
                                </c:if>
                                <c:if test="${blog.comment==1}">
                                    <button type="submit" onclick="comment()" class="btn btn-sm btn-primary" >发表</button>
                                </c:if>
                            </div>
                        </div>    <!-- row -->
                    </div>
                </section>
            </aside>
        </div>
    </div>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/weadmin/lib/layui/layui.js" charset="utf-8"></script>

    <script type="text/javascript">
        document.title='${blog.title}';
        let nowPage;
        let layer;
        let blogId=${blog.id};
        layui.use('layer', function(){
            layer = layui.layer;
        });
        function loadComment(page){
            nowPage=page;
            let commentCount=$("#comment_count");
            let json={};
            json['page']=page;
            json['id']=blogId;
            $.ajax({
                url: "${pageContext.request.contextPath}/article/common/list",
                type: "get",
                contentType: "application/json;charset=utf-8",
                data: json,
                xhrFields: {
                    withCredentials: true
                },
                crossDomain: true,
                success: function (data) {
                    console.log(data);
                    commentCount.html(data.total)
                    setCommentList(data.data,page);
                    if(data.total===0){
                        $("#no_comment").show()
                    }else {
                        $("#no_comment").hide()
                    }
                    if(page*10>=data.total){
                        $("#look_more_comment").hide()
                    }else {
                        $("#look_more_comment").show()
                    }
                },
                error: function () {
                    layer.msg('获取评论失败');
                }
            });
        }

        function setCommentList(data,page){
            let commentList=$("#comment_list");
            let html='';
            for(let i=0;i<data.length;i++){
                html=html+'<li class="comment-item">' +
                    '<span class="nickname"><span>'+(
                        (nowPage-1)*10+i+1
                    )+'#&nbsp;</span>'+data[i].userName+'<span style="color:orangered">&nbsp;' +
                    (data[i].userType==2?'作者':data[i].userType==1?'管理员':'') +
                    '</span></span>' +
                    '<time class="submit-date" datetime="'+data[i].commonTime+'">'+data[i].commonTime+'</time>' +
                    (data[i].canDelete?'<a href="javascript:void(0)" onclick="delete_comment('+data[i].id+')" style="color: lightslategray;">&nbsp;删除</a>':'')+
                    '<div class="text" style="text-indent: 2em">' +
                    ''+data[i].content+'' +
                    '</div>' +
                    '</li>';
            }
            if(page===1){
                commentList.html(html);
            }else {
                commentList.append(html);
            }
        }

        function loadMore(){
            loadComment(nowPage+1)
        }

        function delete_comment(id){

        }

        function comment(){
            let json={};
            let comment=$("#id_comment").val();
            if(comment.trim().length===0){
                layer.msg('评论不能为空!');
                return false;
            }
            json['articleId']=blogId;
            json['content']=comment;
            json['articlePassword']='${blog.password}';
            $.ajax({
                url: "${pageContext.request.contextPath}/article/common/action/comment",
                type: "post",
                contentType: "application/json;charset=utf-8",
                data: JSON.stringify(json),
                xhrFields: {
                    withCredentials: true
                },
                crossDomain: true,
                success: function (data) {
                    console.log(data)
                    if(data.result===1){
                        layer.msg('评论成功!')
                        $("#id_comment").val('')
                        loadComment(1);
                    }else {
                        layer.msg(data.msg);
                    }
                },
                error: function () {
                    layer.msg('评论失败');
                }
            });
        }

        loadComment(1)
    </script>
</div>


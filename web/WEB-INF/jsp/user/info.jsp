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
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/editormd/css/editormd.preview.css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/layui-page.css"/>
<style type="text/css">
    .gz {
        font-family: "SF Pro Display", Roboto, Noto, Arial, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", sans-serif;
        text-align: center;
        outline: 0;
        text-decoration: none;
        cursor: pointer;
        padding: 3px 20px 3px 20px;
        display: inline;
        box-sizing: border-box;
        height: 100%;
        font-size: 14px;
        background: linear-gradient(92deg, #ffba40 0%, #ff503e 37%, #ff2f50 81%, #ff1b40 100%);
        color: #fff;
        line-height: 26px;
        border-radius: 14px;
    }

    .nickname {
        font-family: "SF Pro Display", Roboto, Noto, Arial, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", sans-serif;
        text-align: center;
        outline: 0;
        text-decoration: none;
        cursor: pointer;
        padding: 10px 20px 3px 20px;
        display: inline;
        box-sizing: border-box;
        height: 100%;
        font-size: 22px;
        line-height: 100%;
        border-radius: 14px;
    }



</style>
<div class="content-body">
    <div class="container">
        <div class="row">
            <main class="col-md-8">
                <article class="post post-1">
                    <header class="entry-header">
                        <div style="text-align: left">
                            <c:if test="${showUser.face!=null}">
                                <img height="80px" style="display: inline" src="${showUser.face}"/>
                            </c:if>
                            <c:if test="${showUser.face==null}">
                                <img height="80px" src="${pageContext.request.contextPath}/static/img/face.png"/>
                            </c:if>
                            <div style="display: inline">
                                <h1 class="entry-title nickname" style="text-align: left;display: inline;">
                                    ${showUser.nickname}</h1>
                                <c:if test="${gz==false}">
                                    <span class="gz">关注</span>
                                </c:if>
                                <c:if test="${gz==true}">
                                    <span class="gz">已关注</span>
                                </c:if>
                            </div>
                        </div>
                        <div class="entry-meta" style="text-align: left;margin-top: 20px">
                            <span class="post-author"><a href="#">加入时间:&nbsp;${showUser.createTime}&nbsp;</a></span>
                            <span class="views-count"><a href="#">博客数:&nbsp;${allArticlesCount} </a></span>
                            <span class="views-count"><a href="#">阅读量:&nbsp;${allVisit} </a></span>
                        </div>
                        <div style="width: 100%;display: block" id="articles">


                        </div>
                        <div style="text-align: center;" id="no_blog" hidden>
                            <a href="">该用户太懒了,没写过博客!</a>
                        </div>
                        <div id="pages"></div>
                    </header>
                </article>
            </main>
            <aside class="col-md-4">

                <div class="widget widget-category">
                    <h3 class="widget-title">分类</h3>
                    <ul id="categoryList">
                    </ul>
                    <div style="text-align: center;" id="look_more_comment">
                        <a href="javascript:void(0)" onclick="loadMore()">查看更多</a>
                    </div>
                    <div style="text-align: center;" id="no_comment">
                        <a href="">该用户太懒了,没有一个分类!</a>
                    </div>
                </div>


            </aside>
        </div>
    </div>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/weadmin/lib/layui/layui.js"
            charset="utf-8"></script>

    <script type="text/javascript">
        document.title = '${showUser.nickname}';
        let nowPage;
        let layer;
        let userId =${showUser.id};
        let laypage;
        let nowArticlePage;
        layui.use('layer', function () {
            layer = layui.layer;
        });

        layui.use('laypage', function () {
            laypage = layui.laypage;
            loadUserArticles(1);
        });

        function initPages(count, current) {
            currentPage = current;
            laypage.render({
                elem: 'pages',
                curr: current,
                count: count //数据总数，从服务端得到
                , jump: function (obj, first) {
                    //obj包含了当前分页的所有参数，比如：
                    //首次不执行
                    if (!first) {
                        console.log('执行')
                        initData(obj.curr)
                    }
                }
            });
            $("#size").html('共有数据：' + count + ' 条');
        }

        function loadCategories(page) {
            nowPage = page;
            let commentCount = $("#comment_count");
            let json = {};
            json['page'] = page;
            json['userId'] = userId;
            $.ajax({
                url: "${pageContext.request.contextPath}/user/categoryList",
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
                    setCategoryList(data.data, page);
                    if (data.total === 0) {
                        $("#no_comment").show()
                    } else {
                        $("#no_comment").hide()
                    }
                    if (page * 10 >= data.total) {
                        $("#look_more_comment").hide()
                    } else {
                        $("#look_more_comment").show()
                    }
                },
                error: function () {
                    layer.msg('获取分类失败');
                }
            });
        }

        function loadMore() {
            loadCategories(nowPage + 1)
        }

        function setCategoryList(data, page) {
            let commentList = $("#categoryList");
            let html = '';
            for (let i = 0; i < data.length; i++) {
                html = html + '<li id="category' + ((page - 1) * 10 + i + 1) + '" ' +
                    'onclick="changeCategory(' + ((page - 1) * 10 + i + 1) + ',' + data[i].id + ')">' +
                    '<a href="javascript:void(0)">' + data[i].name + ' <span class="post-count">' +
                    '(' + data[i].blogUseCount + ')</span></a></li>';
            }
            if (page === 1) {
                commentList.html(html);
            } else {
                commentList.append(html);
            }
        }

        function loadUserArticles(page) {
            let json = {};
            json['page'] = page;
            json['userId'] = userId;
            nowArticlePage = page;
            $.ajax({
                url: "${pageContext.request.contextPath}/user/articleList",
                type: "get",
                contentType: "application/json;charset=utf-8",
                data: json,
                xhrFields: {
                    withCredentials: true
                },
                crossDomain: true,
                success: function (data) {
                    console.log(data);
                    setUserArticles(data.data)
                    initPages(data.total, nowPage)
                    if (data.total === 0) {
                        $("#no_blog").show()
                    } else {
                        $("#no_blog").hide()
                    }

                },
                error: function () {
                    layer.msg('获取文章失败');
                }
            });
        }

        function setUserArticles(data) {
            let html = ''
            for(let i=0;i<data.length;i++){
                html=html+'<header class="entry-header">' +
                    '<p class="entry-title">' +
                    '<a href="${pageContext.request.contextPath}/article/detail?url='+data[i].url+'" target="_blank">'+data[i].title+'</a></p>' +
                    '<div class="entry-content clearfix">' +
                    '<p>'+data[i].description+'</p>' +
                    '</div>' +
                    (data[i].thumb==null?'':'<div class="entry-content clearfix">' +
                        '<p><img src="'+data[i].thumb+'" alt="" style="max-height: 300px" width="auto"></p>' +
                        '</div>')+
                    '<div class="entry-meta">' +
                    '<span class="post-date"><a href="javascript:void(0)"><time class="entry-date" datetime="'+data[i].createTime+'">'+data[i].createTime+'</time></a></span>' +
                    '<span class="post-category"><a href="javascript:void(0)">分类: '+data[i].typeName+'</a></span>' +
                    '<span class="comments-link"><a href="javascript:void(0)"> 浏览 '+data[i].visit+'</a></span>' +
                    '<span class="comments-link"><a href="javascript:void(0)"> 评论 '+data[i].common+'</a></span>' +
                    '<span class="comments-link"><a href="javascript:void(0)"> 权限 '+data[i].pubLevelName+'</a></span>' +
                    '</div>' +
                    '</header>';
            }
            $("#articles").html(html)
        }

        loadCategories(1)
    </script>
</div>


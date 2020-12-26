<%--
Created by IntelliJ IDEA.
User: gujianyang
Date: 2020/10/12
Time: 10:41 上午
To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/custom_new.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/layui-page.css"/>
<div class="content-body">
    <div class="container">
        <div class="row" style="min-height: 500px">
            <main class="col-md-8">
                <div class="col-md-12">
                    <div class="input-group">
                        <input type="text" id="keyword" class="form-control" placeholder="请输入关键词" value="${keyword}"/>
                        <span class="input-group-addon" style="text-align: center"><a href="javascript:void(0)" onclick="doSearch()"><i class="glyphicon glyphicon-search"><span>搜索&nbsp;&nbsp;&nbsp;</span></i></a></span>
                    </div>
                </div>
                <div class="widget " id="article_result" style="margin-top: 30px">
                    <div id="articles">

                    </div>
                    <div id="pages">

                    </div>
                </div>

            </main>
            <aside class="col-md-4">
                <div class="widget " >
                    <ul style="list-style: none;padding-left: 0px;margin-top: 30px" id="user_result">
                        <li>
                            <div class="Box-row col-md-12">
                                <div class="d-flex gutter-condensed ">
                                    <div class="col-lg-3 " style="max-width: 25%">
                                        <img class="avatar avatar-user" style="height: auto" src="${pageContext.request.contextPath}/static/img/face.png" width="100%" height="100%" alt="avatar image">
                                    </div>
                                    <div class="col-lg-9">
                                        <div class="f4 text-bold lh-condensed mb-2 f">
                                            <div style="color: #0366d6;font-size: 16px">
                                                Joe Block
                                            </div>
                                            <span class="d-block text-normal text-gray">unixorn</span>
                                        </div>

                                        <p class="f6 mb-0">
                                            <a class="">
                                                <b class="red_text">new:</b> awesome-zsh-plugins
                                            </a>
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>
            </aside>
            <script src="${pageContext.request.contextPath}/static/weadmin/lib/layui/layui.js" charset="utf-8"></script>
            <script src="${pageContext.request.contextPath}/static/js/customer.js"></script>
            <script type="text/javascript">
                let userResult=$("#user_result");
                let articleResult=$("#article_result");
                let keyword=$("#keyword");
                let layer;
                let laypage;
                let currentArticlePage;
                layui.use(['layer'], function () {
                    layer = layui.layer;
                });
                layui.use('laypage', function () {
                    laypage = layui.laypage;
                    search(keyword.val(),1);
                });
                function search(key,page){
                    if(key.trim().length===0)
                        return;
                    let articles=$("#articles");
                    articles.empty();
                    userResult.empty();
                    $.ajax({
                        url: "${pageContext.request.contextPath}/search/article?keyword="+key+"&page="+page,
                        type: "post",
                        contentType: "application/json;charset=utf-8",
                        xhrFields: {
                            withCredentials: true
                        },
                        crossDomain: true,
                        success: function (data) {
                            console.log(data);
                            initArticle(data.data);
                            initPages(data.total,page)
                        },
                        error: function () {

                        }
                    });
                    $.ajax({
                        url: "${pageContext.request.contextPath}/search/user?keyword="+key+"&page="+page,
                        type: "post",
                        contentType: "application/json;charset=utf-8",
                        xhrFields: {
                            withCredentials: true
                        },
                        crossDomain: true,
                        success: function (data) {
                            console.log(data);
                            initAuthor(data.data);
                        },
                        error: function () {

                        }
                    });
                }

                function initArticle(data){
                    if(data.length===0){
                        $("#articles").append('暂无搜索结果')
                        return
                    }
                    let html=''
                    let keyword=$("#keyword").val();
                    for(let i=0;i<data.length;i++){
                        let h='<div class="col-md-12 post-box">' +
                            '<div class="post-header">' +
                            '<div class="post-title">' +
                            '<a href="${pageContext.request.contextPath}/article/detail?url='+data[i].url+'">'+replaceHeightLight(data[i].title,keyword)+'</a>' +
                            '</div>' +
                            '<div class="post-meta">' +
                            '<span class="post-meta-item-text">作者 </span>' +
                            '<a href="${pageContext.request.contextPath}/user/info?userId='+data[i].userId+'">' +
                            '<span itemprop="name">'+data[i].userName+' </span>' +
                            '</a>' +
                            '<span class="post-meta-item-text">发表于</span>' +
                            '<time title="创建于" datetime="'+data[i].createTime+'">' +
                            data[i].createTime +
                            '</time>' +
                            '<span class="post-meta-item-text"> 分类于</span>' +
                            '<a href="javscript:void(0)">' +
                            '<span itemprop="name"> '+data[i].typeName+' </span>' +
                            '</a>' +
                            '<span class="post-meta-item-text">浏览</span>' +
                            '<a href="javscript:void(0)">' +
                            '<span itemprop="name"> '+data[i].visit+'</span>' +
                            '</a>' +
                            '<span class="post-meta-item-text">评论</span>' +
                            '<a href="javscript:void(0)">' +
                            '<span itemprop="name"> '+data[i].common+' </span>' +
                            '</a>' +
                            '<span class="post-meta-item-text">公开级别</span>' +
                            '<a href="javscript:void(0)">' +
                            '<span itemprop="name"> '+data[i].pubLevelName+' </span>' +
                            '</a>' +
                            '</div>' +
                            '</div>' +
                            '<div class="post-body">';
                            if(data[i].description!==undefined&&data[i].description!==null){
                                h=h+data[i].description;
                            }
                            if(data[i].thumb!==undefined&&data[i].thumb!==null){
                                h=h+'<div class="entry-content clearfix">' +
                                    '<p><img src="'+data[i].thumb+'" alt="" height="300px" width="auto"></p>' +
                                    '</div>';
                            }
                            h=h+
                            '<div class="">' +
                            '<a class="read_btn" href="${pageContext.request.contextPath}/article/detail?url='+data[i].url+'">' +
                            '阅读全文 »' +
                            '</a>' +
                            '</div>' +
                            '</div>' +
                            '</div>';
                            html=html+h;
                    }
                    $("#articles").append(html)
                }

                function doSearch(){
                    if(keyword.val().trim().length===0){
                        layer.msg('关键字不能为空')
                        return;
                    }
                    search(keyword.val(),1)
                }

                function initPages(count, current) {
                    currentArticlePage = current;
                    laypage.render({
                        elem: 'pages',
                        curr: current,
                        count: count //数据总数，从服务端得到
                        , jump: function (obj, first) {
                            //obj包含了当前分页的所有参数，比如：
                            //首次不执行
                            if (!first) {
                                search($("#keyword").val(),obj.curr);
                            }
                        }
                    });

                }

                $('#keyword').bind('keydown',function(event){
                    if(event.keyCode === 13) {
                        doSearch()
                    }
                });

                function initAuthor(data){
                    if(data.length===0){
                        userResult.append('暂无搜索结果')
                        return
                    }
                    let html='';
                    let keyword=$("#keyword").val();
                    for(let i=0;i<data.length;i++){
                        let h='<li><div class="Box-row col-md-12">' +
                            '<div class="d-flex gutter-condensed ">' +
                            '<div class="col-md-3" style="max-width: 25%">';
                            if(data[i].face===undefined||data[i].face===null){
                                h=h+'<img class="avatar avatar-user" style="height: auto" src="${pageContext.request.contextPath}/static/img/face.png" width="100%" height="100%" alt="avatar image">';
                            }else {
                                h=h+'<img class="avatar avatar-user" style="height: auto" src="'+data[i].face+'" width="100%" height="100%" alt="avatar image">';
                            }
                            h=h+
                            '</div>' +
                            '<div class="col-md-9">' +
                            '<div class="f4 text-bold lh-condensed mb-2 f">' +
                            '<a href="${pageContext.request.contextPath}/user/info?userId='+data[i].id+'"><div style="color: #0366d6;font-size: 16px">' +
                            replaceHeightLight(data[i].nickname,keyword) +
                            '</div></a>' +
                            '<div class=" text-gray" style="overflow:hidden;text-overflow:ellipsis;white-space:nowrap;">'+data[i].sign+'</div>' +
                            '</div>' +
                            '' +
                            '<p  style="overflow:hidden;text-overflow:ellipsis;white-space:nowrap;">';
                            if(data[i].lastRelease!==undefined&&data[i].lastRelease!==null){
                                h=h+'<a class="" href="${pageContext.request.contextPath}/article/detail?url=">' +
                                    '<b class="red_text">new:</b> ' +data[i].lastRelease+
                                    '</a>' ;
                            }else {
                                h=h+'<a class="" href="javascript:void(0)">' +
                                    '<b class="red_text">new:</b> 暂无文章' +
                                    '</a>' ;
                            }
                            h=h+
                            '</p></div></div></div></li>'
                        html=html+h;
                    }
                    userResult.append(html);
                }
            </script>
        </div>
    </div>
</div>

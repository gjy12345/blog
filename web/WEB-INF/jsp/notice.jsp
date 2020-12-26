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
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/custom_new.css">
<div class="content-body">
    <div class="container">
        <div class="row">
            <main class="col-md-12">
                <article class="post post-1">
                    <header class="entry-header ">
                        <h1 class="entry-title" style="text-align: center">${notice.title}</h1>
                        <div class="post-meta" style="text-align: center">
                            <span class="post-meta-item-text">发布者</span>
                            <a href="javscript:void(0)">
                                <span itemprop="name"> 管理员</span>
                            </a>
                            <span class="post-meta-item-text">发表于</span>
                            <time title="创建于" datetime="${notice.createTime}">
                                ${notice.createTime}
                            </time>
                            <span class="post-meta-item-text">最后一次更新:</span>
                            <time title="创建于" datetime="${notice.updateTime}">
                                ${notice.updateTime==null?notice.createTime:notice.updateTime}
                            </time>

                        </div>
                    </header>
                    <div id="test-markdown-view" style="padding-left: 0px;margin-right: 0px;padding-top: 0px">
                        <!-- Server-side output Markdown text -->
                        <textarea style="display:none;">${notice.content}</textarea>
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
            </main>
        </div>
    </div>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/weadmin/lib/layui/layui.js" charset="utf-8"></script>

    <script type="text/javascript">
        document.title='${notice.title}';
    </script>
</div>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: gujianyang
  Date: 2020/12/11
  Time: 12:06 上午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap-card.css" />
<style type="text/css">
    .widget-category1 ul {
        padding: 0;
        margin: 0;
    }

    .widget-category1 ul li {
        list-style-type: none;
        position: relative;
        line-height: 170%;
        margin-bottom: 10px;
    }

    .widget-category1 ul li::before {
        position: absolute;
        left: -25px;
        top: 1px;
        font-size: 18px;
        color: #000;
    }
</style>

<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/custom_new.css">
<div class="content-body">
    <div class="container">
        <h3 style="text-align: center">
            排行榜
        </h3>
        <div class="row">
            <div class="col-md-4" style="margin-bottom: 20px">
                <div class="items text-center" style="border: lightslategray solid 1px;border-radius: 10px;padding-bottom: 40px;">
                    <div class="card " style="background: #5d6169;border-top-left-radius: 10px;border-top-right-radius: 10px;color: white">
                        <div class="card-body">阅读量排行榜</div>
                    </div>
                    <div style="padding-left: 20px;padding-right: 20px;text-align: left;min-height: 400px" class="widget widget-category1">
                        <ul>
                            <c:forEach items="${visitRanking}" var="blog" varStatus="state">
                                <li>
                                    <span >${state.count}. </span>
                                    <a href="${pageContext.request.contextPath}/article/detail?url=${blog.url}">${blog.title}</a>
                                    <div style="font-size: 6px;color: grey;float: right;">作者:${blog.userName}</div>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="col-md-4" style="margin-bottom: 20px">
                <div class="items text-center" style="border: lightslategray solid 1px;border-radius: 10px;padding-bottom: 40px;">
                    <div class="card " style="background: #5d6169;border-top-left-radius: 10px;border-top-right-radius: 10px;color: white">
                        <div class="card-body">创作排行榜</div>
                    </div>
                    <div style="padding-left: 20px;padding-right: 20px;text-align: left;min-height: 400px" class="widget widget-category1">
                        <ul>
                            <c:forEach items="${czRanking}" var="user" varStatus="state">
                                <li>
                                    <span >${state.count}. </span>
                                    <a href="${pageContext.request.contextPath}/user/info?userId=${user.id}">${user.nickname}</a>
                                    <div style="font-size: 6px;color: grey;float: right;">博客数量:${user.blogCount}</div>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="col-md-4" style="margin-bottom: 20px">
                <div class="items text-center" style="border: lightslategray solid 1px;border-radius: 10px;padding-bottom: 40px;">
                    <div class="card " style="background: #5d6169;border-top-left-radius: 10px;border-top-right-radius: 10px;color: white">
                        <div class="card-body">评论排行榜</div>
                    </div>
                    <div style="padding-left: 20px;padding-right: 20px;text-align: left;min-height: 400px" class="widget widget-category1">
                        <ul>
                            <c:forEach items="${commentRanking}" var="blog" varStatus="state">
                                <li>
                                    <span >${state.count}. </span>
                                    <a href="${pageContext.request.contextPath}/article/detail?url=${blog.url}">${blog.title}</a>
                                    <div style="font-size: 6px;color: grey;float: right;">评论数:${blog.common}</div>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


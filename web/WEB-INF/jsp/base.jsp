<%--
Created by IntelliJ IDEA.
User: gujianyang
Date: 2020/10/12
Time: 10:41 上午
To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <title>${blogConfig.blogName}</title>

    <!-- meta -->
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- css -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/blog/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/blog/ionicons.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/blog/pace.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/blog/custom.css">

    <!-- js -->
    <script src="${pageContext.request.contextPath}/static/blog/jquery-2.1.3.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/blog/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/blog/pace.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/blog/modernizr.custom.js"></script>
</head>

<body>
<div class="container">
    <header id="site-header">
        <div class="row">
            <div class="col-md-4 col-sm-5 col-xs-8">
                <div class="logo">
                    <h1><a href="index.html"><b>${blogConfig.blogName}</b></a></h1>
                </div>
            </div><!-- col-md-4 -->
            <div class="col-md-8 col-sm-7 col-xs-4">
                <nav class="main-nav" role="navigation">
                    <div class="navbar-header">
                        <button type="button" id="trigger-overlay" class="navbar-toggle">
                            <span class="ion-navicon"></span>
                        </button>
                    </div>

                    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                        <ul class="nav navbar-nav navbar-right">
                            <li class="cl-effect-11"><a href="${pageContext.request.contextPath}/">探索</a></li>
                            <li class="cl-effect-11"><a href="${pageContext.request.contextPath}/user/follow">关注</a></li>
                            <li class="cl-effect-11"><a href="about.html">排行</a></li>
                            <li class="cl-effect-11"><a href="${pageContext.request.contextPath}/user/">我的</a></li>
                        </ul>
                    </div><!-- /.navbar-collapse -->
                </nav>
                <div id="header-search-box">
                    <a id="search-menu" href="#"><span id="search-icon" class="ion-ios-search-strong"></span></a>
                    <div id="search-form" class="search-form">
                        <form role="search" method="get" id="searchform" action="#">
                            <input type="search" placeholder="搜索" required>
                            <button type="submit"><span class="ion-ios-search-strong"></span></button>
                        </form>
                    </div>
                </div>
            </div><!-- col-md-8 -->
        </div>
    </header>
</div>
<jsp:include page="${child_jsp}"></jsp:include>
<footer id="site-footer">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <p class="copyright">&copy ${blogConfig.year} ${blogConfig.blogName}
                </p>
            </div>
        </div>
    </div>
</footer>

<!-- Mobile Menu -->
<div class="overlay overlay-hugeinc">
    <button type="button" class="overlay-close"><span class="ion-ios-close-empty"></span></button>
    <nav>
        <ul>
            <li><a href="#">探索</a></li>
            <li><a href="${pageContext.request.contextPath}/user/follow">关注</a></li>
            <li><a href="about.html">排行</a></li>
            <li><a href="${pageContext.request.contextPath}/user/">我的</a></li>
        </ul>
    </nav>
</div>

<script src="${pageContext.request.contextPath}/static/blog/script.js"></script>

</body>
</html>
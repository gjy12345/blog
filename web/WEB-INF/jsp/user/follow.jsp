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

<div class="content-body">
    <div class="container">
        <div class="row">
            <main class="col-md-8">
                <article class="post post-2">
                    <header class="entry-header">
                        <h3 class="entry-title">
                            <a href="single.html">Adaptive Vs. Responsive Layouts And Optimal Text Readability</a>
                        </h3>
                        <div class="entry-content clearfix">
                            <p>Responsive 前端设计 offers us a way forward, finally allowing us to design for the ebb and flow of things. There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don’t look even slightly.</p>
                        </div>
                        <div class="entry-meta">
								<span class="post-face">
									<img src="${pageContext.request.contextPath}/static/img/face.png" alt="">
								</span>
                            <span class="post-author"><a href="#">Albert Einstein</a></span>

                            <span class="post-date"><a href="#"><time class="entry-date" datetime="2012-11-09T23:15:57+00:00">February 2, 2013</time></a></span>

                            <span class="post-category"><a href="#">前端设计</a></span>

                            <span class="comments-link"><a href="#">4 评论</a></span>

                            <span class="comments-link"><a href="#">4 赞同</a></span>
                        </div>
                    </header>
                </article>
                <article class="post post-2">
                    <header class="entry-header">
                        <h3 class="entry-title">
                            <a href="single.html">Adaptive Vs. Responsive Layouts And Optimal Text Readability</a>
                        </h3>
                        <div class="entry-content clearfix">
                            <p>Responsive 前端设计 offers us a way forward, finally allowing us to design for the ebb and flow of things. There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don’t look even slightly.</p>
                        </div>
                        <div class="entry-meta">
								<span class="post-face">
									<img src="${pageContext.request.contextPath}/static/img/face.png" alt="">
								</span>
                            <span class="post-author"><a href="#">Albert Einstein</a></span>

                            <span class="post-date"><a href="#"><time class="entry-date" datetime="2012-11-09T23:15:57+00:00">February 2, 2013</time></a></span>

                            <span class="post-category"><a href="#">前端设计</a></span>

                            <span class="comments-link"><a href="#">4 评论</a></span>

                            <span class="comments-link"><a href="#">4 赞同</a></span>
                        </div>
                    </header>
                </article>
                <article class="post post-2">
                    <header class="entry-header">
                        <h3 class="entry-title">
                            <a href="single.html">Adaptive Vs. Responsive Layouts And Optimal Text Readability</a>
                        </h3>
                        <div class="entry-content clearfix">
                            <p>Responsive 前端设计 offers us a way forward, finally allowing us to design for the ebb and flow of things. There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don’t look even slightly.</p>
                        </div>
                        <div class="entry-meta">
								<span class="post-face">
									<img src="${pageContext.request.contextPath}/static/img/face.png" alt="">
								</span>
                            <span class="post-author"><a href="#">Albert Einstein</a></span>

                            <span class="post-date"><a href="#"><time class="entry-date" datetime="2012-11-09T23:15:57+00:00">February 2, 2013</time></a></span>

                            <span class="post-category"><a href="#">前端设计</a></span>

                            <span class="comments-link"><a href="#">4 评论</a></span>

                            <span class="comments-link"><a href="#">4 赞同</a></span>
                        </div>
                    </header>
                </article>
                <article class="post post-2">
                    <header class="entry-header">
                        <h3 class="entry-title">
                            <a href="single.html">Adaptive Vs. Responsive Layouts And Optimal Text Readability</a>
                        </h3>
                        <div class="entry-content clearfix">
                            <p>Responsive 前端设计 offers us a way forward, finally allowing us to design for the ebb and flow of things. There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don’t look even slightly.</p>
                        </div>
                        <div class="entry-meta">
								<span class="post-face">
									<img src="${pageContext.request.contextPath}/static/img/face.png" alt="">
								</span>
                            <span class="post-author"><a href="#">Albert Einstein</a></span>

                            <span class="post-date"><a href="#"><time class="entry-date" datetime="2012-11-09T23:15:57+00:00">February 2, 2013</time></a></span>

                            <span class="post-category"><a href="#">前端设计</a></span>

                            <span class="comments-link"><a href="#">4 评论</a></span>

                            <span class="comments-link"><a href="#">4 赞同</a></span>
                        </div>
                    </header>
                </article>
                <article class="post post-2">
                    <header class="entry-header">
                        <h3 class="entry-title">
                            <a href="single.html">Adaptive Vs. Responsive Layouts And Optimal Text Readability</a>
                        </h3>
                        <div class="entry-content clearfix">
                            <p>Responsive 前端设计 offers us a way forward, finally allowing us to design for the ebb and flow of things. There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don’t look even slightly.</p>
                        </div>
                        <div class="entry-meta">
								<span class="post-face">
									<img src="${pageContext.request.contextPath}/static/img/face.png" alt="">
								</span>
                            <span class="post-author"><a href="#">Albert Einstein</a></span>

                            <span class="post-date"><a href="#"><time class="entry-date" datetime="2012-11-09T23:15:57+00:00">February 2, 2013</time></a></span>

                            <span class="post-category"><a href="#">前端设计</a></span>

                            <span class="comments-link"><a href="#">4 评论</a></span>

                            <span class="comments-link"><a href="#">4 赞同</a></span>
                        </div>
                    </header>
                </article>
                <!-- 简单分页效果
                <div class="pagination-simple">
                    <a href="#">上一页</a>
                    <span class="current">第 6 页 / 共 11 页</span>
                    <a href="#">下一页</a>
                </div>
                -->
                <div class="pagination">
                    <ul>
                        <li><a href="">1</a></li>
                        <li><a href="">...</a></li>
                        <li><a href="">4</a></li>
                        <li><a href="">5</a></li>
                        <li class="current"><a href="">6</a></li>
                        <li><a href="">7</a></li>
                        <li><a href="">8</a></li>
                        <li><a href="">...</a></li>
                        <li><a href="">11</a></li>
                    </ul>
                </div>
            </main>
            <aside class="col-md-4">
                <div class="widget ">
                    <h3 class="widget-title">关注的人:</h3>
                    <ul style="list-style: none;padding-left: 0px">
                        <li>
                            <article class="Box-row col-md-12">
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
                            </article>
                        </li>
                    </ul>
                </div>
                <div class="widget text-center">
                    <a href="">加载更多..</a>
                </div>
            </aside>
        </div>
    </div>
</div>
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
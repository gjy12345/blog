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
		<title>Code &amp; Blogs</title>

		<!-- meta -->
		<meta charset="UTF-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1">

	    <!-- css -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/ionicons.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/pace.css">
	    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/custom.css">

	    <!-- js -->
	    <script src="${pageContext.request.contextPath}/static/js/jquery-2.1.3.min.js"></script>
	    <script src="${pageContext.request.contextPath}/static/js/bootstrap.min.js"></script>
	    <script src="${pageContext.request.contextPath}/static/js/pace.min.js"></script>
	    <script src="${pageContext.request.contextPath}/static/js/modernizr.custom.js"></script>
	</head>

	<body>
		<div class="container">	
			<header id="site-header">
				<div class="row">
					<div class="col-md-4 col-sm-5 col-xs-8">
						<div class="logo">
							<h1><a href="index.jsp"><b>Code</b> &amp; Blogs</a></h1>
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
    								<li class="cl-effect-11"><a href="blog-index.html" data-hover="Home">Home</a></li>
    								<li class="cl-effect-11"><a href="blog-home.html" data-hover="Blog">Blog</a></li>
    								<li class="cl-effect-11"><a href="blog-animation.html" data-hover="Animation">Animation</a></li>
    								<li class="cl-effect-11"><a href="blog-about.html" data-hover="About">About</a></li>
    								<li class="cl-effect-11"><a href="blog-contact.html" data-hover="Contact">Contact</a></li>
  								</ul>
							</div><!-- /.navbar-collapse -->
						</nav>
						<div id="header-search-box">
							<a id="search-menu" href="#"><span id="search-icon" class="ion-ios-search-strong"></span></a>
							<div id="search-form" class="search-form">
								<form role="search" method="get" id="searchform" action="#">
									<input type="search" placeholder="Search" required>
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
					<main class="col-md-12">
						<h1>➤ 【 网站公告 ※】</h1>

						<article class="post post-1">
							<header class="entry-header">
								<h1 class="entry-title">
									<a href="blog-detail.html">Adaptive Vs. Responsive Layouts And Optimal Text Readability</a>
								</h1>
								<div class="entry-meta">
									<span class="post-category"><a href="#">Web Design</a></span>
			
									<span class="post-date"><a href="#"><time class="entry-date" datetime="2012-11-09T23:15:57+00:00">February 2, 2013</time></a></span>
			
									<span class="post-author"><a href="#">Albert Einstein</a></span>
			
									<span class="comments-link"><a href="">4 Comments</a></span>
								</div>
							</header>
							<div class="entry-content clearfix">
								<p>Responsive web design offers us a way forward, finally allowing us to design for the ebb and flow of things. There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don’t look even slightly.</p>
								<div class="read-more cl-effect-14">
									<a href="#" class="more-link">查看公告 <span class="meta-nav">→</span></a>
								</div>
							</div>
						</article>
						<h1>➤ 【 推荐文章 ⇩】</h1>

						<article class="post post-2">
							<header class="entry-header">
								<h1 class="entry-title">
									<a href="single.html">Adaptive Vs. Responsive Layouts And Optimal Text Readability</a>
								</h1>
								<div class="entry-meta">
									<span class="post-category"><a href="#">Web Design</a></span>
			
									<span class="post-date"><a href="#"><time class="entry-date" datetime="2012-11-09T23:15:57+00:00">February 2, 2013</time></a></span>
			
									<span class="post-author"><a href="#">Albert Einstein</a></span>
			
									<span class="comments-link"><a href="#">4 Comments</a></span>
								</div>
							</header>
							<div class="entry-content clearfix">
								<p>Responsive web design offers us a way forward, finally allowing us to design for the ebb and flow of things. There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don’t look even slightly.</p>
								<div class="read-more cl-effect-14">
									<a href="#" class="more-link">查看文章 <span class="meta-nav">→</span></a>
								</div>
							</div>
						</article>

						<article class="post post-3">
							<header class="entry-header">
								<h1 class="entry-title">
									<a href="single.html">Adaptive Vs. Responsive Layouts And Optimal Text Readability</a>
								</h1>
								<div class="entry-meta">
									<span class="post-category"><a href="#" rel="category tag">Web Design</a></span>
			
									<span class="post-date"><a href="#"><time class="entry-date" datetime="2012-11-09T23:15:57+00:00">February 2, 2013</time></a></span>
			
									<span class="post-author"><a href="#">Albert Einstein</a></span>
			
									<span class="comments-link"><a href="#">4 Comments</a></span>
								</div>
							</header>
							<div class="entry-content clearfix">
								<p>Responsive web design offers us a way forward, finally allowing us to design for the ebb and flow of things. There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don’t look even slightly.</p>
								<div class="read-more cl-effect-14">
									<a href="#" class="more-link">查看文章 <span class="meta-nav">→</span></a>
								</div>
							</div>
						</article>

						<article class="post post-4">
							<header class="entry-header">
								<h1 class="entry-title">
									<a href="single.html">Adaptive Vs. Responsive Layouts And Optimal Text Readability</a>
								</h1>
								<div class="entry-meta">
									<span class="post-category"><a href="#" rel="category tag">Web Design</a></span>
			
									<span class="post-date"><a href="#"><time class="entry-date" datetime="2012-11-09T23:15:57+00:00">February 2, 2013</time></a></span>
			
									<span class="post-author"><a href="#">Albert Einstein</a></span>
			
									<span class="comments-link"><a href="#">4 Comments</a></span>
								</div>
							</header>
							<div class="entry-content clearfix">
								<p>Responsive web design offers us a way forward, finally allowing us to design for the ebb and flow of things. There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don’t look even slightly.</p>
								<div class="read-more cl-effect-14">
									<a href="#" class="more-link">查看文章 <span class="meta-nav">→</span></a>
								</div>
							</div>
						</article>
					</main>
				</div>
			</div>
		</div>
		<footer id="site-footer">
			<div class="container">
				<div class="row">
					<div class="col-md-12">
						<p class="copyright"><a href="#">【后台登陆】</a>&nbsp; &copy; 2014 ThemeWagon.com</p>
					</div>
				</div>
			</div>
		</footer>

		<!-- Mobile Menu -->
		<div class="overlay overlay-hugeinc">
			<button type="button" class="overlay-close"><span class="ion-ios-close-empty"></span></button>
			<nav>
				<ul>
					<li><a href="index.jsp">Home</a></li>
					<li><a href="full-width.html">Blog</a></li>
					<li><a href="about.html">About</a></li>
					<li><a href="contact.html">Contact</a></li>
				</ul>
			</nav>
		</div>

		<script src="${pageContext.request.contextPath}/static/js/script.js"></script>

	</body>
</html>

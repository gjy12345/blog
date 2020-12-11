<%--
Created by IntelliJ IDEA.
User: gujianyang
Date: 2020/10/12
Time: 10:41 上午
To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="content-body">
	<div class="container">
		<div class="row">
			<main class="col-md-12">
				<h4>➤ 【 最近更新 ※】</h4>
				<c:forEach items="${recentBlogs}" var="blog" varStatus="s">
					<article class="post post-1 ">
						<header class="entry-header text-left" >
							<div class="entry-title">
								<a href="blog-detail.html">
										${blog.title}
								</a>
							</div>
							<div class="entry-meta">
							<span class="post-face">
<%--								<c:if test="">--%>
<%--									--%>
<%--								</c:if>--%>
<%--								<c:if test="">--%>
<%--									<img src="${pageContext.request.contextPath}/static/img/face.png" alt="">--%>
<%--								</c:if>--%>
							</span>
								<span class="post-category"><a href="#">${blog.type}</a></span>

								<span class="post-date"><a href="#"><time class="entry-date" datetime="2012-11-09T23:15:57+00:00">February 2, 2013</time></a></span>

								<span class="post-author"><a href="#">Albert Einstein</a></span>

								<span class="comments-link"><a href="">4 评论</a></span>
								<span class="comments-link"><a href="#">4 赞同</a></span>
							</div>
						</header>
					</article>
				</c:forEach>
				<h4>➤ 【 推荐文章 ⇩】</h4>

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
				<h4>➤ 【 热门文章 @】</h4>
			</main>
		</div>
	</div>
</div>

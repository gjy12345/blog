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
<div class="content-body">
	<div class="container">
		<div class="row">
			<main class="col-md-12">
				<c:forEach items="${recent_blog}" var="blog" varStatus="s">
					<div class="col-md-12 post-box">
						<div class="post-header">
							<div class="post-title">
								<a href="${pageContext.request.contextPath}/article/detail?url=${blog.url}">${blog.title}</a>
							</div>
							<div class="post-meta">
								<span class="post-meta-item-text">作者</span>
								<a href="${pageContext.request.contextPath}/user/info?userId=${blog.userId}">
									<span itemprop="name"> ${blog.userName}</span>
								</a>
								<span class="post-meta-item-text">发表于</span>
								<time title="创建于" datetime="${blog.createTime}">
										${blog.createTime}
								</time>
								<span class="post-meta-item-text">分类于</span>
								<a href="javscript:void(0)">
									<span itemprop="name"> ${blog.typeName}</span>
								</a>
								<span class="post-meta-item-text">浏览</span>
								<a href="javscript:void(0)">
									<span itemprop="name"> ${blog.visit}</span>
								</a>
								<span class="post-meta-item-text">评论</span>
								<a href="javscript:void(0)">
									<span itemprop="name"> ${blog.common}</span>
								</a>
								<span class="post-meta-item-text">公开级别</span>
								<a href="javscript:void(0)">
									<span itemprop="name"> ${blog.pubLevelName}</span>
								</a>
							</div>
						</div>
						<div class="post-body">
							<c:if test="${blog.description!=null}">
								${blog.description}
							</c:if>
							<c:if test="${blog.thumb!=null}">
								<div class="entry-content clearfix">
									<p><img src="${blog.thumb}" alt="" height="300px" width="auto"></p>
								</div>
							</c:if>
							<div class="">
								<a class="read_btn" href="${pageContext.request.contextPath}/article/detail?url=${blog.url}">
									阅读全文 »
								</a>
							</div>
						</div>
					</div>
				</c:forEach>
			</main>
		</div>
	</div>
</div>

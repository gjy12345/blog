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
		<meta charset="UTF-8">
		<title>欢迎页面</title>
		<meta name="renderer" content="webkit">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/static/weadmin/static/css/font.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/static/weadmin/static/css/weadmin.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/static/blog/bootstrap.min.css">


	</head>

	<body>
		<div class="weadmin-body">
			<blockquote class="layui-elem-quote">
				${nowTime},亲爱的${USER_SESSION_TAG.nickname}.</blockquote>
			<div class="layui-fluid" style="overflow: hidden;">
				<div class="layui-row layui-col-space15">
					<div class="layui-col-md8">
						<div class="layui-card">
							<div class="layui-card-header">仪表盘</div>
							<div class="layui-card-body">
								<div class="layui-carousel weadmin-shortcut" lay-filter="shortcut" lay-indicator="inside" lay-arrow="none" style="width: 100%; height: 280px;">
									<div carousel-item="">
										<ul class="layui-row layui-col-space10 layui-this">
											<li class="layui-col-xs3">
												<a href="http://www.layui.com/doc/" target="_blank">
													<i class="layui-icon">${blogCount}</i>
													<cite>博客数量</cite>
												</a>
											</li>
											<li class="layui-col-xs3">
												<a href="http://www.layui.com/admin/" target="_blank">
													<i class="layui-icon">22
														<i class="layui-icon-top "
														   style="font-size: 16px;color: red;">
															+1
														</i>
													</i>
													<cite>评论</cite>
												</a>
											</li>
											<li class="layui-col-xs3">
												<a href="http://layim.layui.com/" target="_blank">
													<i class="layui-icon">290
														<i class="layui-icon-top "
														   style="font-size: 16px;color: red;">
															+1
														</i>
													</i>
													<cite>阅读量</cite>
												</a>
											</li>
											<li class="layui-col-xs3">
												<a href="http://fly.layui.com/case/u/777504" target="_blank">
													<i class="layui-icon">22
														<i class="layui-icon-top "
														   style="font-size: 16px;color: red;">
															+1
														</i>
													</i>
													<cite>点赞</cite>
												</a>
											</li>
											

										</ul>

									</div>
								</div>

							</div>
						</div>
					</div>
					<div class="layui-col-md4">
						<div class="layui-card">
							<div class="layui-card-header">
								关于我
								<i class="layui-icon" style="color: #FF5722;">&#xe756;</i>
							</div>
							<div class=" layui-text weadmin-text">
								<article class="Box-row col-md-12 layui-card-body" style="margin-top: 10px">
									<div class="d-flex gutter-condensed ">
										<div class="col-lg-3 " style="max-width: 80px">
											<c:choose>
												<c:when test="${USER_SESSION_TAG.face!=null}"><img class="avatar avatar-user" style="height: auto" src="${USER_SESSION_TAG.face}" width="100%" height="100%" alt="avatar image"></c:when>
												<c:when test="${USER_SESSION_TAG.face==null}"><img class="avatar avatar-user" style="height: auto" src="${pageContext.request.contextPath}/static/img/face.png" width="100%" height="100%" alt="avatar image"></c:when>
											</c:choose>
										</div>
										<div class="col-lg-9">
											<div class="f4 text-bold lh-condensed mb-2" style="min-height: 100px">
												<div style="color: #0366d6;font-size: 16px">
													${USER_SESSION_TAG.nickname}
												</div>
												<span class="d-block text-normal text-gray">
													${USER_SESSION_TAG.sign}
												</span>
											</div>
										</div>
									</div>
									<div class="col-md-12">
										<div class="col-md-3">
											等级:LV${USER_SESSION_TAG.level}
										</div>
										<div class="col-md-3">
											粉丝: <a href="">21</a>
										</div>
										<div class="col-md-3">
											关注: <a href="">12</a>
										</div>
										<div class="col-md-3">
											访问量: <a href="">12</a>
										</div>
									</div>
								</article>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="layui-col-lg12 layui-collapse" style="border: none;">
				<div class="layui-col-lg6 layui-col-md12">
						
					
					<!--统计信息展示-->
					<fieldset class="layui-elem-field" style="padding: 5px;">
						<!--WeAdmin公告-->
						<div class="layui-card">
							<div class="layui-card-header layui-elem-quote" style="min-height: 50px">公告 <a href="#">更多</a></div>
							<div class="layui-card-body">
								<div class="layui-carousel weadmin-notice" lay-filter="notice" lay-indicator="inside" lay-arrow="none" style="width: 100%; height: 280px;">
									<div carousel-item="">
										<div class="">
											<a href="https://gitee.com/lovetime/WeAdmin" target="_blank" class="layui-bg-red">新的风暴已经出现</a>
										</div>
										<div class="">
											<a href="http://www.layui.com/admin/" target="_blank" class="layui-bg-blue">怎么能够停滞不前</a>
										</div>

									</div>
									<div class="layui-carousel-ind">
										<ul>
											<li class="layui-this"></li>
											<li></li>
										</ul>
									</div>
									<!--<button class="layui-icon layui-carousel-arrow" lay-type="sub"></button>
									<button class="layui-icon layui-carousel-arrow" lay-type="add"></button>-->
								</div>

							</div>
						</div>
						<!--<legend>信息统计</legend>-->
						<blockquote class="layui-elem-quote font16">最新博客</blockquote>
						<div class="col-md-12">
							<div class="col-md-12">
								<c:forEach items="${recentBlogs}" var="blog" varStatus="s">
									<div class="entry-content clearfix table-bordered" style="padding-bottom: 10px;padding-top: 10px;">
										<div class="entry-title col-md-10 font16" >
											<a href="${pageContext.request.contextPath}/article/detail?url=${blog.url}" target="_blank">
													<c:choose>
														<c:when test="${blog.publicityLevel==7}">
															[全部可见]
														</c:when>
														<c:when test="${blog.publicityLevel==5}">
															[仅我可见]
														</c:when>
														<c:when test="${blog.publicityLevel==6}">
															[需要密码]
														</c:when>
													</c:choose>
												[博客] ${blog.title}
											</a>
										</div>
										<div class="col-md-2">
												${blog.createTime}
										</div>
									</div>
								</c:forEach>
							</div>
						</div>
					</fieldset>
					<blockquote class="layui-elem-quote font16">最新评论</blockquote>
					<div class="col-md-12" style="min-height: 300px">
						<div class="col-md-12">
							<div class="entry-content clearfix table-bordered" style="padding-bottom: 10px;padding-top: 10px;">
								<div class="entry-title col-md-10 font16" >
									<a href="single.html"></a>[评论] <a href="">Linus</a>: 你这个是怎么是实现的?&emsp;
									<a href="">[查看]</a>
								</div>
								<div class="col-md-2">
									2020-10-1
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="layui-col-lg6 layui-col-md12">
					<fieldset class="layui-elem-field we-changelog" style="padding: 5px;">
						<!--更新日志-->
						<blockquote class="layui-elem-quote font16">操作日志</blockquote>
						<ul class="layui-timeline" style="max-height: 780px;overflow-y: auto;">
							<c:forEach items="${operations}" var="operation" varStatus="s">
								<li class="layui-timeline-item">
									<c:choose>
										<c:when test="${operation.operationType==1}">
											<i class="layui-icon layui-timeline-axis">&#xe63f;</i>
										</c:when>
										<c:when test="${operation.operationType==2}">
											<i class="layui-icon layui-timeline-axis">&#xe609;</i>
										</c:when>
										<c:when test="${operation.operationType==4}">
											<i class="layui-icon layui-timeline-axis">&#xe702;</i>
										</c:when>
										<c:when test="${operation.operationType==3}">
											<i class="layui-icon layui-timeline-axis">&#xe6b2;</i>
										</c:when>
									</c:choose>
									<div class="layui-timeline-content layui-text">
										<div class="layui-timeline-title">
											<h3>${operation.operation}</h3>
											<span class="layui-badge-rim">${operation.operationTime}</span>
										</div>
										<ul>
											<li>ip: ${operation.ip}</li>
											<li>客户端: ${operation.client}</li>
										</ul>
									</div>
								</li>

							</c:forEach>
						</ul>
					</fieldset>
				</div>
			</div>
		</div>
	</body>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/weadmin/lib/layui/layui.js" charset="utf-8"></script>
	<script type="text/javascript">
		layui.extend({
			admin: '${pageContext.request.contextPath}/static/weadmin/static/js/admin',
		});
		layui.use(['jquery', 'element','util', 'admin', 'carousel'], function() {
			var element = layui.element,
				$ = layui.jquery,
				carousel = layui.carousel,
				util = layui.util,
				admin = layui.admin;
			//建造实例
			carousel.render({
				elem: '.weadmin-shortcut'
				,width: '100%' //设置容器宽度				
				,arrow: 'none' //始终显示箭头	
				,trigger: 'hover'
				,autoplay:false
			});
			
			carousel.render({
				elem: '.weadmin-notice'
				,width: '100%' //设置容器宽度				
				,arrow: 'none' //始终显示箭头	
				,trigger: 'hover'
				,autoplay:true
			});
			
			// $(function(){
			// 	setTimeAgo(2018,0,1,13,14,0,'#firstTime');
			// 	setTimeAgo(2019,5,20,16,0,0,'#lastTime');
			// });
			// function setTimeAgo(y, M, d, H, m, s,id){
			//     var str = util.timeAgo(new Date(y, M||0, d||1, H||0, m||0, s||0));
			//     $(id).html(str);
			//     console.log(str);
			//  };
		});
	</script>

</html>
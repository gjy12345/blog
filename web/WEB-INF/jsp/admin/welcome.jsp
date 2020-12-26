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
				${nowTime},亲爱的${ADMIN_SESSION_TAG.nickname}.</blockquote>
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
												<a href="javascript:void(0)">
													<i class="layui-icon">${userCount}</i>
													<cite>总用户数</cite>
												</a>
											</li>
											<li class="layui-col-xs3">
												<a href="javascript:void(0)">
													<i class="layui-icon">
														${blogCount}
													</i>
													<cite>总博客数</cite>
												</a>
											</li>
											<li class="layui-col-xs3">
												<a href="javascript:void(0)">
													<i class="layui-icon">${commentCount}
													</i>
													<cite>总评论数</cite>
												</a>
											</li>
											<li class="layui-col-xs3">
												<a href="javascript:void(0)">
													<i class="layui-icon">${online}
													</i>
													<cite>在线用户</cite>
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
												<c:when test="${ADMIN_SESSION_TAG.face!=null}"><img class="avatar avatar-user" style="height: auto" src="${USER_SESSION_TAG.face}" width="100%" height="100%" alt="avatar image"></c:when>
												<c:when test="${ADMIN_SESSION_TAG.face==null}"><img class="avatar avatar-user" style="height: auto" src="${pageContext.request.contextPath}/static/img/face.png" width="100%" height="100%" alt="avatar image"></c:when>
											</c:choose>
										</div>
										<div class="col-lg-9">
											<div class="f4 text-bold lh-condensed mb-2" style="min-height: 100px">
												<div style="color: #0366d6;font-size: 16px">
													${ADMIN_SESSION_TAG.nickname}
												</div>
												<span class="d-block text-normal text-gray">
													${ADMIN_SESSION_TAG.sign}
												</span>
											</div>
										</div>
									</div>
									<div class="col-md-12">
										<div class="col-md-4">
											登录ip: <a href="javascript:void(0)">${ADMIN_SESSION_TAG.lastLoginIp}</a>
										</div>
										<div class="col-md-8">
											登录时间: <a href="javascript:void(0)">${ADMIN_SESSION_TAG.lastLoginTime}</a>
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
						<!--<legend>信息统计</legend>-->
						<blockquote class="layui-elem-quote font16">系统信息</blockquote>
						<div class="col-md-12">
							<c:forEach items="${systemInfos}" var="systemInfo">
								<div class="entry-content clearfix table-bordered" style="padding-bottom: 10px;padding-top: 10px;">
									<div class="entry-title col-md-6 font16" >
											${systemInfo.key}:
									</div>
									<div class="col-md-6">
											${systemInfo.value}
									</div>
								</div>
							</c:forEach>
						</div>
					</fieldset>

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
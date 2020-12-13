<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>修改分类</title>
		<meta name="renderer" content="webkit">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/static/weadmin/static/css/font.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/static/weadmin/static/css/weadmin.css">
	</head>

	<body>
		<div class="weadmin-body">

			<form id="form1" class="layui-form">
				<input type="text" name="id" value="${category.id}" hidden>
				<div class="layui-form-item">
					<label class="layui-form-label">分类名称</label>
					<div class="layui-input-block">
						<input type="text" value="${category.name}" name="name" lay-verify="required" jq-error="请输入分类名称" placeholder="请输入分类名称" autocomplete="off" class="layui-input ">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">描述</label>
					<div class="layui-input-block">
						<input type="text" value="${category.description}" name="description" lay-verify="required" jq-error="请输入描述" placeholder="请输入描述" autocomplete="off" class="layui-input ">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">状态</label>
					<div class="layui-input-inline">
						<input type="radio" name="lock" title="启用" value="0" ${category.lock==0?'checked':''}/>
						<input type="radio" name="lock" title="禁用" value="1" ${category.lock==1?'checked':''}/>
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-input-block">
						<button class="layui-btn" lay-submit="" lay-filter="add">立即提交</button>
						<button type="reset" class="layui-btn layui-btn-primary">重置</button>
					</div>
				</div>
				<input type="hidden" name="level" value="0" />
			</form>
		</div>
		<script src="${pageContext.request.contextPath}/static/weadmin/lib/layui/layui.js" charset="utf-8"></script>
		<script type="text/javascript">
			layui.extend({
				admin: '${pageContext.request.contextPath}/static/weadmin/static/js/admin'
			});
			layui.use(['admin','jquery','form', 'layer'], function() {
				let admin = layui.admin,
					$ = layui.jquery,
					form = layui.form,
					layer = layui.layer;

				//监听提交
				form.on('submit(add)', function(data) {
					console.log(JSON.stringify(data.field));
					$.ajax({
						url: "${pageContext.request.contextPath}/user/manage/article/type/edit",
						type: 'post',
						async: true,
						contentType: 'application/json',
						processData: false, //默认为true，默认情况下，发送的数据将被转换为对象，设为false不希望进行转换
						data: JSON.stringify(data.field),
						success: function(data) {
							if(data.result==1){
								layer.alert("修改成功", {
									icon: 6
								}, function() {
									// 获得frame索引
									var index = parent.layer.getFrameIndex(window.name);
									//关闭当前frame
									parent.layer.close(index);
								});
							}else {
								layer.msg(data.msg);
							}
						},
						error: function(data) {
							layer.msg('发生了错误!');
						}
					})
					//发异步，把数据提交给php

					return false;
				});
			});


		</script>
	</body>

</html>
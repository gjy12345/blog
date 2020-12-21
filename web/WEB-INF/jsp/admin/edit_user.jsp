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
    <title>修改用户</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/weadmin/static/css/font.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/static/weadmin/static/css/weadmin.css">
      <script src="${pageContext.request.contextPath}/static/blog/jquery-2.1.3.min.js"></script>
  </head>
  
  <body>
    <div class="weadmin-body">
        <div class="layui-form">
          <div class="layui-form-item">
              <label for="username" class="layui-form-label">
                  登录名
              </label>
              <div class="layui-input-inline">
                  <input type="text" id="username" name="username" value="${user.username}"
                  autocomplete="off" class="layui-input">
              </div>
          </div>
            <div class="layui-form-item">
                <label for="username" class="layui-form-label">
                    昵称
                </label>
                <div class="layui-input-inline">
                    <input type="text" id="nickname" name="nickname" value="${user.nickname}"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
          <div class="layui-form-item">
              <label for="sign" class="layui-form-label">
                  个性签名
              </label>
              <div class="layui-input-inline">
                  <textarea type="text" id="sign" name="sign"
                  autocomplete="off" class="layui-input layui-textarea">${user.sign}</textarea>
              </div>
          </div>
            <div class="layui-form-item">
                <label for="sign" class="layui-form-label">
                    状态
                </label>
                <div class="layui-input-inline">
                    <select id="lock">
                        <option value="0" ${user.lock==0?'selected':''} >不锁定</option>
                        <option value="1" ${user.lock==1?'selected':''}>锁定</option>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label for="sign" class="layui-form-label">
                    性别
                </label>
                <div class="layui-input-inline">
                    <select id="sex">
                        <option value="0" ${user.sex==0?'selected':''}>男</option>
                        <option value="1" ${user.sex==1?'selected':''}>女</option>
                    </select>
                </div>
            </div>
          <div class="layui-form-item">
              <label for="password" class="layui-form-label">
                  <span class="we-red">*</span>密码
              </label>
              <div class="layui-input-inline">
                  <input type="password" id="password" name="rePassword" required="" lay-verify="repass"
                  autocomplete="off" class="layui-input">
              </div>
          </div>
          <div class="layui-form-item">
              <label for="password" class="layui-form-label">
              </label>
              <button  class="layui-btn" onclick="submitData()">
                  修改
              </button>
          </div>
      </div>
    </div>
		<script src="${pageContext.request.contextPath}/static/weadmin/lib/layui/layui.js" charset="utf-8"></script>
    <script type="text/javascript">
        let layer;
        layui.use(['upload','layer','form'], function(){
            let upload = layui.upload;
            layer =layui.layer;
        });

        function submitData(){
            let nickname=$("#nickname").val().trim();
            let sign=$("#sign").val().trim();
            let password=$("#password").val().trim();
            let lock=$("#lock").val().trim();
            let username=$("#username").val().trim();
            if(username.length===0){
                layer.msg('请输入登录账号')
                return
            }
            if(nickname.length===0){
                layer.msg('请输入昵称')
                return
            }
            if(password.length===0){
                layer.msg('请输入修改的密码')
                return
            }
            let data={};
            data['username']=username;
            data['password']=password;
            data['nickname']=nickname;
            data['sign']=sign;
            data['lock']=lock;
            data['sex']=$("#sex").val();
            data['id']=${user.id};
            $.ajax({
                url: "${pageContext.request.contextPath}/admin/user/edit",
                type: "post",
                contentType: "application/json;charset=utf-8",
                data: JSON.stringify(data),
                xhrFields: {
                    withCredentials: true
                },
                crossDomain: true,
                success: function (data) {
                    console.log(data);
                    if(data.result===1){
                        layer.msg('修改成功');
                    }else {
                        layer.msg(data.msg);
                    }
                },
                error: function () {
                    layer.msg('修改失败！');
                }
            });
        }
    </script>
  </body>

</html>
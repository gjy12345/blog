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
    <title>修改信息</title>
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
                    头像
                </label>
                <div class="layui-input-inline">
                    <div class="col-sm-8">
                        <div class="form-control" style="height: 120px;">
                            <c:if test="${ADMIN_SESSION_TAG.face==null}">
                                <img id="show_image"  style="height: 100px;width: 100px" src="${pageContext.request.contextPath}/static/img/face.png" alt="">
                            </c:if>
                            <c:if test="${ADMIN_SESSION_TAG.face!=null}">
                                <img id="show_image"  style="height: 100px;width: 100px" src="${ADMIN_SESSION_TAG.face}" alt="">
                            </c:if>

                        </div>
                        <button type="button" class="layui-btn layui-btn-sm" id="upload_face">
                            <i class="layui-icon">&#xe67c;</i>上传头像
                        </button>
                    </div>
                </div>
            </div>
          <div class="layui-form-item">
              <label for="username" class="layui-form-label">
                  登录名
              </label>
              <div class="layui-input-inline">
                  <input type="text" id="username" name="username"
                         disabled="disabled"
                  autocomplete="off" value="${ADMIN_SESSION_TAG.username}" class="layui-input">
              </div>
          </div>
            <div class="layui-form-item">
                <label for="username" class="layui-form-label">
                    昵称
                </label>
                <div class="layui-input-inline">
                    <input type="text" id="nickname" name="nickname"
                           autocomplete="off" value="${ADMIN_SESSION_TAG.nickname}" class="layui-input">
                </div>
            </div>
          <div class="layui-form-item">
              <label for="sign" class="layui-form-label">
                  个性签名
              </label>
              <div class="layui-input-inline">
                  <textarea type="text" id="sign" name="sign"
                  autocomplete="off" class="layui-input layui-textarea">${ADMIN_SESSION_TAG.sign}</textarea>
              </div>
          </div>
            <div class="layui-form-item">
                <label for="sign" class="layui-form-label">
                    性别
                </label>
                <div class="layui-input-inline">
                    <select id="sex">
                        <option value="0" ${ADMIN_SESSION_TAG.sex==0?'selected':''} >男</option>
                        <option value="1" ${ADMIN_SESSION_TAG.sex==1?'selected':''} >女</option>
                    </select>
                </div>
            </div>
          <div class="layui-form-item">
              <label for="password" class="layui-form-label">
                  <span class="we-red">*</span>新密码
              </label>
              <div class="layui-input-inline">
                  <input type="password" id="password" name="pass" required="" lay-verify="pass"
                  autocomplete="off" class="layui-input">
              </div>
              <div class="layui-form-mid layui-word-aux">
                  6到16个字符
              </div>
          </div>
          <div class="layui-form-item">
              <label for="rePassword" class="layui-form-label">
                  <span class="we-red">*</span>确认密码
              </label>
              <div class="layui-input-inline">
                  <input type="password" id="rePassword" name="rePassword" required="" lay-verify="repass"
                  autocomplete="off" class="layui-input">
              </div>
          </div>
          <div class="layui-form-item">
              <label for="rePassword" class="layui-form-label">
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
        let face=null;
        layui.use(['upload','layer','form'], function(){
            let upload = layui.upload;
            layer =layui.layer;
            //执行实例
            let uploadInst = upload.render({
                elem: '#upload_face' //绑定元素
                ,url: '${pageContext.request.contextPath}/common/upload' //上传接口
                ,acceptMime: 'image/jpg, image/png'
                ,done: function(res){
                    //上传完毕回调
                    console.log(res)
                    if(res.success===1){
                        face=res.url;
                        layer.msg('上传成功！')
                        $("#show_image").attr('src',face);
                    }else {
                        layer.msg(res.msg);
                    }
                }
                ,error: function(){
                    //请求异常回调
                    layer.msg('图片上传失败');
                }
            });
        });

        function submitData(){
            let nickname=$("#nickname").val().trim();
            let sign=$("#sign").val().trim();
            let password=$("#password").val().trim();
            let rePassword=$("#rePassword").val().trim();
            if(nickname.length===0){
                layer.msg('请输入昵称')
                return
            }
            if(password.length===0){
                layer.msg('请输入修改的密码')
                return
            }
            if(password!==rePassword){
                layer.msg('前后输入密码不一致')
                return
            }
            let data={};
            data['face']=face;
            data['password']=password;
            data['nickname']=nickname;
            data['sign']=sign;
            data['sex']=$("#sex").val();
            $.ajax({
                url: "${pageContext.request.contextPath}/admin/editSelf",
                type: "post",
                contentType: "application/json;charset=utf-8",
                data: JSON.stringify(data),
                xhrFields: {
                    withCredentials: true
                },
                crossDomain: true,
                success: function (data) {
                    console.log(data);
                    if(data.result==1){
                        layer.msg('修改成功');
                    }else {
                        layer.msg('修改失败');
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
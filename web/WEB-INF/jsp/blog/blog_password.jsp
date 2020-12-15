<%--
  Created by IntelliJ IDEA.
  User: gujianyang
  Date: 2020/12/14
  Time: 7:21 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="${pageContext.request.contextPath}/static/weadmin/lib/layui/layui.js" charset="utf-8"></script>
<div class="col-md-12">
    <div class="col-md-4 col-md-offset-4" style="height: 300px">
        <div>
            <h1 class="h3 mb-3 fw-normal">此文章需要输入密码:</h1>
            <label for="password" class="visually-hidden"></label>
            <input type="password" id="password" class="form-control" placeholder="访问密码" required="" autofocus="">
            <button class="btn btn-sm btn-primary" type="submit" onclick="submit()" style="margin-top: 20px">访问</button>
            <p style="color: red;margin-top: 10px">${msg}</p>
        </div>
    </div>
    <script type="text/javascript">
        let layer;
        layui.use(['layer'], function () {
            layer = layui.layer;
        });
        function submit(){
            let password=$("#password").val().trim();
            if(password.length===0){
                layer.msg('请输入密码');
                return
            }
            window.location='${pageContext.request.contextPath}/article/detail?url=${url}&password='+password;
        }
    </script>
</div>

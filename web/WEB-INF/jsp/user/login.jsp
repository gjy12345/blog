<%--
Created by IntelliJ IDEA.
User: gujianyang
Date: 2020/10/12
Time: 10:41 上午
To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">
  <title>user-login</title>

  <!-- Bootstrap core CSS -->
  <link href="${pageContext.request.contextPath}/static/css/bootstrap.css" rel="stylesheet">

  <!-- Custom styles for this template -->
  <link href="${pageContext.request.contextPath}/static/css/signin.css" rel="stylesheet">
  <script src="${pageContext.request.contextPath}/static/blog/jquery-2.1.3.min.js"></script>
  <script src="${pageContext.request.contextPath}/static/blog/bootstrap.min.js"></script>
  <script type="text/javascript">
    var contextPath='${pageContext.request.contextPath}';
    function changeVcode(){
      document.getElementById('vcode_img').src = contextPath+"/common/vcode?time=?"+new Date();
    }
  </script>
</head>

<body class="text-center">
<form class="form-signin" action="${pageContext.request.contextPath}/user/manage/login" method="post">
  <h1 style="margin-bottom: 50px">Blog Login</h1>
  <div class="mb-2">
    <input type="text" id="inputUsername" value="${username}" name="username" class="form-control" placeholder="账号" required autofocus>
  </div>
  <div class="">
    <input type="password" name="password" value="${password}" id="inputPassword" class="form-control" placeholder="密码" required>
  </div>
  <div  class=" ">
    <input type="text" id="vcode" name="vcode" class="form-control col-lg-5 d-inline mb-1 float-left" placeholder="验证码" required>
    <img onclick="changeVcode()" id="vcode_img" src="${pageContext.request.contextPath}/common/vcode" class="form-control col-lg-6 mb-1 d-inline float-right"
         style="height: 45px;width: 100%;padding: 0px"
         alt="">
  </div>
  <p style="clear: both;color: red;text-align: left;font-size: 14px">
    ${msg}
  </p>
  <button class=" btn btn-lg btn-dark btn-block" type="submit" >登 陆</button>
  <p class="mt-5 mb-3 text-muted">&copy; ${blogConfig.year} ${blogConfig.blogName}</p>
</form>
</body>
</html>

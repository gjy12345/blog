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
  </head>

  <body class="text-center">
    <form class="form-signin">
      <h1 style="margin-bottom: 50px">Blogs Login</h1>
      <label for="inputEmail" class="sr-only">Email address</label>
      <input type="email" id="inputEmail" class="form-control" placeholder="Username" required autofocus>
      <label for="inputPassword" class="sr-only">Password</label>
      <input type="password" id="inputPassword" class="form-control" placeholder="Password" required>
      <button class="btn btn-lg btn-dark btn-block" type="submit">登 陆</button>
      <p class="mt-5 mb-3 text-muted">&copy; 2021 Blog</p>
    </form>
  </body>
</html>

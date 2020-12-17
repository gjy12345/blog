<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>500</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/error/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/error/404.css">
    <script src="${pageContext.request.contextPath}/static/js/500.js"></script>

</head>

<body>
    <div class="error">
        <div class="container-floud">
            <div class="col-xs-12 ground-color text-center">
                <div class="container-error-404">
                    <div class="clip">
                        <div class="shadow">
                            <span class="digit thirdDigit">5</span>
                        </div>
                    </div>
                    <div class="clip">
                        <div class="shadow">
                            <span class="digit secondDigit">0</span>
                        </div>
                    </div>
                    <div class="clip">
                        <div class="shadow">
                            <span class="digit firstDigit">0</span>
                        </div>
                    </div>
                    <div class="msg">OH!
                        <span class="triangle"></span>
                    </div>
                </div>
                <h2 class="h1">${msg!=null?msg:"服务器开小差了..."}</h2>
                <p>
                    <a class="tohome" href="${pageContext.request.contextPath}/">返回首页</a>
                </p>
            </div>
        </div>
        <div style="clear: both;height: 200px;width: 100%"></div>
    </div>


</body>
</html>
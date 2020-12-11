<%--
  Created by IntelliJ IDEA.
  User: gujianyang
  Date: 2020/12/10
  Time: 6:12 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>设置博客参数</title>
    <script src="${pageContext.request.contextPath}/static/blog/jquery-2.1.3.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/blog/bootstrap.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/blog/bootstrap.min.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/weadmin/lib/layui/layui.js" charset="utf-8"></script>

</head>
<body>

    <div class="col-md-12 " style="margin-top: 40px">
        <form class="form-horizontal col-md-12" role="form">
            <input type="text" id="uploadImage" hidden="hidden">
            <div class="form-group">
                <label for="pub_level" class="col-sm-2 control-label">谁可以看:</label>
                <div class="col-sm-8">
<%--                    <input type="email" class="form-control" id="inputEmail3" placeholder="">--%>
                    <select id="pub_level" class="form-control" onchange="change_pub_level()">
                        <option value="7">所有人</option>
                        <option value="5">只有我</option>
                        <option value="6">需要密码</option>
                    </select>
                </div>
            </div>
            <div class="form-group" id="visit_password" hidden="hidden">
                <label for="password" class="col-sm-2 control-label">密码:</label>
                <div class="col-sm-8">
                    <input type="password" class="form-control" id="password" placeholder="请输入博客访问密码">
                </div>
            </div>
            <div class="form-group">
                <label for="comment" class="col-sm-2 control-label">是否允许评论:</label>
                <div class="col-sm-8">
                    <%--                    <input type="email" class="form-control" id="inputEmail3" placeholder="">--%>
                    <select id="comment" class="form-control">
                        <option value="1">允许</option>
                        <option value="0">不允许</option>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label for="ms" class="col-sm-2 control-label">博客描述:</label>
                <div class="col-sm-8">
                    <textarea id="ms" type="text" maxlength="200"
                              rows="8"
                              class="form-control"  placeholder="将和标题一起展示"></textarea>
                </div>
            </div>
            <div class="form-group">
                <label for="image_file" class="col-sm-2 control-label">博客封面(最大10m):</label>
                <div class="col-sm-8">
                    <div class="form-control" style="height: 315px;text-align: center">
                        <img id="show_image"  style="height: 300px;width: auto" src="${pageContext.request.contextPath}/static/img/placeholder.jpg" alt="">
                    </div>
                    <input class="form-control" type="file" id="image_file" onchange="onImageChange()" >
                </div>
            </div>
            <div class="form-group">
                <label for="key_word" class="col-sm-2 control-label">博客关键字:</label>
                <div class="col-sm-8">
                    <input type="text" id="key_word" class="form-control" placeholder="让文章更容易被搜索到">
                </div>
            </div>
            <div class="form-group">
                <label for="a_type" class="col-sm-2 control-label">分类:</label>
                <div class="col-sm-8">
                    <select id="a_type" class="form-control">
                        <option value=""></option>
                        <c:forEach items="${categories}" var="category">
                            <option value="${category.id}">${category.name}</option>
                        </c:forEach>
                        categories
                    </select>
                </div>
            </div>
        </form>
    </div>
    <script type="text/javascript">
        function onImageChange(){
            uploadFile();
        }
        function uploadFile(){
            // 获取上传文件的数据
            let fromData=new FormData();
            console.log($('#image_file')[0].files[0]);
            fromData.append('file',$('#image_file')[0].files[0]);

            $.ajax({
                url: "${pageContext.request.contextPath}/common/upload",
                type: 'post',
                async: true,
                contentType: false,
                processData: false, //默认为true，默认情况下，发送的数据将被转换为对象，设为false不希望进行转换
                data: fromData,
                success: function(data) {
                    if(data.success==1){
                        layui.use('layer', function(){
                            var layer = layui.layer;

                            layer.msg('上传成功');
                        });
                        $("#uploadImage").val(data.url);
                        $("#show_image").attr('src',data.url);
                    }else {
                        layui.use('layer', function(){
                            var layer = layui.layer;

                            layer.msg('上传失败');
                        });
                    }
                },
                error: function(data) {

                }
            })
        }

        function change_pub_level(){
            let options=$("#pub_level option:selected").val();
            if(Number(options)===6){
                $("#visit_password").show();
            }else {
                $("#visit_password").hide();
            }
        }
    </script>
</body>
</html>

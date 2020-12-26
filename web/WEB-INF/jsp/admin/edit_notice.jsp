<%--
  Created by IntelliJ IDEA.
  User: gujianyang
  Date: 2020/12/9
  Time: 4:43 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>修改公告</title>
</head>
<body id="body">
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/editormd/css/editormd.min.css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/blog/bootstrap.min.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/weadmin/lib/layui/layui.js" charset="utf-8"></script>

<div style="width: 98%;margin: 0 auto;" id="top_input">
    <label class="control-label" style="margin-bottom: 10px;margin-top: 10px">
        <i style="color: red">*&nbsp;</i>公告标题:
    </label>
    <input type="text" id="title" class="form-control" placeholder="请输入标题" value="${notice.title}" required>
    <label class="control-label" style="margin-bottom: 10px;margin-top: 10px">
        <i style="color: red">*&nbsp;</i>
        公告内容:
    </label>
</div>
<div id="editor" style="margin: 0 auto;background: grey;">
    <!-- Tips: Editor.md can auto append a `<textarea>` tag -->
    <textarea id="#md" style="display:none;height: 100%;" class="col-md-12">${notice.content}</textarea>
</div>
<div id="view">

</div>
<div id="buttons_box" style="width: 98%;margin: 0 auto;text-align: right;padding-top: 10px">
    <button type="button" id="submit" class="btn btn-primary">修改公告</button>

</div>
<script src="${pageContext.request.contextPath}/static/editormd/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/static/editormd/js/editormd.min.js"></script>
<script type="text/javascript">
    var editor;
    var layer;
    layui.use('layer', function(){
        layer = layui.layer;
    });
    function resetHeight() {
        var h = $(window).height();
        $("#body").css("height", h + "px");
        var css = $("#top_input").height() + $("#buttons_box").height() + 20;
        $(function () {
            editor = editormd("editor", {
                width: "98%",
                height: (h - css) + 'px',
                imageUpload: true,
                emoji: true,
                imageFormats: ["jpg", "jpeg", "gif", "png"],
                imageUploadURL: '${pageContext.request.contextPath}/common/upload',
                htmlDecode: false,
                placeholder: '输入公告内容',
                // markdown: "xxxx",     // dynamic set Markdown text
                path: "${pageContext.request.contextPath}/static/editormd/lib/"  // Autoload modules mode, codemirror, marked... dependents libs path
            });
        });
    }

    resetHeight();
    $("#submit").click(function () {
        const title = $("#title").val();
        const blog = editor.getPreviewedHTML();
        if(title.trim().length==0||blog.trim().length==0){
            layer.msg('标题为空或内容为空!');
            return ;
        }
        let data={};
        data['id']=${notice.id};
        data['title']=title;
        data['content']=editor.getMarkdown();
        $.ajax({
            url: "${pageContext.request.contextPath}/admin/notice/edit",
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
                    layer.msg('修改公告成功');
                    setTimeout(function (){
                        let index = parent.layer.getFrameIndex(window.name);
                        //关闭当前frame
                        parent.layer.close(index);
                    },50)
                }else {
                    layer.msg(data.msg);
                }
            },
            error: function () {
                layer.msg('修改公告失败！');
            }
        });
    });

    window.onresize = function () {
        resetHeight()

    }


</script>
</body>
</html>
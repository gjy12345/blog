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
    <title>发布博客</title>
</head>
<body id="body">
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/editormd/css/editormd.min.css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/blog/bootstrap.min.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/weadmin/lib/layui/layui.js" charset="utf-8"></script>

<div style="width: 98%;margin: 0 auto;" id="top_input">
    <label class="control-label" style="margin-bottom: 10px;margin-top: 10px">
        <i style="color: red">*&nbsp;</i>博客标题:
    </label>
    <input type="text" id="title" class="form-control" placeholder="请输入标题" required>
    <label class="control-label" style="margin-bottom: 10px;margin-top: 10px">
        <i style="color: red">*&nbsp;</i>
        博客内容:
    </label>
</div>
<div id="editor" style="margin: 0 auto;background: grey;">
    <!-- Tips: Editor.md can auto append a `<textarea>` tag -->
    <textarea id="#md" style="display:none;height: 100%;" class="col-md-12"></textarea>
</div>
<div id="view">

</div>
<div id="buttons_box" style="width: 98%;margin: 0 auto;text-align: right;padding-top: 10px">

    <button type="button" id="save_cg" class="btn btn-warning">保存草稿</button>
    <button type="button" id="release_blog" class="btn btn-primary">发布博客</button>

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
                placeholder: '开始创作吧..',
                // markdown: "xxxx",     // dynamic set Markdown text
                path: "${pageContext.request.contextPath}/static/editormd/lib/"  // Autoload modules mode, codemirror, marked... dependents libs path
            });
        });
    }

    resetHeight();
    $("#save_cg").click(function () {
        //暂时保存博客
        const title = $("#title").val();
        const blog = editor.getPreviewedHTML();
        //getMarkdown
        //getPreviewedHTML

    });
    $("#release_blog").click(function () {
        const title = $("#title").val();
        const blog = editor.getPreviewedHTML();
        if(title.trim().length==0||blog.trim().length==0){
            layer.msg('标题为空或博客内容为空!');
            return ;
        }
        //发布博客
        $.get('${pageContext.request.contextPath}/user/manage/choseOptions', {}, function(str){
            layer.open({
                type: 1,
                content: str ,//注意，如果str是object，那么需要字符拼接。
                area: [($(window).width()*0.8)+'px', ($(window).height()*0.8)+'px'],
                btn: ['发布'],
                yes: function(index, layero){
                    //按钮【按钮一】的回调
                    let json={};
                    json['publicityLevel']=Number(layero.contents().find("#pub_level").val());
                    json['password']=layero.contents().find("#password").val();
                    json['description']=layero.contents().find("#ms").val();
                    json['comment']=Number(layero.contents().find("#comment").val());
                    json['thumb']=layero.contents().find("#uploadImage").val();
                    json['keywords']=layero.contents().find("#key_word").val();
                    let type=layero.contents().find("#a_type").val();
                    if(type.length>0){
                        json['type']=type;
                    }
                    json['title']=title;
                    json['content']=blog;
                    setTimeout( () => {
                        $.ajax({
                            url: "${pageContext.request.contextPath}/user/manage/article/new",
                            type: "post",
                            contentType: "application/json;charset=utf-8",
                            data: JSON.stringify(json),
                            xhrFields: {
                                withCredentials: true
                            },
                            crossDomain: true,
                            success: function (data) {
                                console.log(data);
                                if(data.result==1){
                                    layer.msg('发布成功');
                                    $("#title").val('');
                                    editor.clear()
                                }else {
                                    layer.msg('发布失败');
                                }
                            },
                            error: function () {
                                layer.msg('发布失败');
                            }
                        });
                    },50)
                    layer.close(index);
                }
            });
        });
    });

</script>
</body>
</html>
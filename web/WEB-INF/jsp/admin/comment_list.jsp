<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>评论列表</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/weadmin/static/css/font.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/weadmin/static/css/weadmin.css">
    <script src="${pageContext.request.contextPath}/static/blog/jquery-2.1.3.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/customer.js"></script>
</head>

<body>
<div class="weadmin-body">
    <div class="weadmin-block  layui-form">
<%--        <button class="layui-btn " onclick="addBlog()"><i class="layui-icon">&#xe654;</i>写博客</button>--%>

    <div class="layui-inline">
            <label class="layui-form-label layui-inline" style="width: auto">模糊搜索:</label>
            <div class="layui-input-block">
                <input class="layui-input" placeholder="关键字" name="keyword" id="keyword">
            </div>
        </div>
        <button class="layui-btn" onclick="initData(1)"><i class="layui-icon">&#xe615;</i>搜索</button>
        <a class="layui-btn layui-btn-sm" style="line-height:1.6em;margin-top:3px;float:right"
           href="javascript:location.replace(location.href);" title="刷新">
            <i class="layui-icon" style="line-height:30px">&#xe666;</i></a>
    </div>

    <div id="demo">
        <table class="layui-table ">
            <thead>
            <tr>
                <th>序号</th>
                <th>文章名</th>
                <th>评论人</th>
                <th>评论</th>
                <th>评论时间</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody id="tbody"  class="layui-form-item" >

            </tbody>
        </table>
    </div>
    <div id="foot">
        <div id="pages" style="float: left"></div>
        <span class="fr" id="size" style="line-height:40px;float: right"></span>
    </div>
</div>
<script src="${pageContext.request.contextPath}/static/weadmin/lib/layui/layui.all.js" charset="utf-8"></script>
<%--<script src="${pageContext.request.contextPath}/static/weadmin/lib/layui/layui.js" charset="utf-8"></script>--%>
<script type="text/javascript">
    let laypage;
    let layer;
    let currentPage;
    let element ;
    let form;
    layui.use(['layer','form','element'], function () {
        layer = layui.layer;
        element = layui.element;
        form = layer.form;
    });
    layui.use('laypage', function () {
        laypage = layui.laypage;
        initData(1);
    });
    //执行一个laypage实例
    function initPages(count, current) {
        currentPage = current;
        laypage.render({
            elem: 'pages',
            curr: current,
            count: count //数据总数，从服务端得到
            , jump: function (obj, first) {
                //obj包含了当前分页的所有参数，比如：
                //首次不执行
                if (!first) {
                    console.log('执行')
                    initData(obj.curr)
                }
            }
        });
        $("#size").html('共有数据：' + count + ' 条');
    }

    function initData(page) {
        let name=$("#keyword").val();
        let showType=$("#look_type").val();
        let url = '${pageContext.request.contextPath}/admin/comment/list?page=' + page
        +'&keyword='+name;
        if(showType!==''){
            url=url+'&showType='+showType;
        }
        $.ajax({
            url: url,
            type: 'post',
            async: true,
            contentType: 'application/json',
            processData: false, //默认为true，默认情况下，发送的数据将被转换为对象，设为false不希望进行转换
            success: function (data) {
                initPages(data.total, page);
                initTableData(data.data, page);
            },
            error: function (data) {
                layer.msg('发生了错误!');
            }
        })
    }

    function initTableData(data, page) {
        let tbody = $("#tbody");
        let len = data.length;
        let html = '';
        let keyword=$("#keyword").val().trim();
        for (let i = 0; i < len; i++) {
            html=html+'<tr id="line'+(i+1)+'">' +
                '<td>'+((page-1)*10+i+1)+'</td>' +
                '<td>'+data[i].articleTitle+'</td>' +
                '<td>'+data[i].userName+'</td>' +
                '<td width="45%">'+replaceHeightLight(data[i].content,keyword)+'</td>' +
                '<td>'+data[i].commonTime+'</td>' +
                '<td>' +
                '<button class="layui-btn layui-btn-sm layui-btn-normal" ' +
                'onclick="lookBlog(\'' + data[i].articleUrl + '\')">查看博客</button><button class="layui-btn layui-btn-sm layui-btn-danger" ' +
                'onclick="deleteComment(' + data[i].id + ',' + (i + 1) + ')">删除评论</button>' +
                '</td>' +
                '</tr>';
        }
        tbody.html(html);

    }

    function lookBlog(key){
        let lookUrl='${pageContext.request.contextPath}/article/detail?url='+key;
        window.open(lookUrl,'_blank')
    }

    function deleteComment(commentId,rowIndex){
        let row=$("#line"+rowIndex);
        layer.confirm('是否确认删除此评论？', function (index) {
            $.ajax({
                url: '${pageContext.request.contextPath}/admin/comment/delete',
                type: 'post',
                async: true,
                data:{id:commentId},
                success: function (data) {
                    if(data.result===1){
                        layer.msg('删除成功!')
                        row.remove();
                    }else {
                        layer.msg(data.msg)
                    }
                },
                error: function (data) {
                    layer.msg('发生了错误!');
                }
            })
        });
    }
</script>
</body>

</html>
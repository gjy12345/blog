<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>文章列表</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/weadmin/static/css/font.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/weadmin/static/css/weadmin.css">
    <script src="${pageContext.request.contextPath}/static/blog/jquery-2.1.3.min.js"></script>
</head>

<body>
<div class="weadmin-body">
    <div class="weadmin-block  layui-form">
        <div class=" layui-inline">
            <label class="layui-form-label" style="width: auto">公开性:</label>
            <div class="layui-input-block">
                <select id="pub_level">
                    <option value=""></option>
                    <option value="7">公开</option>
                    <option value="5">私密</option>
                    <option value="6">密码</option>
                </select>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label" style="width: auto">分类:</label>
            <div class="layui-input-block">
                <select id="type">
                    <option value=""></option>
                    <c:forEach items="${categories}" var="category">
                        <option value="${category.id}">${category.name}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label layui-inline" style="width: auto">博客名:</label>
            <div class="layui-input-block">
                <input class="layui-input" placeholder="博客名" name="name" id="name">
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
                <th>账号</th>
                <th>昵称</th>
                <th>文章名</th>
                <th>状态</th>
                <th>分类</th>
                <th>关键词</th>
                <th>开放性</th>
                <th>访问量</th>
                <th>评论数</th>
                <th>评论开关</th>
                <th>创建时间</th>
                <th>最后修改时间</th>
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
<script src="${pageContext.request.contextPath}/static/js/customer.js"></script>
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
        let name=$("#name").val();
        let status=$("#pub_level").val();
        let type=$("#type").val();
        let url = '${pageContext.request.contextPath}/admin/blog/list?page=' + page
        +'&title='+name;
        if(status!==''&&status.length!==0){
            url=url+'&publicityLevel='+status;
        }
        if(type!==''&&type.length!==0){
            url=url+'&type='+type;
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
        let keyword=$("#name").val().trim();
        for (let i = 0; i < len; i++) {
            html=html+
                '<tr id="line'+(i+1)+'"><th>'+((page-1)*10 + i+1)+'</th>' +
                '<td>'+data[i].loginUsername+'</td>'+
                '<td>'+data[i].userName +'</td>'+
                '<td id="name'+(i+1)+'">'+replaceHeightLight(data[i].title,keyword)+'</td>' +
                '<td>'+setStatusType(data[i])+'</td>' +
                '<td>'+data[i].typeName+'</td>' +
                '<td>'+data[i].keywords+'</td>' +
                '<td>'+setPubFiled(data[i].pubLevelName,data[i].password)+'</td>' +
                '<td>'+data[i].visit+'</td>' +
                '<td>'+data[i].common+'</td>' +
                '<td id="state' + (i + 1) + '">'+(data[i].comment == 0 ? '关闭' : '开启')+'</td>'+
                '<td>'+data[i].createTime+'</td>' +
                '<td>'+data[i].updateTime+'</td>' +
                '<td>'+
                '<button class="layui-btn layui-btn-sm layui-btn-normal" onclick="lookBlog(\''+data[i].url+'\')">查看</button>'
                +
                (data[i].status===3?'<button class="layui-btn layui-btn-sm " onclick="lockBlog(' + data[i].id + ','+data[i].status+')">解锁</button> ':
                    '<button class="layui-btn layui-btn-sm layui-btn-warm" onclick="lockBlog(' + data[i].id + ','+data[i].status+')">锁定</button> ') +
                '<button class="layui-btn layui-btn-sm layui-btn-danger"' +
                ' onclick="deleteBlog(' + data[i].id + ',' + (i + 1) + ')">删除</button>'
                +
                '</td></tr>';
        }
        tbody.html(html);

    }

    function lookBlog(key){
        let lookUrl='${pageContext.request.contextPath}/article/detail?url='+key;
        window.open(lookUrl,'_blank')
    }

    function setPubFiled(pub,password){
        if(pub==='密码'){
            return pub+'&nbsp;<a href="javascript:void(0);" style="color: lightskyblue" onclick="showPassword(\''+password+'\')">查看密码</ac>';
        }
        return pub;
    }

    function showPassword(p){
        layer.alert('访问密码:'+p);
    }

    function deleteBlog(id, row_index) {
        let line = $("#line" + row_index);
        let name = $("#name" + row_index).text();
        layer.confirm('是否确认删除博客:' + name + "?", function (index) {
            $.ajax({
                url: '${pageContext.request.contextPath}/admin/blog/delete',
                type: 'post',
                data: {id: id},
                success: function (data) {
                    if (data.result === 1) {
                        layer.msg('删除成功');
                        line.remove();
                    } else {
                        layer.msg(data.msg);
                    }
                    layer.close(index);
                },
                error: function (data) {
                    layer.msg('发生了错误!');
                    layer.close(index);
                }
            })
        });
    }

    function lockBlog(id,status) {
        if(status===4){
            layer.msg('无法锁定处于回收站的文章!');
            return
        }
        let message;
        let lock;
        if(status===1){
            message="是否确认锁定博客?锁定后将无法被除作者外的用户查看";
            lock=3;
        }
        else if(status===3){
            message="是否确认解锁博客?";
            lock=1;
        }
        else{
            layer.msg('未知状态')
            return
        }
        layer.confirm(message, function (index) {
            $.ajax({
                url: '${pageContext.request.contextPath}/admin/blog/lockOrUnlock',
                type: 'post',
                data: {id: id,changeStatus:lock},
                success: function (data) {
                    if (data.result === 1) {
                        layer.msg('操作成功');
                        initData(currentPage);
                    } else {
                        layer.msg(data.msg);
                    }
                    layer.close(index);
                },
                error: function (data) {
                    layer.msg('发生了错误!');
                    layer.close(index);
                }
            })
        });
    }

    function setStatusType(data){
        if(data.status===1){
            return "<span style='color: green'>正常</span>"
        }else if(data.status===3){
            return "<span style='color: red'>锁定</span>"
        }else if(data.status===4){
            return "<span style='color: red'>回收站</span>"
        }
    }
</script>
</body>

</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>用户列表</title>
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
        <button class="layui-btn " onclick="addUser()"><i class="layui-icon">&#xe654;</i>新建用户</button>
        <div class=" layui-inline">
            <label class="layui-form-label" style="width: auto">性别:</label>
            <div class="layui-input-block">
                <select id="sex">
                    <option value=""></option>
                    <option value="0">男</option>
                    <option value="1">女</option>
                </select>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label" style="width: auto">状态:</label>
            <div class="layui-input-block">
                <select id="status">
                    <option value=""></option>
                    <option value="1">锁定</option>
                    <option value="0">未锁定</option>
                </select>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label layui-inline" style="width: auto">模糊搜索:</label>
            <div class="layui-input-block">
                <input class="layui-input" placeholder="用户名或昵称" name="name" id="name">
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
                <th>用户名</th>
                <th>昵称</th>
                <th>性别</th>
                <th>状态</th>
                <th>在线状态</th>
                <th>上次发布</th>
                <th>上次登录ip</th>
                <th>上次登录时间</th>
                <th>博客数量</th>
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
<script src="${pageContext.request.contextPath}/static/js/customer.js"></script>
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
        let status=$("#status").val();
        let sex=$("#sex").val();
        let url = '${pageContext.request.contextPath}/admin/user/list?page=' + page
        +'&keyword='+name;
        if(status!==''&&status.length!==0){
            url=url+'&lock='+status;
        }
        if(sex!==''&&sex.length!==0){
            url=url+'&sex='+sex;
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
            html=html+'<tr id="line'+(i+1)+'">' +
                '<td>'+((page-1)*10+i+1)+'</td>' +
                '<td id="name'+(i+1)+'">'+replaceHeightLight(data[i].username,keyword)+'</td>' +
                '<td id="nickname'+(i+1)+'">'+replaceHeightLight(data[i].nickname,keyword)+'</td>' +
                '<td>'+data[i].sexName+'</td>'+
                '<td>'+(data[i].lock===0?'<span style="color: green">正常</span>':'<span style="color: red">锁定</span>')+'</td>' +
                '<td>'+(
                    data[i].online===true?'<span style="color: green">在线</span>':
                        '<span style="color: orangered">不在线</span>'
                )+'</td>' +
                '<td>'+(
                    data[i].lastRelease!==undefined?data[i].lastRelease:'未发布'
                )+'</td>' +
                '<td>'+(
                    data[i].lastLoginIp!==undefined?data[i].lastLoginIp:'从未登录'
                )+'</td>' +
                '<td>'+(
                    data[i].lastLoginTime!==undefined?data[i].lastLoginTime:'从未登录'
                )+'</td>' +
                '<td>'+data[i].blogCount+'</td>' +
                '<td>'+
                '<button class="layui-btn layui-btn-sm layui-btn-normal" onclick="showUser('+data[i].id+','+(i+1)+')">查看</button>'+
                '<button class="layui-btn layui-btn-sm " onclick="editUser('+data[i].id+','+(i+1)+')">编辑</button>'+
                '<button class="layui-btn layui-btn-sm layui-btn-danger" onclick="deleteUser('+data[i].id+','+(i+1)+')">删除</button>'+
                (data[i].online===true?'<button class="layui-btn layui-btn-sm layui-btn-primary"' +
                    ' onclick="exitUser('+data[i].id+','+(i+1)+')">下线</button>':'')+
                '</td>' +
                '</tr>';
        }
        tbody.html(html);

    }

    function addUser(){
        let w=$(window).width()*0.4;
        let h=$(window).height()*0.9;
        layer.open({
            type:2,
            title: '新增',
            maxmin : true,
            area:[w+'px', h+'px'],
            content: "${pageContext.request.contextPath}/admin/user/add"
        });
    }

    function showUser(id,rowId){
        let w=$(window).width()*0.8;
        let h=$(window).height()*0.9;
        layer.open({
            type:2,
            title: '用户信息',
            maxmin : true,
            area:[w+'px', h+'px'],
            content: "${pageContext.request.contextPath}/user/info?userId="+id,
            end: function () {
                //刷新本页
                initData(currentPage);
            }
        });
    }

    function editUser(id,rowId){
        let w=$(window).width()*0.8;
        let h=$(window).height()*0.9;
        layer.open({
            type:2,
            title: '修改用户信息',
            maxmin : true,
            area:[w+'px', h+'px'],
            content: "${pageContext.request.contextPath}/admin/user/edit?userId="+id,
            end: function () {
                //刷新本页
                initData(currentPage);
            }
        });
    }

    function deleteUser(id,rowId){
        let line = $("#line" + rowId);
        let name = $("#name" + rowId).text();
        layer.confirm('是否确认删除用户:' + name + "?", function (index) {
            $.ajax({
                url: '${pageContext.request.contextPath}/admin/user/delete',
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

    function exitUser(id,rowId){
        let name=$("#nickname"+rowId).text();
        layer.confirm('是否确认强制用户: ' + name + " 下线?", function (index) {
            $.ajax({
                url: '${pageContext.request.contextPath}/admin/user/exitLogin',
                type: 'post',
                data: {id: id},
                success: function (data) {
                    if (data.result === 1) {
                        layer.msg('退出成功');
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
</script>
</body>

</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>文章分类</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/weadmin/static/css/font.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/weadmin/static/css/weadmin.css">
    <script src="${pageContext.request.contextPath}/static/blog/jquery-2.1.3.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/weadmin/lib/layui/lay/modules/element.js"></script>
</head>

<body>
<div class="weadmin-body">
    <div class="weadmin-block  layui-form">
        <button class="layui-btn" onclick="showAdd()"><i class="layui-icon">&#xe654;</i>添加</button>
        <div class=" layui-inline">
            <label class="layui-form-label">状态:</label>
            <div class="layui-input-block">
                <select id="status">
                    <option value=""></option>
                    <option value="0">正常</option>
                    <option value="1">停用</option>
                </select>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label layui-inline">分类名:</label>
            <div class="layui-input-block">
                <input class="layui-input" placeholder="分类名" name="name" id="name">
            </div>
        </div>
        <button class="layui-btn" onclick="initData(1)"><i class="layui-icon">&#xe615;</i>搜索</button>
        <a class="layui-btn layui-btn-sm" style="line-height:1.6em;margin-top:3px;float:right"
           href="javascript:location.replace(location.href);" title="刷新">
            <i class="layui-icon" style="line-height:30px">&#xe666;</i></a>
    </div>

    <div id="demo">
        <table class="layui-table">
            <thead>
            <tr>
                <th>序号</th>
                <th>分类名</th>
                <th>分类博客</th>
                <th>是否启用</th>
                <th>描述</th>
                <th>创建时间</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody id="tbody">

            </tbody>
        </table>
    </div>
    <div id="foot">
        <div id="pages" style="float: left"></div>
        <span class="fr" id="size" style="line-height:40px;float: right"></span>
    </div>
</div>
<script src="${pageContext.request.contextPath}/static/weadmin/lib/layui/layui.js" charset="utf-8"></script>
<script type="text/javascript">
    let laypage;
    let layer;
    let currentPage;
    layui.use(['layer','form'], function () {
        layer = layui.layer;
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
                console.log(obj.curr); //得到当前页，以便向服务端请求对应页的数据。
                //首次不执行
                if (!first) {
                    console.log('执行')
                    initData(obj.curr)
                }
            }
        });
        $("#size").html('共有数据：' + count + ' 条');
    }

    function showAdd() {
        layer.open({
            type: 2,
            area: ['500px', $(window).height() * 0.7 + 'px'],
            fix: false, //不固定
            shadeClose: true,
            shade: 0.4,
            title: '添加分类',
            content: '${pageContext.request.contextPath}/user/manage/article/type/add',
            end: function () {
                initData(1);
            }
        });
    }

    function initData(page) {
        let name=$("#name").val();
        let status=$("#status").val();
        console.log(name)
        console.log(status)
        let url = '${pageContext.request.contextPath}/user/manage/article/type/list?page=' + page
        +'&name='+name;
        if(status!==''&&status.length!==0){
            url=url+'&lock='+status;
        }
        $.ajax({
            url: url,
            type: 'get',
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
        for (let i = 0; i < len; i++) {
            html = html + '<tr id="line' + (i * page + 1) + '"><td>' + (i + 1) + '</td>'
                + '<td id="name' + (i + 1) + '">' + data[i].name + '</td>'
                + '<td>' + data[i].blogUseCount + '</td>'
                + '<td id="state' + (page + 1) + '"><span class="layui-btn  layui-btn-xs ' +
                (data[i].lock == 1 ? 'layui-btn-danger' : 'layui-btn-normal')
                + '">' + (data[i].lock == 1 ? '停用' : '启用')
                + '</span></td>'
                + '<td>' + data[i].description + '</td>'
                + '<td>' + data[i].createTime + '</td>'
                + '<td>' +
                '<button class="layui-btn layui-btn-sm" onclick="showEdit(' + data[i].id + ')">编辑</button> ' +
                (data[i].lock == 1 ?
                    '<button onclick="setLockOrUnlock(0,' + (i * page + 1) + ',' + data[i].id + ')" class="layui-btn layui-btn-normal layui-btn-sm">启用</button>'
                    : '<button onclick="setLockOrUnlock(1,' + (i * page + 1) + ',' + data[i].id + ')" class="layui-btn layui-btn-sm layui-btn-warm">禁用</button>') +
                '<button class="layui-btn layui-btn-sm layui-btn-danger"' +
                ' onclick="deleteCategory(' + data[i].id + ',' + (i + 1) + ')">删除</button>'
                +
                '</td></tr>';
        }
        tbody.html(html);
    }

    function deleteCategory(id, row_index) {
        let line = $("#line" + row_index);
        let name = $("#name" + row_index).text();
        layer.confirm('是否确认删除类型:' + name + "?删除后使用此分类的博客将会成为未分类!", function (index) {
            $.ajax({
                url: '${pageContext.request.contextPath}/user/manage/article/type/delete',
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

    function showEdit(id) {
        layer.open({
            type: 2,
            area: ['500px', $(window).height() * 0.7 + 'px'],
            fix: false, //不固定
            shadeClose: true,
            shade: 0.4,
            title: '修改分类',
            content: '${pageContext.request.contextPath}/user/manage/article/type/edit?id=' + id,
            end: function () {
                //刷新本页
                initData(currentPage);
            }
        });
    }

    function setLockOrUnlock(change_state, row_index, id) {
        let statue = $("#state" + row_index);
        let name = $("#name" + row_index).text();
        layer.confirm('是否确认' + (change_state == 0 ? '禁用' : '启用') + '类型:' + name + "?", function (index) {
            $.ajax({
                url: '${pageContext.request.contextPath}/user/manage/article/type/lockOrUnlock',
                type: 'post',
                data: {id: id, lock: change_state},
                success: function (data) {
                    let html;
                    if (data.result === 1) {
                        layer.msg(change_state === 0 ? '启用成功' : '禁用成功');
                        if (change_state === 0)
                            html = '<span class="layui-btn  layui-btn-xs layui-btn-normal">启用</span>';
                        else
                            html = '<span class="layui-btn  layui-btn-xs layui-btn-danger">禁用</span>';
                    } else {
                        layer.msg(data.msg);
                    }
                    statue.html(html);
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
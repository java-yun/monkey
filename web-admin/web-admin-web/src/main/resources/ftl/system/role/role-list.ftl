<#-- Created by IntelliJ IDEA.
 User: zxm
 Date: 2017/12/19
 Time: 18:00
 To change this template use File | Settings | File Templates.
 角色管理-->
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>角色管理</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi"/>
    <link rel="stylesheet" href="${re.contextPath}/plugin/layui/css/layui.css">
    <link rel="stylesheet" href="${re.contextPath}/plugin/lenos/main.css">
    <script type="text/javascript" src="${re.contextPath}/plugin/jquery/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${re.contextPath}/plugin/layui/layui.all.js" charset="utf-8"></script>
</head>

<body>
<div class="lenos-search">
    <div class="select">
        角色名：
        <div class="layui-inline">
            <input class="layui-input" style="height:38px; width:200px" id="roleName" autocomplete="off">
        </div>
        描述：
        <div class="layui-inline">
            <input class="layui-input" style="height:38px; width:200px" id="description" autocomplete="off">
        </div>
        <button class="select-on layui-btn layui-btn-sm" data-type="select"><i class="layui-icon"></i>
        </button>
        <button class="layui-btn layui-btn-sm icon-position-button" id="refresh" style="float: right;" data-type="reload">
            <i class="layui-icon">ဂ</i>
        </button>
    </div>

</div>
<div class="layui-col-md13">
    <div class="layui-btn-group">
        <@shiro.hasPermission name="role:add">
            <button class="layui-btn layui-btn-normal" data-type="add">
                <i class="layui-icon">&#xe608;</i>新增
            </button>
        </@shiro.hasPermission>
        <@shiro.hasPermission name="role:del">
            <button class="layui-btn layui-btn-danger" data-type="del">
                <i class="layui-icon">&#xe642;</i>删除
            </button>
        </@shiro.hasPermission>
    </div>
</div>
<table id="roleList" class="layui-hide" lay-filter="user"></table>
<script type="text/html" id="toolBar">
    <@shiro.hasPermission name="role:update">
        <a class="layui-btn layui-btn-xs  layui-btn-normal" lay-event="edit">编辑</a>
    </@shiro.hasPermission>
    <@shiro.hasPermission name="role:del">
        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
    </@shiro.hasPermission>
</script>
<script>
    layui.laytpl.toDateString = function (d, format) {
        const date = new Date(d || new Date());
        const ymd = [
                  this.digit(date.getFullYear(), 4),
                  this.digit(date.getMonth() + 1),
                  this.digit(date.getDate())
              ];
        const hms = [
                  this.digit(date.getHours()),
                  this.digit(date.getMinutes()),
                  this.digit(date.getSeconds())
              ];
        format = format || 'yyyy-MM-dd HH:mm:ss';
        return format.replace(/yyyy/g, ymd[0])
            .replace(/MM/g, ymd[1])
            .replace(/dd/g, ymd[2])
            .replace(/HH/g, hms[0])
            .replace(/mm/g, hms[1])
            .replace(/ss/g, hms[2]);
    };

    //数字前置补零
    layui.laytpl.digit = function (num, length, end) {
        let str = '';
        num = String(num);
        length = length || 2;
        for (let i = num.length; i < length; i++) {
            str += '0';
        }
        return num < Math.pow(10, length) ? str + (num | 0) : num;
    };

    document.onkeydown = function (e) { // 回车提交表单
        const theEvent = window.event || e;
        const code = theEvent.keyCode || theEvent.which;
        if (code === 13) {
            $(".select .select-on").click();
        }
    }
    layui.use('table', function () {
        const table = layui.table;
        //方法级渲染
        table.render({
            id: 'roleList',
            elem: '#roleList',
            url: 'showRoleList?v=' + new Date().getTime(),
            cols: [[
                {checkbox: true, fixed: true, width: '5%'},
                {field: 'roleName', align: 'center', title: '角色名称', width: '20%', sort: true},
                {field: 'description', align: 'center', title: '角色描述', width: '20%', sort: true},
                {field: 'createUser', align: 'center', title: '创建人', width: '110'},
                {field: 'updateUser', align: 'center', title: '修改人', width: '110'},
                {field: 'createTime', align: 'center', title: '创建时间', width: '180', templet: '<div>{{ layui.laytpl.toDateString(d.createTime) }}</div>'},
                {field: 'updateTime', align: 'center', title: '修改时间', width: '180', templet: '<div>{{ layui.laytpl.toDateString(d.updateTime) }}</div>'},
                {field: 'id', title: '操作', width: '20%', toolbar: "#toolBar"}
            ]],
            page: {
                layout: ['count', 'prev', 'page', 'next', 'limit', 'skip'],
                first: '首页',
                last: '尾页'
            },
            limit: 20,
            limits: [20, 50, 100],
            request: {
                pageName: 'page',
                limitName: 'limit'
            },
            height: 'full-83'
        });

        const $ = layui.$, active = {
            select: function () {
                const roleName = $('#roleName').val();
                const description = $('#description').val();
                table.reload('roleList', {
                    where: {
                        roleName: roleName,
                        description: description
                    }
                });
            },
            reload: function () {
                $('#roleName').val('');
                $('#description').val('');
                table.reload('roleList', {
                    where: {
                        roleName: null,
                        description: null
                    }
                });
            },
            add: function () {
                add('添加角色', 'showAddRole', 700, 450);
            },
            del: function () {
                const checkStatus = table.checkStatus('roleList'),
                        data = checkStatus.data;
                if (data.length === 0) {
                    layer.msg('请勾选一列', {icon: 5});
                }
                let ids = '';
                for (let i = 0; i < data.length; i++) {
                    if (i === 0) {
                      ids = data[i].id;
                    } else {
                      ids = ids + "," + data[i].id
                    }
                }
                layer.confirm('确定删除选中的角色?', function () {
                    del(ids);
                });
            },
            detail: function () {
                const checkStatus = table.checkStatus('roleList'),
                        data = checkStatus.data;
                if (data.length !== 1) {
                    layer.msg('请选择一行查看', {icon: 5});
                    return false;
                }
                detail('查看角色信息', 'toUpdateRole?id=' + data[0].id, 700, 450);
            }
        };

        //监听表格复选框选择
        table.on('checkbox(user)', function (obj) {
            console.log(obj)
        });
          //监听工具条
        table.on('tool(user)', function (obj) {
            const data = obj.data;
            if (obj.event === 'detail') {
                detail('查看角色', 'toUpdateRole?id=' + data.id, 700, 450);
            } else if (obj.event === 'del') {
                layer.confirm('确定删除角色[<label style="color: #00AA91;">' + data.roleName + '</label>]?', function () {
                    del(data.id);
                });
            } else if (obj.event === 'edit') {
                update('编辑角色', 'toUpdateRole?id=' + data.id, 700, 450);
            }
        });

        $('.layui-col-md13 .layui-btn').on('click', function () {
            const type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
        $('.select .layui-btn').on('click', function () {
            const type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });

    });

    function del(ids) {
        $.ajax({
            url: "del",
            type: "post",
            data: {ids: ids},
            success: function (d) {
                if (d.code === '000000') {
                    layer.msg(d.msg, {icon: 6, anim: 2});
                    layui.table.reload('roleList');
                } else {
                    layer.msg(d.msg, {icon: 5, anim: 2});
                }
            }
        });
    }

    function detail(title, url, w, h) {
        if (title == null || title === '') {
            title = false;
        }
        if (url == null || url === '') {
            url = "/error/404";
        }
        if (w == null || w === '') {
            w = ($(window).width() * 0.9);
        }
        if (h == null || h === '') {
            h = ($(window).height() - 50);
        }
        layer.open({
            id: 'user-detail',
            type: 2,
            area: [w + 'px', h + 'px'],
            fix: false,
            maxmin: true,
            shadeClose: true,
            shade: 0.4,
            title: title,
            content: url + '&detail=true',
            // btn:['关闭']
        });
    }

    /**
     * 更新用户
     */
    function update(title, url, w, h) {
        if (title == null || title === '') {
            title = false;
        }
        if (url == null || url === '') {
            url = "/error/404";
        }
        if (w == null || w === '') {
            w = ($(window).width() * 0.9);
        }
        if (h == null || h === '') {
            h = ($(window).height() - 50);
        }
        layer.open({
            id: 'user-update',
            type: 2,
            area: [w + 'px', h + 'px'],
            fix: false,
            maxmin: true,
            shadeClose: false,
            shade: 0.4,
            title: title,
            content: url + '&detail=false'
        });
    }

    /*弹出层*/
    /*
     参数解释：
     title   标题
     url     请求的url
     id      需要操作的数据id
     w       弹出层宽度（缺省调默认值）
     h       弹出层高度（缺省调默认值）
     */
    function add(title, url, w, h) {
        if (title == null || title === '') {
            title = false;
        }
        if (url == null || url === '') {
            url = "/error/404";
        }
        if (w == null || w === '') {
            w = ($(window).width() * 0.9);
        }
        if (h == null || h === '') {
            h = ($(window).height() - 50);
        }
        layer.open({
            id: 'user-add',
            type: 2,
            area: [w + 'px', h + 'px'],
            fix: false,
            maxmin: true,
            shadeClose: false,
            shade: 0.4,
            title: title,
            content: url
        });
    }
</script>
</body>

</html>

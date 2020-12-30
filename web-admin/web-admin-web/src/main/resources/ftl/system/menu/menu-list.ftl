<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title></title>
    <meta name="description" content="">
    <meta name="keywords" content="">
    <link href="" rel="stylesheet">
    <link rel="stylesheet" href="${re.contextPath}/plugin/layuitree/layui/css/layui.css">
    <link rel="stylesheet" href="${re.contextPath}/plugin/lenos/main.css"/>
</head>
<body>
<div class="lenos-search">
    <div class="select">
        菜单名称：
        <div class="layui-inline">
            <input class="layui-input" style="height:38px; width:200px" id="name" autocomplete="off"
                   placeholder="支持模糊查询">
        </div>
        类型：
        <div class="layui-inline">
            <select id="type" class="layui-select" style="height:38px; width:140px">
                <option value="">请选择</option>
                <option value="0">菜单</option>
                <option value="1">按钮</option>
            </select>
        </div>
        <button class="select-on layui-btn layui-btn-sm" data-type="select" id="search"><i class="layui-icon"></i>
        </button>
        <button class="layui-btn layui-btn-sm icon-position-no-button" id="refresh" style="float: right;" onclick="location.replace(location.href);">
            <i class="layui-icon i-icon" style="font-size: 21px">ဂ</i>
        </button>
    </div>
</div>

<div class="layui-col-md13">
    <div class="layui-btn-group">
        <#--      <@shiro.hasPermission name="menu:add">-->
        <button class="layui-btn layui-btn-normal" data-type="add">
            <i class="layui-icon">&#xe608;</i>新增
        </button>
        <#--      </@shiro.hasPermission>-->
    </div>
</div>
<div id="menuTree"></div>
</body>
<script type="text/javascript" src="${re.contextPath}/plugin/layuitree/layui/layui.js" charset="utf-8"></script>
<script type="text/javascript" src="${re.contextPath}/plugin/jquery/jquery-3.2.1.min.js" charset="utf-8"></script>
<script type="text/javascript">
    let layer;

    function del(nodeId) {
        layer.confirm("确定要删除该菜单吗？", {icon: 3, title: '提示'}, function (index) {
            $.ajax({
                url: "deleteMenu",
                type: "POST",
                data: {"id": nodeId},
                success: function (data) {
                    layer.close(index);
                    if (data.code === '000000') {
                        //window.top.layer.msg(data.msg,{icon:6,anim:2});
                        location.href = "showMenu";
                    } else {
                        window.top.layer.msg('请求失败', {icon: 5, anim: 2});
                    }
                },
                error: function (data) {
                    layer.close(index);
                    window.top.layer.msg('请求失败', {icon: 5, anim: 2});
                }
            });
        });
    }

    function update(nodeId) {
        add('编辑菜单', 'showUpdateMenu?id=' + nodeId, 700, 550);
    }

    function showDetail(nodeId) {
        console.log("查看菜单id：" + nodeId)
        add('查看菜单', 'showDetail?id=' + nodeId, 700, 550);
    }

    const layout = [
        {name: '菜单名称', treeNodes: true, headerClass: 'value_col', colClass: 'value_col', style: 'width: 10%'},
        {
            name: 'url', headerClass: 'value_col', colClass: 'value_col', style: 'width: 10%',
            render: function (row) {
                return '<div class="layui-table-cell laytable-cell-1-username">' + (typeof (row.url) == "undefined" ? '' : row.url) + '</div>'; //列渲染
            }
        },
        {
            name: '类型', headerClass: 'value_col', colClass: 'value_col', style: 'width: 10%',
            render: function (row) {
                return '<div class="layui-table-cell laytable-cell-1-username">' + (row.menuType === "1" ? '按钮' : '菜单') + '</div>'; //列渲染
            }
        },
        {
            name: '权限', headerClass: 'value_col', colClass: 'value_col', style: 'width: 10%',
            render: function (row) {
                return '<div class="layui-table-cell laytable-cell-1-username">' + (typeof (row.permission) == "undefined" ? '' : row.permission) + '</div>'; //列渲染
            }
        },
        {
            name: '图标', headerClass: 'value_col', colClass: 'value_col', style: 'width: 5%',
            render: function (row) {
                return '<div class="layui-table-cell laytable-cell-1-username"><i class="layui-icon">' + (typeof (row.icon) == "undefined" ? '' : row.icon) + '</i></div>'; //列渲染
            }
        },
        {
            name: '序号', headerClass: 'value_col', colClass: 'value_col', style: 'width: 5%',
            render: function (row) {
                return '<div class="layui-table-cell laytable-cell-1-username"><i class="layui-icon">' + (typeof (row.orderNum) == "undefined" ? '' : row.orderNum) + '</i></div>'; //列渲染
            }
        },
        {
            name: '操作',
            headerClass: 'value_col',
            colClass: 'value_col',
            style: 'width: 20%',
            render: function (row) {
                const child_len = row.children == null ? 0 : row.children.length;
                let str = '<@shiro.hasPermission name="menu:edit">' +
                    '<a class="layui-btn layui-btn-xs  layui-btn-normal" onclick="update(\'' + row.id + '\')"><i class="layui-icon">&#xe642;</i> 编辑</a>'
                    + '</@shiro.hasPermission>'; //列渲染
                if (child_len === 0) {
                    str += '<@shiro.hasPermission name="menu:del">' +
                        '<a class="layui-btn layui-btn-danger layui-btn-xs" onclick="del(\'' + row.id + '\')"><i class="layui-icon">&#xe640;</i> 删除</a>'
                        + '</@shiro.hasPermission>';
                }
                return str;
            }
        },
    ];

    layui.use(['tree', 'layer'], function () {
        layer = layui.layer;

        layui.treeGird({
            elem: '#menuTree',
            nodes: ${menus},
            layout: layout
        });
        const $ = layui.$, active = {
            add: function () {
                add('添加菜单', 'showAddMenu', 700, 550);
            }
        };
        $('.layui-btn-group .layui-btn').on('click', function () {
            const type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });

        $('#search').click(function () {
            const name = $('#name').val();
            const type = $('#type').val();
            $.ajax({
                url: 'showMenuByCondition?name=' + name + '&type=' + type,
                type: 'GET',
                success: function (data) {
                    if (data.code === '000000') {
                        $('#menuTree').html('');
                        layui.treeGird({
                            elem: '#menuTree',
                            nodes: data.data,
                            layout: layout
                        });
                    } else {
                        window.top.layer.msg(data.msg, {icon: 5, anim: 2});
                    }
                },
                error: function (data) {
                    window.top.layer.msg('请求失败', {icon: 5, anim: 2});
                }
            });
        });
    });

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

</html>
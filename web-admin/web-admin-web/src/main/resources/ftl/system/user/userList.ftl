<!DOCTYPE html>
<html>
<head>
    <title>用户管理</title>
    <#include "/common/head.ftl" />
</head>

<body>
<div class="layui-card">
    <div class="layui-form layui-card-header">
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">用户名：</label>
                <div class="layui-input-inline">
                    <input type="text" id="username" name="username" placeholder="请输入用户名" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">真实姓名：</label>
                <div class="layui-input-inline">
                    <input type="text" id="realName" name="realName" placeholder="请输入真实姓名" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">角色：</label>
                <div class="layui-input-inline">
                    <select name="roleId" id="roleId" lay-filter="roleId" class="layui-select">
                        <option value="">全部</option>
                        <#list roles as role>
                            <option value="${role.id}">${role.roleName}</option>
                        </#list>
                    </select>
                </div>
            </div>
            <div class="layui-inline">
                <button class="layui-btn layui-btn layui-btn-sm" lay-submit="" lay-filter="search" data-type="select">
                    <i class="layui-icon layui-btn-sm">&#xe615;</i>
                </button>
            </div>
        </div>
    </div>
    <div class="layui-card-body">
        <div style="padding-bottom: 10px;">
        <@shiro.hasPermission name="user:add">
            <button class="layui-btn opt-btn layui-btn-sm" data-type="add">
                添加
            </button>
        </@shiro.hasPermission>
        <@shiro.hasPermission name="user:del">
            <button class="layui-btn opt-btn layui-btn-sm layui-btn-danger" data-type="delete">
                批量删除
            </button>
        </@shiro.hasPermission>
        </div>
        <table id="dataList" class="layui-hide" lay-filter="dataList"></table>
    </div>
</div>

<script type="text/html" id="barDemo">
    <@shiro.hasPermission name="user:update">
        <a class="layui-btn layui-btn-xs  layui-btn-normal" lay-event="edit">编辑</a>
    </@shiro.hasPermission>
    <@shiro.hasPermission name="user:repass">
        <a class="layui-btn layui-btn-xs  layui-btn-normal" lay-event="changePwd">修改密码</a>
    </@shiro.hasPermission>
    <@shiro.hasPermission name="user:del">
        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
    </@shiro.hasPermission>
</script>
<script type="text/html" id="switchTpl">
    <input type="checkbox" name="sex" lay-skin="switch" lay-text="女|男" lay-filter="sexDemo">
</script>
<script type="text/html" id="imgTpl">
    <div><img src="${ossUrl}{{d.photo}}" class="layui-upload-img"></div>
</script>
<script type="text/html" id="delFlagTpl">
    {{# if (d.type == 1) { }}
    封禁
    {{# }else{ }}
    可用
    {{# } }}
</script>
<script type="text/html" id="typeTpl">
    {{# if (d.type == 1) { }}
    内部用户
    {{# }else{ }}
    商户用户
    {{# } }}
</script>
<script type="text/javascript" src="${re.contextPath}/plugin/js/common/merchant.adapter.layer.table.js"></script>
<script type="text/javascript" src="${re.contextPath}/plugin/js/system/user/sysUserList.js"></script>

</body>

</html>

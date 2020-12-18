<!DOCTYPE html>
<html>
<head>
    <title>支付方式配置</title>
    <#include "/common/head.ftl" />
</head>

<body>
<div class="layui-card">
    <div class="layui-form layui-card-header">
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label" style="width:100px">支付方式名称：</label>
                <div class="layui-input-inline">
                    <input type="text" id="subName" placeholder="支持模糊查询" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label" style="width:100px">支付方式编码：</label>
                <div class="layui-input-inline">
                    <input type="text" id="code" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">状态：</label>
                <div class="layui-input-inline">
                    <select id="state" lay-filter="state" class="layui-select">
                        <option value="">全部</option>
                        <option value="1">启用</option>
                        <option value="0">禁用</option>
                    </select>
                </div>
            </div>
            <div class="layui-inline">
                <button class="layui-btn layui-btn layui-btn-sm" id="search" lay-filter="search" data-type="select">
                    <i class="layui-icon layui-btn-sm">&#xe615;</i>
                </button>
            </div>
            <button class="layui-btn layui-btn-sm icon-position-no-button" id="refresh" style="float: right;" data-type="reload">
  				<i class="layui-icon i-icon" style="font-size: 21px">ဂ</i>
			</button>
        </div>
    </div>
    
</div>

<div class="layui-col-md13">
    <div class="layui-btn-group">
    	<@shiro.hasPermission name="payChannel:add">
    		<div class="layui-btn-group">
		        <button class="layui-btn layui-btn-normal" data-type="add" id="payChannelAdd">
		            <i class="layui-icon">&#xe608;</i>新增
		        </button>
	        </div>
        </@shiro.hasPermission>
    </div>
</div>
        
<table id="payChannelList" class="layui-hide" lay-filter="payChannel"></table>

<script type="text/html" id="toolBar">

	<@shiro.hasPermission name="payChannelChild:show">
    	<a class="layui-btn layui-btn-xs  layui-btn-normal" lay-event="children">子类</a>
    </@shiro.hasPermission>
	<@shiro.hasPermission name="payChannel:view">
    	<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="view">查看</a>
    </@shiro.hasPermission>
    <@shiro.hasPermission name="payChannel:update">
    	<a class="layui-btn layui-btn-xs  layui-btn-normal" lay-event="edit">编辑</a>
    </@shiro.hasPermission>
    <@shiro.hasPermission name="payChannel:del">
    	<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
    </@shiro.hasPermission>
    
</script>

<script type="text/javascript" src="${re.contextPath}/plugin/js/biz/payChannel/list.js"></script>

</body>

</html>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>黑名单管理</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi"/>
    <link rel="stylesheet" href="${re.contextPath}/plugin/layui/css/layui.css">
    <link rel="stylesheet" href="${re.contextPath}/plugin/lenos/main.css">
    <script type="text/javascript" src="${re.contextPath}/plugin/jquery/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${re.contextPath}/plugin/layui/layui.all.js "charset="utf-8"></script>
    <script type="text/javascript" src="${re.contextPath}/plugin/js/common.js "charset="utf-8"></script>
    <style type="text/css"> 
		.layui-select-title>.layui-input{
			width : 190px;
			height : 38px;
		}
	</style>
</head>

<body>
  <div class="lenos-search" style="margin-top: 10px;">
    <div class="select">
    	黑名单类型：
    	<div class="layui-inline" style="height:38px;">
      		<select id="blackType" class="layui-select" style="width: 150px;" lay-filter="blackType">
	    		<option value="">全部</option>
	        </select>
    	</div>
    	黑名单内容：
    	<div class="layui-inline" style="height:38px;">
  			<input class="layui-input" style="height:38px; width:200px;" id="blacklist">
    	</div>
    	
    	<button class="select-on layui-btn layui-btn-sm" data-type="select" id="search"><i class="layui-icon"></i>
    	</button>
    	
    	<button class="layui-btn layui-btn-sm icon-position-no-button" id="refresh" style="float: right;" data-type="reload">
      		<i class="layui-icon i-icon" style="font-size: 21px">ဂ</i>
    	</button>
    </div>
  </div>

<div class="layui-col-md13">
    <div class="layui-btn-group">
    	<@shiro.hasPermission name="userBlack:add">
    		<div class="layui-btn-group">
		        <button class="layui-btn layui-btn-normal" data-type="add" id="userBlackAdd">
		            <i class="layui-icon">&#xe608;</i>新增
		        </button>
	        </div>
	        <div class="layui-btn-group">
		        <button class="layui-btn layui-btn-normal" type="button" id="downloadTemplate">
		            <i class="layui-icon">&#xe608;</i>模板下载
		        </button>
	        </div>
	        <div class="layui-btn-group">
		        <button type="button" class="layui-btn layui-btn-normal" id="import">
		  			<i class="layui-icon">&#xe608;</i>导入
				</button>
	        </div>
        </@shiro.hasPermission>
    </div>
</div>
<table id="userBlackList" class="layui-hide" lay-filter="userBlack"></table>
<script type="text/html" id="toolBar">

	<@shiro.hasPermission name="userBlack:view">
    	<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="view">查看</a>
    </@shiro.hasPermission>
    <@shiro.hasPermission name="userBlack:update">
    	<a class="layui-btn layui-btn-xs  layui-btn-normal" lay-event="edit">编辑</a>
    </@shiro.hasPermission>
    <@shiro.hasPermission name="userBlack:del">
    	<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
    </@shiro.hasPermission>

</script>

<script type="text/javascript" src="${re.contextPath}/plugin/js/biz/userBlack/list.js "charset="utf-8"></script>
</body>

</html>
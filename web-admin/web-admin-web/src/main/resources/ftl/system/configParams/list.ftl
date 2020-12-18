<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>优惠活动配置</title>
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
    	<input type="hidden" id="configId" value="${configId?string('#')}"/>
    	参数名称：
    	<div class="layui-inline" style="height:38px;">
      		<input class="layui-input" style="height:38px; width:180px;" id="paramName" autocomplete="off" placeholder="支持模糊查询">
    	</div>
    	参数类型：
    	<div class="layui-inline" style="height:38px;">
      		<input class="layui-input" style="height:38px; width:180px;" id="paramType" autocomplete="off">
    	</div>
    	参数键：
    	<div class="layui-inline" style="height:38px;">
      		<input class="layui-input" style="height:38px; width:180px;" id="paramKey" autocomplete="off">
    	</div>
    	参数值：
    	<div class="layui-inline" style="height:38px;">
      		<input class="layui-input" style="height:38px; width:180px;" id="paramValue" autocomplete="off">
    	</div>
    	状态：
    	<div class="layui-inline" style="height:38px;">
  			<select id="state" class="layui-select" style="height:38px;width: 150px;">
    			<option value="">全部</option>
            	<option value=1>有效</option>
            	<option value=0>无效</option>
        	</select>
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
	    <@shiro.hasPermission name="configParams:add">
	        <button class="layui-btn layui-btn-normal" data-type="add" id="sysConfigParamsAdd">
	            <i class="layui-icon">&#xe608;</i>新增
	        </button>
        </@shiro.hasPermission>
    </div>
</div>
<table id="sysConfigParamsList" class="layui-hide" lay-filter="sysConfigParams"></table>
<script type="text/html" id="toolBar">

	<@shiro.hasPermission name="configParams:update">
    	<a class="layui-btn layui-btn-xs  layui-btn-normal" lay-event="edit">编辑</a>
    </@shiro.hasPermission>
    <@shiro.hasPermission name="configParams:del">
    	<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
    </@shiro.hasPermission>

</script>

<script type="text/javascript" src="${re.contextPath}/plugin/js/system/configParams/list.js "charset="utf-8"></script>
</body>

</html>
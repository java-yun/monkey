<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>系统操作日志</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi"/>
    <link rel="stylesheet" href="${re.contextPath}/plugin/layui/css/layui.css">
    <link rel="stylesheet" href="${re.contextPath}/plugin/lenos/main.css">
    <script type="text/javascript" src="${re.contextPath}/plugin/jquery/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${re.contextPath}/plugin/layui/layui.all.js "charset="utf-8"></script>
    <script type="text/javascript" src="${re.contextPath}/plugin/js/common.js "charset="utf-8"></script>
    <script type="text/javascript" src="${re.contextPath}/plugin/My97DatePicker/WdatePicker.js "charset="utf-8"></script>
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
    	用户名：
    	<div class="layui-inline" style="height:38px;">
  			<input class="layui-input" style="height:38px; width:200px;" id="optUserName" autocomplete="off">
    	</div>
    	
    	菜单名称：
    	<div class="layui-inline" style="height:38px;">
      		<input class="layui-input" style="height:38px; width:200px;" id="menuName" autocomplete="off" placeholder="支持模糊查询">
    	</div>
    	
    	按钮名称：
    	<div class="layui-inline" style="height:38px;">
      		<input class="layui-input" style="height:38px; width:200px;" id="buttonName" autocomplete="off" placeholder="支持模糊查询">
    	</div>
    	
    	操作时间:
      	<div class="layui-input-inline" style="width: 400px;">
          	<input name="startDate" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="请选择开始时间" id="startDate" autocomplete="off"
                 class="layui-input" style="width: 185px; height: 38px; display: inline;" value="${today}">
          	&nbsp;至&nbsp;
          	<input name="endDate" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="请选择结束时间" id="endDate" autocomplete="off"
                 class="layui-input" style="width: 185px; height: 38px; display: inline;" value="${today}">
      	</div>
    	
    	<button class="select-on layui-btn layui-btn-sm" data-type="select" id="search"><i class="layui-icon"></i>
    	</button>
    	
    	<button class="layui-btn layui-btn-sm icon-position-no-button" id="refresh" style="float: right;" data-type="reload">
      		<i class="layui-icon i-icon" style="font-size: 21px">ဂ</i>
    	</button>
    </div>
  </div>

<table id="sysOperateLogList" class="layui-hide" lay-filter="sysOperateLog"></table>
<script type="text/html" id="toolBar">

	<@shiro.hasPermission name="sysOperateLog:view">
    	<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="view">查看</a>
    </@shiro.hasPermission>

</script>

<script type="text/javascript" src="${re.contextPath}/plugin/js/system/sysOperateLog/list.js "charset="utf-8"></script>
</body>

</html>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>激活码</title>
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
    	激活码：
    	<div class="layui-inline" style="height:38px;">
      		<input class="layui-input" style="height:38px; width:200px;" id="activationNo" autocomplete="off">
    	</div>
    	用户ID：
    	<div class="layui-inline" style="height:38px;">
      		<input class="layui-input" style="height:38px; width:200px;" id="userId" autocomplete="off">
    	</div>
    	状态：
    	<div class="layui-inline" style="height:38px;">
      		<select id="state" class="layui-select" style="width: 120px;" lay-filter="state">
	    		<option value="">全部</option>
	        </select>
    	</div>
    	生效时间:
      	<div class="layui-input-inline" style="width: 400px;">
          	<input name="startDate" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="请选择开始时间" id="startDate" autocomplete="off"
                 class="layui-input" style="width: 185px; height: 38px; display: inline;">
          	&nbsp;至&nbsp;
          	<input name="endDate" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="请选择结束时间" id="endDate" autocomplete="off"
                 class="layui-input" style="width: 185px; height: 38px; display: inline;">
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
    	<@shiro.hasPermission name="activationCode:export">
	        <div class="layui-btn-group">
		        <button type="button" class="layui-btn layui-btn-normal" id="export">
		  			<i class="layui-icon">&#xe608;</i>导出激活码
				</button>
	        </div>
        </@shiro.hasPermission>
    </div>
</div>
<table id="activationCodeList" class="layui-hide" lay-filter="activationCode"></table>
<script type="text/html" id="toolBar">

	<@shiro.hasPermission name="activationCode:view">
    	<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="view">查看</a>
    </@shiro.hasPermission>

</script>

<script type="text/javascript" src="${re.contextPath}/plugin/js/biz/activationCode/list.js "charset="utf-8"></script>
</body>

</html>
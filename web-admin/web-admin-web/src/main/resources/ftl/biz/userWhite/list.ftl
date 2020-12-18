<!DOCTYPE html>
<html>
<head>
    <title>白名单管理</title>
    <#include "/common/head.ftl" />
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
    	白名单类型：
    	<div class="layui-inline" style="height:38px;">
      		<select id="whiteType" class="layui-select" style="width: 150px;">
	    		<option value="">全部</option>
	    		<#if typeList??>
	    			<#list typeList as item>
                        <option value="${item.value}">${item.desc}</option>
                    </#list>
	    		</#if>
	        </select>
    	</div>
    	白名单内容：
    	<div class="layui-inline" style="height:38px;">
  			<input class="layui-input" style="height:38px; width:200px;" id="content">
    	</div>
    	状态：
    	<div class="layui-inline" style="height:38px;">
      		<select id="state" class="layui-select" style="width: 150px;">
	    		<option value="">全部</option>
	    		<option value="1">启用</option>
	    		<option value="0">禁用</option>
	        </select>
    	</div>
    	创建时间:
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
    	<@shiro.hasPermission name="userWhite:add">
    		<div class="layui-btn-group">
		        <button class="layui-btn layui-btn-normal" data-type="add" id="userWhiteAdd">
		            <i class="layui-icon">&#xe608;</i>新增
		        </button>
	        </div>
        </@shiro.hasPermission>
    </div>
</div>
<table id="userWhiteList" class="layui-hide" lay-filter="userWhite"></table>
<script type="text/html" id="toolBar">

	<@shiro.hasPermission name="userWhite:view">
    	<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="view">查看</a>
    </@shiro.hasPermission>
    <@shiro.hasPermission name="userWhite:update">
    	<a class="layui-btn layui-btn-xs  layui-btn-normal" lay-event="edit">编辑</a>
    </@shiro.hasPermission>
    <@shiro.hasPermission name="userWhite:del">
    	<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
    </@shiro.hasPermission>

</script>

<script type="text/javascript" src="${re.contextPath}/plugin/js/biz/userWhite/list.js "charset="utf-8"></script>
</body>

</html>
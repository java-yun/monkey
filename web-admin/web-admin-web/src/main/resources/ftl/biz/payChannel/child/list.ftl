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
        	<input type="hidden" name="payChannelId" id="payChannelId" value="${payChannelId?string('#')}">
        	<div class="layui-inline" style="height:38px;">
	  			<form class="layui-form">
	  				<div class="layui-inline">
						<label class="layui-form-label">
							游戏名称：
						</label>
						<div class="layui-input-inline">		    
			        		<select class="layui-input" id="appId" style="width: 150px;" lay-search="">
			        			<option value=''>直接选择或搜索选择</option>
                        		<#list appList as app>
                            		<option value="${app.id}">${app.appName}</option>
                       			 </#list>
	                		</select>
	      				</div>
					</div>
					<div class="layui-inline">
						<label class="layui-form-label" style="width:100px">
							客户端类型：
						</label>
						<div class="layui-input-inline">		    
			        		<select class="layui-select" id="osPlatform" style="width: 150px;" >
			        			<option value="">全部</option>
                        		<#list osList as os>
                            		<option value="${os.value}">${os.desc}</option>
                       			 </#list>
	                		</select>
	      				</div>
	      			</div>
			    </form>
	    	</div>
            <div class="layui-inline">
                <label class="layui-form-label">SDK版本：</label>
                <div class="layui-input-inline" style="width:150px">
                    <input type="text" id="sdkVersion" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">状态：</label>
                <div class="layui-input-inline">
                    <select id="state" lay-filter="state" class="layui-select" style="width:100px">
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
    	<@shiro.hasPermission name="payChannelChild:add">
    		<div class="layui-btn-group">
		        <button class="layui-btn layui-btn-normal" data-type="add" id="payChannelChildAdd">
		            <i class="layui-icon">&#xe608;</i>新增
		        </button>
	        </div>
        </@shiro.hasPermission>
    </div>
</div>
        
<table id="payChannelChildList" class="layui-hide" lay-filter="payChannelChild"></table>

<script type="text/html" id="toolBar">

	<@shiro.hasPermission name="payChannelChild:view">
    	<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="view">查看</a>
    </@shiro.hasPermission>
    <@shiro.hasPermission name="payChannelChild:update">
    	<a class="layui-btn layui-btn-xs  layui-btn-normal" lay-event="edit">编辑</a>
    </@shiro.hasPermission>
    <@shiro.hasPermission name="payChannelChild:del">
    	<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
    </@shiro.hasPermission>
    
</script>

<script type="text/javascript" src="${re.contextPath}/plugin/js/biz/payChannel/child/list.js"></script>

</body>

</html>

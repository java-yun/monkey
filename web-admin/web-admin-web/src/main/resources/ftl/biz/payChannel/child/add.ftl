<!DOCTYPE html>
<html>

<head>
    <title>新增支付方式子类</title>
    <#include "/common/head.ftl" />
    <link rel="stylesheet" href="${re.contextPath}/plugin/x-admin/css/xadmin.css">
</head>

<body>
<div class="x-body">
    <form class="layui-form layui-form-pane" style="margin-left: 20px;">
        <div style="width:100%;height:500px;">
   			 
            <div class="layui-form-item">
                <fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
                    <legend style="font-size:16px;">基础信息</legend>
                </fieldset>
            </div>
            <div style="margin-left:15%">

				<div class="layui-form-item">
					<label class="layui-form-label" style="width: 23%">
						<span class="x-red">*</span>&nbsp;游戏名称
					</label>
					<div class="layui-input-inline" style="width: 65%">		    
		        		<select name="appId" class="layui-input" lay-verify="required" lay-search="">
		        			<option value=''>直接选择或搜索选择</option>
                    		<#list appList as app>
                        		<option value="${app.id}">${app.appName}</option>
                   			 </#list>
                		</select>
      				</div>
				</div>
				<div class="layui-form-item">
                    <label for="name" class="layui-form-label" style="width: 23%">
                        <span class="x-red">*</span>&nbsp;SDK版本
                    </label>
                    <div class="layui-input-inline" style="width: 65%">
                        <input type="text" name="sdkVersion" lay-verify="required" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label for="name" class="layui-form-label" style="width: 23%">
                        <span class="x-red">*</span>&nbsp;客户端类型
                    </label>
                    <div class="layui-input-inline" style="width: 65%">
                        <select name="osPlatform" class="layui-select" lay-verify="required">
                        	<option value="">全部</option>
                    		<#list osList as os>
                        		<option value="${os.value}">${os.desc}</option>
                   			</#list>
				        </select>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label for="name" class="layui-form-label" style="width: 23%">
                        <span class="x-red">*</span>&nbsp;状态
                    </label>
                    <div class="layui-input-inline" style="width: 65%">
                        <input name="state" value="1" title="启用" type="radio" checked="checked" lay-verify="required"/>
                        <input name="state" value="0" title="禁止" type="radio" lay-verify="required"/>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label" style="width: 23%">
                        	备注
                    </label>
                    <div class="layui-input-inline" style="width: 65%">
                        <input type="text" id="remark" name="remark" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <input type="hidden" value="${payChannelId?string('#')}" name="payChannelId" />
                <div style="height: 60px"></div>
            </div>


        </div>
        <div style="width: 100%;height: 55px;background-color: white;border-top:1px solid #e6e6e6; position: fixed;bottom: 1px;margin-left:-20px;">
            <div class="layui-form-item" style=" float: right;margin-right: 30px;margin-top: 8px">
                <button class="layui-btn layui-btn-normal" lay-filter="add" lay-submit="">
                    	保存
                </button>
                <button class="layui-btn layui-btn-primary" id="close">
                    	取消
                </button>
            </div>
        </div>
    </form>
</div>
<script type="text/javascript" src="${re.contextPath}/plugin/js/biz/payChannel/child/add.js "charset="utf-8"></script>
</body>

</html>

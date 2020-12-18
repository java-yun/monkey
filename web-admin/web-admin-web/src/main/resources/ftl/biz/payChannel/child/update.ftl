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
        
        	<#if isUpdate == false>
	        	<div class="layui-form-item">
	    			<fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
	      				<legend style="font-size:16px;">支付图标</legend>
	    			</fieldset>
	      			<div class="layui-input-inline">
	
	          			<div  id="demo3" style="margin-top: 20px;margin-left: 50px">
	            			<img src="${ossUrl}${payChannelChild.icon}" width="100px" height="100px" class="layui-upload-img layui-circle">
	          			</div>
	
	      			</div>
	   			</div>
   			</#if>
   			 
            <div class="layui-form-item">
                <fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
                    <legend style="font-size:16px;">基础信息</legend>
                </fieldset>
            </div>
            <div style="margin-left:15%">
            
            	<#if isUpdate == false>
            		<div class="layui-form-item">
	                    <label class="layui-form-label" style="width: 23%">
	                        <span class="x-red">*</span>&nbsp;主体名称
	                    </label>
	                    <div class="layui-input-inline" style="width: 65%">
	                        <input type="text" name="primaryName" lay-verify="required" class="layui-input" value="${payChannelChild.primaryName}" disabled="disabled">
	                    </div>
	                </div>
	                <div class="layui-form-item">
	                    <label class="layui-form-label" style="width: 23%">
	                       	<span class="x-red">*</span>&nbsp;支付方式名称
	                    </label>
	                    <div class="layui-input-inline" style="width: 65%">
	                        <input type="text" name="subName" lay-verify="required" class="layui-input" value="${payChannelChild.subName}" disabled="disabled">
	                    </div>
	                </div>
	                <div class="layui-form-item">
	                    <label class="layui-form-label" style="width: 23%">
	                       	<span class="x-red">*</span>&nbsp;支付方式编码
	                    </label>
	                    <div class="layui-input-inline" style="width: 65%">
	                        <input type="text" name="code" lay-verify="required" class="layui-input" value="${payChannelChild.code}" disabled="disabled">
	                    </div>
	                </div>
            	</#if>

				<div class="layui-form-item">
					<label class="layui-form-label" style="width: 23%">
						<span class="x-red">*</span>&nbsp;游戏名称
					</label>
					<div class="layui-input-inline" style="width: 65%">		    
		        		<select name="appId" class="layui-input" lay-verify="required" lay-search="" <#if isUpdate == false>disabled="disabled"</#if>>
		        			<option value=''>直接选择或搜索选择</option>
                    		<#list appList as app>
                        		<option value="${app.id}" <#if app.id == payChannelChild.appId>selected="selected"</#if>>${app.appName}</option>
                   			 </#list>
                		</select>
      				</div>
				</div>
				<div class="layui-form-item">
                    <label for="name" class="layui-form-label" style="width: 23%">
                        <span class="x-red">*</span>&nbsp;SDK版本
                    </label>
                    <div class="layui-input-inline" style="width: 65%">
                        <input type="text" name="sdkVersion" lay-verify="required" class="layui-input" value="${payChannelChild.sdkVersion}" <#if isUpdate == false>disabled="disabled"</#if>>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label for="name" class="layui-form-label" style="width: 23%">
                        <span class="x-red">*</span>&nbsp;客户端类型
                    </label>
                    <div class="layui-input-inline" style="width: 65%">
                        <select name="osPlatform" class="layui-select" lay-verify="required" <#if isUpdate == false>disabled="disabled"</#if>>
                        	<option value="">全部</option>
                    		<#list osList as os>
                        		<option value="${os.value}" <#if os.value == payChannelChild.osPlatform>selected="selected"</#if>>${os.desc}</option>
                   			</#list>
				        </select>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label for="name" class="layui-form-label" style="width: 23%">
                        <span class="x-red">*</span>&nbsp;状态
                    </label>
                    <div class="layui-input-inline" style="width: 65%">
                        <input name="state" value="1" title="启用" type="radio" lay-filter="state" <#if payChannelChild.state == 1>checked="checked"</#if>/>
                        <input name="state" value="0" title="禁止" type="radio" lay-filter="state" <#if payChannelChild.state == 0>checked="checked"</#if>/>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label" style="width: 23%">
                        	备注
                    </label>
                    <div class="layui-input-inline" style="width: 65%">
                        <input type="text" id="remark" name="remark" class="layui-input" value="${payChannelChild.remark}" <#if isUpdate == false>disabled="disabled"</#if>>
                    </div>
                </div>
                <div style="height: 60px"></div>
            </div>


        </div>
        <div style="width: 100%;height: 55px;background-color: white;border-top:1px solid #e6e6e6; position: fixed;bottom: 1px;margin-left:-20px;">
            <div class="layui-form-item" style=" float: right;margin-right: 30px;margin-top: 8px">
                <#if isUpdate == true>
            		<button class="layui-btn layui-btn-normal" lay-filter="add" lay-submit="">
	                   	 保存
	                </button>
	                <button class="layui-btn layui-btn-primary" id="close">
	                   	 取消
	                </button>
            	<#else>
            		<button class="layui-btn layui-btn-primary" id="close">
	                   	 关闭
	                </button>
            	</#if>
            </div>
        </div>
    </form>
</div>
<script type="text/javascript" src="${re.contextPath}/plugin/tools/base.js "charset="utf-8"></script>
<script type="text/javascript" src="${re.contextPath}/plugin/js/biz/payChannel/child/update.js "charset="utf-8"></script>
</body>

</html>

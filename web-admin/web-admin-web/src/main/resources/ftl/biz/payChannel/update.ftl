<!DOCTYPE html>
<html>

<head>
    <title>编辑或查看支付方式</title>
    <#include "/common/head.ftl" />
    <link rel="stylesheet" href="${re.contextPath}/plugin/x-admin/css/xadmin.css">
</head>

<body>
<div class="x-body">
    <form class="layui-form layui-form-pane" style="margin-left: 20px;">
        <div style="width:100%;height:500px;">
        	<div class="layui-form-item">
    			<fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
      				<legend style="font-size:16px;">图标上传</legend>
    			</fieldset>
      			<div class="layui-input-inline">
    				<div class="layui-upload-drag" style="margin-left:10%;" id="test10">
      					<i style="font-size:30px;" class="layui-icon"></i>
     					<p style="font-size: 10px">点击上传，或将文件拖拽到此处</p>
    				</div>
      			</div>
      			<div class="layui-input-inline">

          			<div  id="demo2" style="margin-top: 20px;margin-left: 50px">
          				<img src="${ossUrl}${payChannel.icon}" width="100px" height="100px" class="layui-upload-img layui-circle">
          			</div>
					<input id="icon" name="icon" type="hidden" value="${payChannel.icon}">
      			</div>
   			</div>
   			
            <div class="layui-form-item">
                <fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
                    <legend style="font-size:16px;">基础信息</legend>
                </fieldset>
            </div>
            <div style="margin-left:15%">
            
            	<input type="hidden" name="id" value="${payChannel.id?string('#')}" id="id"/>

                <div class="layui-form-item">
                    <label class="layui-form-label" style="width: 23%">
                        <span class="x-red">*</span>&nbsp;主体名称
                    </label>
                    <div class="layui-input-inline" style="width: 65%">
                        <input type="text" name="primaryName" lay-verify="required" class="layui-input" value="${payChannel.primaryName}" <#if isUpdate == false>disabled="disabled"</#if>>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label" style="width: 23%">
                       	<span class="x-red">*</span>&nbsp;支付方式名称
                    </label>
                    <div class="layui-input-inline" style="width: 65%">
                        <input type="text" name="subName" lay-verify="required" class="layui-input" value="${payChannel.subName}" <#if isUpdate == false>disabled="disabled"</#if>>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label" style="width: 23%">
                       	<span class="x-red">*</span>&nbsp;支付方式编码
                    </label>
                    <div class="layui-input-inline" style="width: 65%">
                        <input type="text" name="code" lay-verify="required" class="layui-input" value="${payChannel.code}" <#if isUpdate == false>disabled="disabled"</#if>>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label" style="width: 23%">
                        <span class="x-red">*</span>&nbsp;排序
                    </label>
                    <div class="layui-input-inline" style="width: 65%">
                        <input type="text" id="sort" name="sort" lay-verify="required|number" class="layui-input" value="${payChannel.sort}" <#if isUpdate == false>disabled="disabled"</#if>>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label" style="width: 23%">
                        <span class="x-red">*</span>&nbsp;状态
                    </label>
                    <div class="layui-input-inline" style="width: 65%">
                        <input name="state" value="1" title="启用" type="radio" lay-filter="state" <#if payChannel.state == 1>checked="checked"</#if>/>
                        <input name="state" value="0" title="禁止" type="radio" lay-filter="state" <#if payChannel.state == 0>checked="checked"</#if>/>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label" style="width: 23%">
                        	备注
                    </label>
                    <div class="layui-input-inline" style="width: 65%">
                        <input type="text" id="remark" name="remark" class="layui-input" value="${payChannel.remark}" <#if isUpdate == false>disabled="disabled"</#if>>
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
</body>
<script type="text/javascript" src="${re.contextPath}/plugin/js/biz/payChannel/update.js "charset="utf-8"></script>

</html>

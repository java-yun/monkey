<!DOCTYPE html>
<html>

<head>
    <title>新增白名单</title>
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
                        <span class="x-red">*</span>&nbsp;白名单类型
                    </label>
                    <div class="layui-input-inline" style="width: 65%">
                        <select name="whiteType" lay-filter="whiteType" class="layui-select" lay-verify="required">
		        			<option value="">全部</option>
		        			<#if typeList??>
	    						<#list typeList as item>
                        			<option value="${item.value}">${item.desc}</option>
                    			</#list>
	    					</#if>
                        </select>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label" style="width: 23%">
                       	<span class="x-red">*</span>&nbsp;白名单内容
                    </label>
                    <div class="layui-input-inline" style="width: 65%">
                        <input type="text" id="content" name="content" lay-verify="required" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label" style="width: 23%">
                        <span class="x-red">*</span>&nbsp;状态
                    </label>
                    <div class="layui-input-inline" style="width: 65%">
                        <input name="state" value="1" title="启用" type="radio" checked="checked" lay-filter="state" id="state"/>
                        <input name="state" value="0" title="禁止" type="radio" lay-filter="state" id="state"/>
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
</body>
<script type="text/javascript" src="${re.contextPath}/plugin/js/biz/userWhite/add.js "charset="utf-8"></script>

</html>

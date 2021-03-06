<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>编辑查看白名单</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi"/>
    <link rel="stylesheet" href="${re.contextPath}/plugin/layui/css/layui.css">
    <link rel="stylesheet" href="${re.contextPath}/plugin/x-admin/css/xadmin.css">
    <script type="text/javascript" src="${re.contextPath}/plugin/jquery/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${re.contextPath}/plugin/layui/layui.all.js" charset="utf-8"></script>
    <script type="text/javascript" src="${re.contextPath}/plugin/tools/tool.js"></script>
    <script type="text/javascript" src="${re.contextPath}/plugin/js/common.js "charset="utf-8"></script>
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
            
            	<input type="hidden" name="id" value="${userWhite.id?string('#')}" id="id"/>

                <div class="layui-form-item">
                    <label class="layui-form-label" style="width: 23%">
                        <span class="x-red">*</span>&nbsp;白名单类型
                    </label>
                    <div class="layui-input-inline" style="width: 65%">
                    	<select name="whiteType" lay-filter="whiteType" class="layui-select" lay-verify="required" <#if isUpdate == false>disabled="disabled"</#if>>
		        			<option value="">全部</option>
		        			<#if typeList??>
	    						<#list typeList as item>
                        			<option value="${item.value}" <#if item.value == userWhite.whiteType>selected="selected"</#if>>${item.desc}</option>
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
                        <input type="text" name="content" lay-verify="required" class="layui-input" value="${userWhite.content}" <#if isUpdate == false>disabled="disabled"</#if>>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label for="state" class="layui-form-label" style="width: 23%">
                        <span class="x-red">*</span>&nbsp;状态
                    </label>
                    <div class="layui-input-inline" style="width: 65%">
                        <input name="state" value="1" title="启用" type="radio" lay-filter="state" id="state" <#if userWhite.state == 1>checked="checked"</#if>/>
                        <input name="state" value="0" title="禁止" type="radio" lay-filter="state" id="state" <#if userWhite.state == 0>checked="checked"</#if>/>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label" style="width: 23%">
                        	备注
                    </label>
                    <div class="layui-input-inline" style="width: 65%">
                        <input type="text" name="remark" class="layui-input" value="${userWhite.remark}" <#if isUpdate == false>disabled="disabled"</#if>>
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
<script type="text/javascript" src="${re.contextPath}/plugin/js/biz/userWhite/update.js "charset="utf-8"></script>

</html>

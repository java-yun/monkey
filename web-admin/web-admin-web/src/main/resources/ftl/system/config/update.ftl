<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>新增参数大类配置</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi"/>
    <link rel="stylesheet" href="${re.contextPath}/plugin/layui/css/layui.css">
    <link rel="stylesheet" href="${re.contextPath}/plugin/x-admin/css/xadmin.css">
    <script type="text/javascript" src="${re.contextPath}/plugin/jquery/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${re.contextPath}/plugin/layui/layui.all.js" charset="utf-8"></script>
    <script type="text/javascript" src="${re.contextPath}/plugin/tools/tool.js"></script>
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

				<input type="hidden" name="bizType" value="${sysConfig.bizType}" id="bizType"/>
				
				<div class="layui-form-item">
                    <label for="name" class="layui-form-label">
                        <span class="x-red">*</span>&nbsp;大类ID
                    </label>
                    <div class="layui-input-inline" style="width: 65%">
                        <input type="text" name="id" lay-verify="required" autocomplete="off" class="layui-input" value="${sysConfig.id?string('#')}" disabled="disabled">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label for="name" class="layui-form-label">
                        <span class="x-red">*</span>&nbsp;大类名称
                    </label>
                    <div class="layui-input-inline" style="width: 65%">
                        <input type="text" name="configName" lay-verify="required" autocomplete="off" class="layui-input" value="${sysConfig.configName}" <#if isUpdate == false>disabled="disabled"</#if>>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label for="name" class="layui-form-label">
                       	<span class="x-red">*</span>&nbsp;编码
                    </label>
                    <div class="layui-input-inline" style="width: 65%">
                        <input type="text" name="code" lay-verify="required" autocomplete="off" class="layui-input" value="${sysConfig.code}" <#if isUpdate == false>disabled="disabled"</#if>>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label for="name" class="layui-form-label">
                        <span class="x-red">*</span>&nbsp;状态
                    </label>
                    <div class="layui-input-inline" style="width: 65%">
                        <input name="state" value="1" title="有效" type="radio" <#if sysConfig.state == 1>checked="checked"</#if> lay-filter="state"/>
                        <input name="state" value="0" title="无效" type="radio" <#if sysConfig.state == 0>checked="checked"</#if> lay-filter="state"/>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label for="name" class="layui-form-label">
                        	参数说明
                    </label>
                    <div class="layui-input-inline" style="width: 65%">
                        <input type="text" name="remark" lay-verify="remark" autocomplete="off" class="layui-input" value="${sysConfig.remark}">
                    </div>
                </div>
                <div style="height: 60px"></div>
            </div>


        </div>
        <div style="width: 100%;height: 55px;background-color: white;border-top:1px solid #e6e6e6; position: fixed;bottom: 1px;margin-left:-20px;">
            <div class="layui-form-item" style=" float: right;margin-right: 30px;margin-top: 8px">
                <button class="layui-btn layui-btn-normal" lay-filter="update" lay-submit="">
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
<script type="text/javascript" src="${re.contextPath}/plugin/js/system/config/update.js "charset="utf-8"></script>

</html>

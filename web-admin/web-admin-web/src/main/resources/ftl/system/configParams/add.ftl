<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>新增参数配置</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi"/>
    <link rel="stylesheet" href="${re.contextPath}/plugin/layui/css/layui.css">
    <link rel="stylesheet" href="${re.contextPath}/plugin/x-admin/css/xadmin.css">
    <script type="text/javascript" src="${re.contextPath}/plugin/jquery/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${re.contextPath}/plugin/layui/layui.all.js" charset="utf-8"></script>
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

                <input type="hidden" id="configId" name="configId" value="${configId?string('#')}">
                <div class="layui-form-item">
                    <label for="name" class="layui-form-label">
                        <span class="x-red">*</span>参数名称
                    </label>
                    <div class="layui-input-inline" style="width: 65%">
                        <input type="text" id="paramName" name="paramName" lay-verify="required" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label for="name" class="layui-form-label">
                        <span class="x-red">*</span>&nbsp;参数值
                    </label>
                    <div class="layui-input-inline" style="width: 65%">
                        <input type="text" id="paramValue" name="paramValue" lay-verify="required" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label for="name" class="layui-form-label">
                        <span class="x-red">*</span>&nbsp;参数键
                    </label>
                    <div class="layui-input-inline" style="width: 65%">
                        <input type="text" id="paramKey" name="paramKey" lay-verify="required" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label for="name" class="layui-form-label">
                        <span class="x-red">*</span>&nbsp;参数类型
                    </label>
                    <div class="layui-input-inline" style="width: 65%">
                        <input type="text" id="paramType" name="paramType" lay-verify="required" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label for="name" class="layui-form-label">
                        <span class="x-red">*</span>&nbsp;状态
                    </label>
                    <div class="layui-input-inline" style="width: 65%">
                        <input name="state" value="1" title="有效" type="radio" checked="checked" lay-filter="state" id="state"/>
                        <input name="state" value="0" title="无效" type="radio" lay-filter="state" id="state"/>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label for="name" class="layui-form-label">
                       	 参数说明
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
<script type="text/javascript" src="${re.contextPath}/plugin/js/system/configParams/add.js "charset="utf-8"></script>
</body>

</html>

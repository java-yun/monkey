<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>激活码详情</title>
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
            
            	<input id="stateHid" type="hidden" value="${activation.state}" />

                <div class="layui-form-item">
                    <label for="name" class="layui-form-label">
                        	激活码
                    </label>
                    <div class="layui-input-inline" style="width: 65%">
                        <input type="text" value="${activation.activationNo}" autocomplete="off" class="layui-input" disabled="disabled">
                    </div>
                </div>
                 <div class="layui-form-item">
                    <label for="name" class="layui-form-label">
                        	用户ID
                    </label>
                    <div class="layui-input-inline" style="width: 65%">
                        <input type="text" value="${activation.userId}" class="layui-input" disabled="disabled">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">
                        <span class="x-red">*</span>&nbsp;状态
                    </label>
                    <div class="layui-input-inline" style="width: 65%">
                        <select name="state" class="layui-select" id="state" disabled="disabled">
		        			<option value="">全部</option>
                        </select>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label for="name" class="layui-form-label">
                        	生效时间
                    </label>
                    <div class="layui-input-inline" style="width: 65%">
                        <input type="text" value="${activation.effectTime?string("yyyy-MM-dd HH:mm:ss")}" class="layui-input" disabled="disabled">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label for="name" class="layui-form-label">
                        	过期时间
                    </label>
                    <div class="layui-input-inline" style="width: 65%">
                        <input type="text" value="${activation.deadTime?string("yyyy-MM-dd HH:mm:ss")}" class="layui-input" disabled="disabled">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label for="name" class="layui-form-label">
                        	使用时间
                    </label>
                    <div class="layui-input-inline" style="width: 65%">
	                    <#if activation.useTime??>
	                        <input type="text" value="${activation.useTime?string("yyyy-MM-dd HH:mm:ss")}" class="layui-input" disabled="disabled">
	                    <#else>
	                        <input type="text" value="" class="layui-input" disabled="disabled">
	                    </#if>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label for="name" class="layui-form-label">
                        	备注
                    </label>
                    <div class="layui-input-inline" style="width: 65%">
                        <input type="text" value="${activation.remark}" autocomplete="off" class="layui-input" disabled="disabled">
                    </div>
                </div>
                <div style="height: 60px"></div>
            </div>
        </div>
        <div style="width: 100%;height: 55px;background-color: white;border-top:1px solid #e6e6e6; position: fixed;bottom: 1px;margin-left:-20px;">
            <div class="layui-form-item" style=" float: right;margin-right: 30px;margin-top: 8px">
        		<button class="layui-btn layui-btn-primary" id="close">
                   	 关闭
                </button>
            </div>
        </div>

    </form>
</div>
<script type="text/javascript" src="${re.contextPath}/plugin/js/biz/activationCode/view.js "charset="utf-8"></script>
</body>

</html>

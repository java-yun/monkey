<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>独代游戏详情</title>
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

                <div class="layui-form-item">
                    <label for="name" class="layui-form-label">
                        	用户名
                    </label>
                    <div class="layui-input-inline" style="width: 65%">
                        <input type="text" value="${record.optUserName}" autocomplete="off" class="layui-input" disabled="disabled">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label for="name" class="layui-form-label">
                        	菜单名称
                    </label>
                    <div class="layui-input-inline" style="width: 65%">
                        <input type="text" value="${record.menuName}" autocomplete="off" class="layui-input" disabled="disabled">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label for="name" class="layui-form-label">
                        	按钮名称
                    </label>
                    <div class="layui-input-inline" style="width: 65%">
                        <input type="text" value="${record.buttonName}" autocomplete="off" class="layui-input" disabled="disabled">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label for="name" class="layui-form-label">
                        	操作时间
                    </label>
                    <div class="layui-input-inline" style="width: 65%">
                        <input type="text" value="${record.optTime?string("yyyy-MM-dd HH:mm:ss")}" autocomplete="off" class="layui-input" disabled="disabled">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label for="name" class="layui-form-label">
                        	请求参数
                    </label>
                    <div class="layui-input-block" style="width:500px;">
	                    <textarea class="layui-textarea" readonly="readonly">${record.requestParams}</textarea>
	                </div>
                </div>
                <div class="layui-form-item">
                    <label for="name" class="layui-form-label">
                        	操作描述
                    </label>
                    <div class="layui-input-block" style="width:500px;">
	                    <textarea class="layui-textarea" readonly="readonly">${record.optDesc}</textarea>
	                </div>
                </div>
                <div style="height: 60px"></div>
            </div>

        </div>

    </form>
</div>
</body>

</html>

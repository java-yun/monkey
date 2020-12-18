<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>用户修改默认密码</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi"/>
    <link rel="stylesheet" href="${re.contextPath}/plugin/layui/css/layui.css">
    <script type="text/javascript" src="${re.contextPath}/plugin/jquery/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${re.contextPath}/plugin/layui/layui.all.js" charset="utf-8"></script>
</head>

<body>
<div class="layui-container">
    <div class="layui-row">
        <div class="layui-col-sm-offset3 layui-col-sm4">
            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
                <legend>用户${user.username}修改系统默认登陆密码</legend>
            </fieldset>
            <form class="layui-form">
                <div class="layui-form-item">
                    <label class="layui-form-label">原密码</label>
                    <div class="layui-input-block">
                        <input type="hidden" name="id" value="${user.id}">
                        <input type="password" id="pass" name="pass" lay-verify="pass" placeholder="请输入原密码" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">新密码</label>
                    <div class="layui-input-block">
                        <input type="password" id="newPass" name="newPwd" lay-verify="newPass" placeholder="请输入新密码" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">确认密码</label>
                    <div class="layui-input-block">
                        <input type="password" id="reNewPass" name="reNewPass" lay-verify="reNewPass" placeholder="请输入原密码" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item" align="center">
                    <button class="layui-btn layui-btn-normal" lay-filter="add" lay-submit="">
                        修改
                    </button>
                    <button class="layui-btn layui-btn-primary" id="close">
                        取消
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>
<script>
    layui.use(['form', 'layer'], function () {
        const form = layui.form, $ = layui.jquery;
        //自定义验证规则
        form.verify({
            pass: function (value) {
                if (value.trim() == "") {
                    return "密码不能为空";
                }
            }
            , newPass: [/(.+){6,12}$/, '密码必须6到12位']
            , reNewPass: function (value) {
                if ($('#newPass').val() != $('#reNewPass').val()) {
                    return '两次密码不一致';
                }
            }
        });

        $('#close').click(function () {
            window.location.href = "${re.contextPath}/logout";
            return false;
        });
        //监听提交
        form.on('submit(add)', function (data) {
            $.ajax({
                url: 'rePass',
                type: 'post',
                data: data.field,
                async: false, dataType: "json", traditional: true,
                success: function (json) {
                    if(json.code == '000000'){
                        window.top.layer.msg(json.msg, {icon: 6});
                        window.location.href = "${re.contextPath}/login";
                    }else{
                        window.top.layer.msg(json.msg, {icon: 6});
                    }

                }, error: function () {
                    window.top.layer.msg('请求失败', {icon: 5});
                }
            });
            return false;
        });
        form.render();
    });
</script>
</body>

</html>

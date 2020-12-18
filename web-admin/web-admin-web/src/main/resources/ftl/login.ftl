<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <title>绯石之心</title>
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi"/>
    <meta http-equiv="Cache-Control" content="no-siteapp"/>

    <link rel="stylesheet" href="${re.contextPath}/plugin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${re.contextPath}/plugin/x-admin/css/font.css">
    <link rel="stylesheet" href="${re.contextPath}/plugin/x-admin/css/xadmin.css">
    <script type="text/javascript" src="${re.contextPath}/plugin/layui/layui.all.js"></script>
    <script type="text/javascript" src="${re.contextPath}/plugin/jquery/jquery-3.2.1.min.js"></script>

</head>
<body class="login-bg">

<div class="login">
    <div class="message">绯石之心数据后台</div>
    <div id="darkbannerwrap"></div>

    <form method="post" action="${re.contextPath}/doLogin" class="layui-form">
        <input name="username" placeholder="用户名" id="username" autocomplete="off" type="text" lay-verify="username" class="layui-input">
        <hr class="hr15">
        <input name="password" lay-verify="password" placeholder="密码" autocomplete="off" type="password" class="layui-input">

        <hr class="hr15">
        
        <div class="layui-inline">
            <div class="layui-input-inline">
                <input type="text" name="smsCode" style="width:190px;height:50px;" placeholder="验证码" autocomplete="off" lay-verify="smsCode" class="layui-input">
            </div>
            <div class="layui-input-inline">
                <input class="layui-btn" id="sendSms" value="发送验证码" style="width:140px;height:50px;" readonly>
            </div>
        </div>

        <hr class="hr15">
            <input value="登录" lay-submit lay-filter="login" style="width:100%;" type="submit">
        <hr class="hr20">
    </form>
</div>


<script>
    var layer;
    $(function () {
        layui.use(['form', 'layer'], function () {
            var form = layui.form;
            form.verify({
                username: function (v) {
                    if (v.trim() == '') {
                        return "用户名不能为空";
                    }
                }
                , password: function (v) {
                    if (v.trim() == '') {
                        return "密码不能为空";
                    }
                }
            });
            form.render();
        });
        layer = layui.layer;
        var msg = '${message}';
        if (msg.trim() != "") {
            layer.msg(msg, {icon: 5, anim: 6, offset: 't'});
        }

    })

    // 发送验证码
    $("#sendSms").click(function () {
        var _this = $(this);
        $.ajax({
            url: 'sendSms'
            , data: {
                username: $("#username").val()
            }
            , type: 'post'
            , success: function (data) {
                if (data.code == '000000') {
                    window.top.layer.msg(data.msg, {icon: 1});
                } else {
                    window.top.layer.msg(data.msg, {icon: 2});
                }
            }
        });
    })


    if (window != top)
        top.location.href = location.href;
</script>


<!-- 底部结束 -->
</body>
</html>

<#--Created by IntelliJ IDEA.
User: Administrator
Date: 2017/12/7
Time: 12:40
To change this template use File | Settings | File Templates.-->

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>重置密码</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi"/>
    <link rel="stylesheet" href="${re.contextPath}/plugin/layui/css/layui.css">
    <link rel="stylesheet" href="${re.contextPath}/plugin/x-admin/css/xadmin.css">
    <script type="text/javascript" src="${re.contextPath}/plugin/jquery/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${re.contextPath}/plugin/layui/layui.all.js" charset="utf-8"></script>
</head>

<body>
<div class="x-body">
    <form class="layui-form layui-form-pane" style="margin-left: 20px;">
        <div style="width:100%;height:300px;overflow: auto;">
            <div class="layui-form-item">
                <fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
                    <legend style="font-size:16px;">修改账户：${user.username}</legend>
                    <input type="hidden" name="id" value="${user.id}">
                </fieldset>
            </div>
            <div class="layui-form-item">
                <label for="newPass" class="layui-form-label">
                    <span class="x-red">*</span>新密码
                </label>
                <div class="layui-input-inline">
                    <input type="password" id="newPass" name="newPwd" lay-verify="newPass" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label for="reNewPass" class="layui-form-label">
                    <span class="x-red">*</span>确认密码
                </label>
                <div class="layui-input-inline">
                    <input type="password" id="reNewPass" name="reNewPass" lay-verify="reNewPass" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div style="height: 60px"></div>
        </div>
        <div style="width: 100%;height: 55px;background-color: white;border-top:1px solid #e6e6e6;
  position: fixed;bottom: 1px;margin-left:-20px;">
            <div class="layui-form-item" style=" float: right;margin-right: 30px;margin-top: 8px">

                <button class="layui-btn layui-btn-normal" lay-filter="add" lay-submit="">
                    修改
                </button>
                <button class="layui-btn layui-btn-primary" id="close">
                    取消
                </button>
            </div>
        </div>
    </form>
</div>
<script>
    const type = "${type}";
    layui.use(['form', 'layer'], function () {
        const $ = layui.jquery;
        const form = layui.form;
        //自定义验证规则
        form.verify({
            newPass: [/(.+){6,12}$/, '密码必须6到12位']
            , reNewPass: function (value) {
                if ($('#newPass').val() !== $('#reNewPass').val()) {
                    return '两次密码不一致';
                }
            }
        });

        $('#close').click(function () {
            const index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        });
        //监听提交
        form.on('submit(add)', function (data) {
            $.ajax({
                url: 'rePass',
                type: 'post',
                data: data.field,
                async: false, dataType: "json", traditional: true,
                success: function (json) {
                    if (json != null && json.code === '000000') {
                        const index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index);
                        if (type !== 'main') {
                            window.parent.layui.table.reload('userList');
                        }
                        window.top.layer.msg(json.msg, {icon: 6});
                    } else {
                        window.top.layer.msg(json.msg, {icon: 5});
                    }
                }, error: function () {
                    const index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
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

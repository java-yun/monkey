<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>用户管理</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi"/>
    <link rel="stylesheet" href="${re.contextPath}/plugin/layui/css/layui.css">
    <link rel="stylesheet" href="${re.contextPath}/plugin/x-admin/css/xadmin.css">
    <script type="text/javascript" src="${re.contextPath}/plugin/jquery/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${re.contextPath}/plugin/layui/layui.all.js" charset="utf-8"></script>
    <script type="text/javascript" src="${re.contextPath}/plugin/tools/tool.js"></script>
    <script type="text/javascript" src="${re.contextPath}/plugin/js/common.js " charset="utf-8"></script>
</head>

<body>
<div class="x-body">
    <form class="layui-form layui-form-pane" style="margin-left: 20px;">
        <div style="width:100%;height:90%;overflow: auto;">
            <div class="layui-form-item">
                <fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
                    <legend style="font-size:16px;">头像上传</legend>
                </fieldset>
                <div class="layui-input-inline">
                    <div class="layui-upload-drag" style="margin-left:10%;" id="test10">
                        <i style="font-size:30px;" class="layui-icon"></i>
                        <p style="font-size: 10px">点击上传，或将文件拖拽到此处</p>
                    </div>
                </div>
                <div class="layui-input-inline">

                    <div id="demo2" style="margin-top: 20px;margin-left: 50px">
                        <img src="${re.contextPath}/plugin/x-admin/images/bg.png" width="100px" height="100px" class="layui-upload-img layui-circle">
                    </div>

                    <input id="photo" name="photo" type="hidden">

                </div>
            </div>
            <div class="layui-form-item">
                <fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
                    <legend style="font-size:16px;">基础信息</legend>
                </fieldset>
            </div>
            <div class="layui-form-item">
                <label for="uname" class="layui-form-label">
                    <span class="x-red">* </span>用户名
                </label>
                <div class="layui-input-inline">
                    <input type="text" id="uname" name="username" lay-verify="username" autocomplete="off" class="layui-input">
                </div>
                <div id="ms" class="layui-form-mid layui-word-aux">
                    <span class="x-red">* </span><span id="ums">将会成为您唯一的登入名</span>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label for="realName" class="layui-form-label">
                        <span class="x-red">* </span>真实姓名
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" id="realName" name="realName" lay-verify="realName" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label for="age" class="layui-form-label">
                        <span class="x-red">* </span>年龄
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" id="age" name="age" lay-verify="number" autocomplete="off" class="layui-input">
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <label for="mobile" class="layui-form-label">
                    <span class="x-red">* </span>手机号
                </label>
                <div class="layui-input-block">
                    <input type="text" id="mobile" value="" style="width: 93%" name="mobile" lay-verify="mobile" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label for="email" class="layui-form-label">
                    邮箱
                </label>
                <div class="layui-input-block">
                    <input type="email" id="email" style="width: 93%" name="email" lay-verify="email" autocomplete="off" class="layui-input">
                </div>
            </div>

            <div class="layui-form-item">
                <label for="mobile" class="layui-form-label">
                    <span class="x-red">* </span>角色
                </label>
                <div class="layui-input-block">
                    <#list boxJson as json>
                        <input type="checkbox" name="role" title="${json.name}" lay-filter="check" value="${json.id}">
                    </#list>
                </div>
            </div>

            <input type="hidden" id="isLegalUserName"/>

        </div>
        <div style="width: 100%;height: 55px;background-color: white;border-top:1px solid #e6e6e6;
  position: fixed;bottom: 1px;margin-left:-20px;">
            <div class="layui-form-item" style=" float: right;margin-right: 30px;margin-top: 8px">

                <button class="layui-btn layui-btn-normal" lay-filter="add" lay-submit="">
                    增加
                </button>
                <button class="layui-btn layui-btn-primary" id="close">
                    取消
                </button>
            </div>
        </div>
    </form>
</div>
<script>
    let flag, msg;
    $(function () {
        $('#uname').on("blur", function () {
            const uname = $('#uname').val();
            const unameRegExp = /^[a-zA-Z]{1}[a-zA-Z0-9]{3,11}$/;
            if (!unameRegExp.test(uname)) {
                $('#ms').append("<span style='color: red;'>4-12位字母和数字(字母开头)</span>");
                $('#isLegalUserName').val("false");
                return;
            }
            if (uname !== '') {
                $.ajax({
                    url: 'checkUser?uname=' + uname, async: false, type: 'get', success: function (data) {
                        $('#ms').find('span').remove();
                        if (data.code === '000000' && data.data) {
                            $('#ms').append("<span style='color: green;'>用户名可用</span>");
                            $('#isLegalUserName').val("true");
                        } else if (data.code === '000000' && !data.data) {
                            $('#ms').append("<span style='color: red;'>用户名存在</span>");
                            $('#isLegalUserName').val("false");
                        } else {
                            msg = data.msg;
                            $('#ms').append("<span style='color: red;'>" + data.msg + "</span>");
                            $('#isLegalUserName').val("false");
                        }
                    }, beforeSend: function () {
                        $('#ms').find('span').remove();
                        $('#ms').append("<span>验证ing</span>");
                    }
                });
            }
        });

    });

    let type;
    layui.use(['form', 'layer', 'upload'], function () {
        const $ = layui.jquery;
        const form = layui.form, layer = layui.layer, upload = layui.upload;

        if ('${currentUser.type}' == 2) {
            $("#usertype").hide();
            $("#cpdiv").hide();
        }

        upload.render({
            elem: '#test10'
            , url: getCtxPath() + '/file/upload?v=' + new Date().getTime()
            , before: function (obj) {
                //预读，不支持ie8
                obj.preview(function (index, file, result) {
                    $('#demo2').find('img').remove();
                    $('#demo2').append('<img src="' + result + '" alt="' + file.name + '" width="100px" height="100px" class="layui-upload-img layui-circle">');
                });
            }, done: function (res) {
                if (res.code !== '000000') {
                    layer.msg(res.msg, {icon: 5, anim: 6});
                } else {
                    $("#photo").val(res.data);
                    console.info($('#photo').val());
                }
            }
        });

        //自定义验证规则
        form.verify({
            username: function (value) {
                const unameRegExp = /^[a-zA-Z][a-zA-Z0-9]{3,11}$/;
                if (!unameRegExp.test(value)) {
                    return '4-12位字母和数字(字母开头)';
                }
                if ($('#isLegalUserName').val() === 'false') {
                    return '用户名不可用';
                }
            }
            , password: [/(.+){6,12}$/, '密码必须6到12位']

            , repass: function (value) {
                if ($('#password').val() !== $('#l_repass').val()) {
                    return '两次密码不一致';
                }
            }
            , email: function (value) {
                if (value !== "") {
                    if (!/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/.test(value)) {
                        return "邮箱格式不正确";
                    }
                }
            }
            , mobile: function (value) {
                if (value.trim() === "") {
                    return "手机号不能为空"
                }
                if (value !== "") {
                    if (!/(^1[3|4|5|7|8]\d{9}$)|(^09\d{8}$)/.test(value)) {
                        return "手机号格式不正确";
                    }
                }
            }
        });

        $('#close').click(function () {
            const index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        });

        form.on('radio(userType)', function (data) {
            type = data.value;
            if (data.value === 1) {
                $("#cpdiv").hide();
            } else if (data.value === 2) {
                $("#cpdiv").show();
            }
        });

        //监听提交
        form.on('submit(add)', function (data) {
            const r = document.getElementsByName("role");
            const role = [];
            for (let i = 0; i < r.length; i++) {
                if (r[i].checked) {
                    console.info(r[i].value);
                    role.push(r[i].value);
                }
            }
            data.field.role = role;

            if (type === 1) {
                data.field.cpId = "";
            }
            layerAjax('addUser', data.field, 'dataList');
            return false;
        });
        form.render();
    });

</script>
</body>

</html>

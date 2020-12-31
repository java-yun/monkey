<#--Created by IntelliJ IDEA.
User: Administrator
Date: 2017/12/27
Time: 12:40
To change this template use File | Settings | File Templates.-->

<!DOCTYPE html>
<html lang="zh">

<head>
    <meta charset="UTF-8">
    <title>菜单管理</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi"/>
    <link rel="stylesheet" href="${re.contextPath}/plugin/layui/css/layui.css">
    <script type="text/javascript" src="${re.contextPath}/plugin/jquery/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${re.contextPath}/plugin/layui/layui.all.js" charset="utf-8"></script>
</head>

<body>
<div class="x-body">
    <form class="layui-form layui-form-pane" style="margin-left: 20px;">
        <div style="width:100%;height:500px;overflow: auto;">
            <div class="layui-form-item">
                <fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
                    <legend style="font-size:16px;">基础信息</legend>
                </fieldset>
            </div>
            <div style="margin-left:25%">
                <div class="layui-form-item">
                    <label for="menuType" class="layui-form-label">
                        <span class="x-red">*</span>类型
                    </label>
                    <div class="layui-input-block" style="width:190px;">
                        <select name="menuType" id="menuType" lay-verify="menuType" lay-filter="menuType">
                            <option value=""></option>
                            <option value="0">菜单</option>
                            <option value="1">按钮</option>
                        </select>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">顶级菜单</label>
                    <div class="layui-input-block">
                        <input type="radio" name="isTop" lay-filter="isTopFilter" value="0" title="否" checked="">
                        <input type="radio" name="isTop" lay-filter="isTopFilter" value="1" title="是">
                    </div>
                </div>

                <div class="layui-form-item" id="pDiv">
                    <label for="pName" class="layui-form-label">
                        父级菜单
                    </label>
                    <div class="layui-input-inline">
                        <input type="hidden" name="pCode" id="pCode">
                        <input type="hidden" name="level" id="level">
                        <input type="text" id="pName" onclick="showTree();" lay-verify="pName" class="layui-input">
                    </div>
                    <div id="treeNode" style="display:none; position: absolute;z-index:1000;background-color: white;">
                        <div id="parentTree" class="demo-tree-more"></div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label for="name" class="layui-form-label">
                        <span class="x-red">*</span>名称
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" id="name" name="name" lay-verify="name" autocomplete="off" class="layui-input">
                    </div>
                    <div id="ms" class="layui-form-mid layui-word-aux">
                        <span class="x-red">*</span><span id="ums">必须填写</span>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label for="url" class="layui-form-label">
                        url
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" id="url" name="url" lay-verify="url" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label for="permission" class="layui-form-label">
                        <span class="x-red">*</span>权限
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" id="permission" name="permission" lay-verify="permission" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label for="icon" class="layui-form-label">
                        <span class="x-red">*</span>图标
                    </label>
                    <div class="layui-input-inline">
                        <div style="margin-left: 20px;margin-top:5px">
                            <ul>
                                <li style="display: inline-block;width: 50px;" id="menu-icon"><i class="layui-icon" id="icon" style="font-size: 25px;"></i></li>
                                <li style="display: inline-block;"><i class="layui-btn layui-btn-primary layui-btn-sm" id="select_icon">选择图标</i></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label for="orderNum" class="layui-form-label">
                        <span class="x-red">*</span>序号
                    </label>
                    <div class="layui-input-inline">
                        <input type="orderNum" id="orderNum" name="orderNum" lay-verify="orderNum" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div style="height: 60px"></div>
            </div>
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
    layui.use(['form', 'layer', 'tree'], function () {
        const $ = layui.$;
        const form = layui.form, layer = layui.layer, tree = layui.tree;
        tree.render({
            elem: '#parentTree',
            data: ${menus},
            click: function (node) {
                if (node.data.menuType === '1') {
                    layer.msg('请勿选择按钮', {icon: 5, anim: 6});
                    return false;
                }
                $('#pCode').val(node.data.code);
                $('#pName').val(node.data.name);
                $('#level').val(node.data.level);
            }
        });

        $('#select_icon').click(function () {
            parent.layer.open({
                id: 'icon',
                type: 2,
                area: ['800px', '600px'],
                shade: 0.4,
                zIndex: layer.zIndex,
                title: '图标',
                content: 'icon'
            });
        });
        //自定义验证规则
        const type = $('#menuType');
        form.verify({

            menuType: function (v) {
                console.info(v === '')
                if (v === '') {
                    return '类型不能为空';
                }
            },
            pName: function (v) {
                const isTop = $("input[name='isTop']:checked").val();
                if (isTop === '0' && v.trim() === '') {
                    return '父菜单不能为空';
                }
            },
            name: function (v) {
                if (v.trim() === '') {
                    return '名称不能为空';
                }
            },
            url: function (v) {
                const isTop = $("input[name='isTop']:checked").val();
                if (type.val() === '1') {
                    $('#url').val('');
                }
                if (isTop === '0' && type.val() === '0' && v.trim() === '') {

                    return 'url不能为空';
                }
            },
            permission: function (v) {
                const isTop = $("input[name='isTop']:checked").val();
                if (isTop === '0' && (type.val() === '1' || type.val() === '0') && v.trim() === '') {
                    return '权限不能为空';
                }
            },
            orderNum: [/^[0-9]*[1-9][0-9]*$/, '请填写序号(正整数)']
        });

        form.on('select(menuType)', function (data) {
            if (data.value === '1') {//按钮
                dOs('url', true);
                //判断radio的值
                const isTop = $("input[name='isTop']:checked").val();
                if (isTop === '0') {
                    dOs('pName', false);
                }
                $("input[name='isTop'][value='1']").attr("disabled", "disabled");
            } else if (data.value === '0') {
                $("input[name='isTop'][value='1']").removeAttr('disabled');
                dOs('url', false);
                dOs('pName', false);
            }
            form.render();
        });

        form.on('radio(isTopFilter)', function (data) {
            if (data.value === '1') {
                dOs('pName', true);
                dOs('url', true);
                dOs('permission', true);
            } else {
                dOs('pName', false);
                dOs('url', false);
                dOs('permission', false);
            }
        });


        /**
         * id :元素id
         * flag true:禁止输入，false 允许输入
         */
        function dOs(id, flag) {
            const $id = $("#" + id);
            if (flag) {
                $id.val('');
                $id.attr('disabled', 'disabled').css('background', '#e6e6e6');
            } else
                $id.removeAttr('disabled').css('background', 'white')
        }

        $('#close').click(function () {
            const index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
            return false;
        });


        //监听提交
        form.on('submit(add)', function (data) {
            data.menuType = $("#menuType option:selected").val();
            data.isTop = $("input[name='isTop']:checked").val();

            console.log("获取到menuType值为:" + data.menuType);
            data.field['icon'] = $('#icon').text();
            $.ajax({
                url: 'addMenu',
                type: 'post',
                data: data.field,
                async: false, dataType: "json", traditional: true,
                success: function (data) {
                    console.info(data.msg);
                    if(data.code === "000000") {
                        const index = parent.layer.getFrameIndex(window.name);
                        window.top.layer.msg(data.msg, {icon: 6, anim: 2});
                        parent.layer.close(index);
                        parent.location.replace(parent.location.href);
                    } else {
                        window.top.layer.msg(data.msg, {icon: 5, anim: 2});
                    }
                }, error: function () {
                    const index = parent.layer.getFrameIndex(window.name);
                    window.top.layer.msg('请求失败', {icon: 5, anim: 2});
                    parent.layer.close(index);
                }
            });
            return false;
        });
        form.render();
    });

    function showTree() {
        const p = $('#pName');
        const cityObj = p;
        const cityOffset = p.offset();
        const width = p.css('width');
        $('#treeNode').css({
            left: cityOffset.left + 'px',
            top: cityOffset.top + cityObj.outerHeight() + 'px',
            width: width,
            border: '1px solid #e6e6e6'
        }).slideDown('fast');
        $('body').bind('mousedown', onBodyDown);
        $('#treeNode').css('display', 'inline');
    }

    function hideMenu() {
        $('#treeNode').fadeOut('fast');
        $('body').unbind('blur', onBodyDown);
    }

    function onBodyDown(event) {
        if (!(event.target.id === 'treeNode' || $(event.target).parents('#treeNode').length > 0)) {
            hideMenu();
        }
    }

</script>
</body>

</html>

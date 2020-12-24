/**by zxm 154040976@qq.com*/
/**
 * 统一处理js方法
 */
/**框架*/
let message;
layui.config({
  base: getCtxPath() + '/plugin/build/js/',
  version: '1.0.1'
}).use(['app', 'message', 'element'], function() {
    const switchSkin = function(value) {
      var _target = $('link[kit-skin]')[0];
      _target.href = _target.href.substring(0, _target.href.lastIndexOf('/') + 1) + value + _target.href.substring(_target.href.lastIndexOf('.'));
      setSkin(value);
    };
    const app = layui.app,
        $ = layui.jquery,
        element = layui.element,
        layer = layui.layer;
    //将message设置为全局以便子页面调用
  message = layui.message;
  //主入口
  app.set({
    type: 'iframe'
  }).init();
  $('dl.skin > dd').on('click', function() {
      const $that = $(this);
      const skin = $that.children('a').data('skin');
      switchSkin(skin);
  });
    const setSkin = function (value) {
            layui.data('kit_skin', {
                key: 'skin',
                value: value
            });
        },
        getSkinName = function () {
            return layui.data('kit_skin').skin;
        },
        initSkin = function () {
            var skin = getSkinName();
            switchSkin(skin === undefined ? 'default' : skin);
        }();


    $('.change-mode').on('click', function () {
        var code = $(this).attr("data-module-id");
        console.log("点击事件获取到code值为:" + code);
        $.ajax({
            url: getCtxPath() + "/menu/changeMode?code=" + code,
            type: "GET",
            success: function (data) {
                var buffer = new StringBuffer();
                if(data.code === '000000') {
                    if(data.data != null && data.data.length > 0) {
                        for (i in data.data) {
                            replaceHtml(data.data[i], buffer);
                        }
                    }
                    $(".layui-nav-tree .layui-side-scroll .layui-nav-tree").html(buffer.toString());
                    app.set({
                        type: 'iframe'
                    }).init();
                    element.render('nav');
                } else {
                    layer.msg(data.msg);
                }
            },
            error: function () {

            }
        });
    });

    function replaceHtml(obj, buf) {
        if (obj.children != null && obj.children.length > 0 ) {
            buf.append('<li class=\"layui-nav-item\">');
            buf.append('<a class="" href="javascript:;"><i aria-hidden="true" class="layui-icon">' + (obj.icon == null ?'':obj.icon) + '</i><span>&nbsp;' + obj.name + '</span></a>');
            buf.append('<dl class="layui-nav-child">');
            replaceHtml(obj.children, buf);
            buf.append('</dl>');
            buf.append('</li>');
        } else if(obj.length > 0){
            for (i in obj) {
                buf.append('<dd class="layui-nav-item">');
                buf.append('<a href="javascript:;" kit-target data-options="{url:\'' + getCtxProjectPath() + '/' + obj[i].url + '\',icon:\'' + (obj[i].icon == null ?'':obj[i].icon) + '\',title:\'' + obj[i].name + '\',id:\'' + obj[i].id +'\'}">');
                buf.append('<i class="layui-icon"> ' + (obj[i].icon == null ?'':obj[i].icon) + '</i><span>&nbsp;' +  obj[i].name + '</span></a>');
                buf.append('</dd>');
            }
        } else {
            buf.append('<dd class="layui-nav-item">');
            buf.append('<a href="javascript:;" kit-target data-options="{url:\'' + getCtxProjectPath() + '/' + obj.url + '\',icon:\'' + (obj.icon == null ?'':obj.icon) + '\',title:\'' + obj.name + '\',id:\'' + obj.id +'\'}">');
            buf.append('<i class="layui-icon"> ' + (obj.icon == null ?'':obj.icon) + '</i><span>&nbsp;' +  obj.name + '</span></a>');
            buf.append('</dd>');
        }
    }
    //基本信息
    $('.user-detail').on('click', function () {
        var id = $(this).attr("data-module-id");
        main_detail('基本信息', 'user/toUpdateUser?id=' + id, 900, 600);
    });
     //修改密码  
    $('.user-uppw').on('click', function () {
        var id = $(this).attr("data-module-id");
        main_rePwd('修改密码', 'user/goRePass?type=main&id=' + id, 500, 350);
    });
    function main_detail(title, url, w, h) {
        if (title == null || title === '') {
            title = false;
        }
        ;
        if (url == null || url === '') {
            url = "error/404";
        }
        ;
        if (w == null || w === '') {
            w = ($(window).width() * 0.9);
        }
        ;
        if (h == null || h === '') {
            h = ($(window).height() - 50);
        }
        ;
        layer.open({
            id: 'main-user-detail',
            type: 2,
            area: [w + 'px', h + 'px'],
            fix: false,
            maxmin: true,
            shadeClose: true,
            shade: 0.4,
            title: title,
            content: url + '&detail=true',
            // btn:['关闭']
        });
    }

    function main_rePwd(title, url, w, h) {
        if (title == null || title == '') {
            title = false;
        }
        ;
        if (url == null || url == '') {
            url = "404.html";
        }
        ;
        if (w == null || w == '') {
            w = ($(window).width() * 0.9);
        }
        ;
        if (h == null || h == '') {
            h = ($(window).height() - 50);
        }
        ;
        layer.open({
            id: 'main_user-rePwd',
            type: 2,
            area: [w + 'px', h + 'px'],
            fix: false,
            maxmin: true,
            shadeClose: true,
            shade: 0.4,
            title: title,
            content: url,
        });
    }
        
    function StringBuffer() {
        this.__strings__ = new Array;
    }

    StringBuffer.prototype.append = function (str) {
        this.__strings__.push(str);
    };

    StringBuffer.prototype.toString = function () {
        return this.__strings__.join("");
    };
});
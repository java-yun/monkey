layui.config({
	base : '../../plugin/js/'
}).use(['layer', 'form'], function () {
	var $ = layui.jquery, 
	layerTips = parent.layer === undefined ? layui.layer : parent.layer, // 获取父窗口的layer对象
    layer = layui.layer, // 获取当前窗口的layer对象
    form = layui.form;
	
	initBlackTypeSelect();
	
	//初始化黑名单类型select
	function initBlackTypeSelect() {
		$.ajax({
            url:getCtxPath() + "/userBlack/getBlackTypeList?v=" + new Date().getTime(),
            method : "get",
            dataType : "json",
            success: function (data) {
            	if (data.code == 0) {
            		$.each(data.data, function(index, item) {
        				$("#blackType").append("<option value='" + item.value + "'>" + item.desc + "</option>");
                	});
            		form.render();
				} else {
					window.top.layer.msg('黑名单类型初始化失败',{icon:5,anim:2});
				}
            },
            error: function () {
            	window.layer.msg('请求失败', {
					icon : 2
				});
            }
        });
	}
	
	$('#close').click(function () {
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
        return false;
    });
	
	//监听提交
    form.on('submit(add)', function (data) {
        $.ajax({
            url: 'add',
            type: 'post',
            data: data.field,
            async: false, 
            dataType: "json", 
            traditional: true,
            success: function (data) {
            	if (data.code == 0) {
					var index = parent.layer.getFrameIndex(window.name);
					window.top.layer.msg(data.msg, {
						icon : 6
					});
					parent.layer.close(index);
					parent.layui.table.reload('user_black_list_tb');
				} else {
					window.layer.msg(data.msg, {
						icon : 2
					});
				}
            }, 
            error: function () {
                var index = parent.layer.getFrameIndex(window.name);
                window.top.layer.msg('请求失败', {icon: 2});
                parent.layer.close(index);
            }
        });
        return false;
    });
    form.render();
	
});
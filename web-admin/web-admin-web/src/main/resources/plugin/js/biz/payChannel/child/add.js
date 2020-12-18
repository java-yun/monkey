layui.config({
	base : '../../plugin/js/'
}).use(['layer', 'form' ], function () {
	var $ = layui.jquery, 
	layerTips = parent.layer === undefined ? layui.layer : parent.layer, // 获取父窗口的layer对象
    layer = layui.layer,// 获取当前窗口的layer对象
    form = layui.form;
	
	debugger;
    form.render();
	
	$('#close').click(function () {
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
        return false;
    });
	
	//监听提交
    form.on('submit(add)', function (data) {
    	data.field.appName = $("[name='appId']").find("option:selected").text();
        $.ajax({
            url: 'addChild',
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
					parent.layui.table.reload('pay_channel_child_list');
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
	
});
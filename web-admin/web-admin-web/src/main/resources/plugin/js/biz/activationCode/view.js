layui.config({
	base : '../../plugin/js/'
}).use(['layer', 'form'], function () {
	var $ = layui.jquery, 
	layerTips = parent.layer === undefined ? layui.layer : parent.layer, // 获取父窗口的layer对象
    layer = layui.layer, // 获取当前窗口的layer对象
    form = layui.form;
	
	initActivationCodeSelect();
	
	//初始化黑名单类型select
	function initActivationCodeSelect() {
		$.ajax({
			url:getCtxPath() + "/activationCode/getActivationCodeState?v=" + new Date().getTime(),
            method : "get",
            dataType : "json",
            success: function (data) {
            	debugger;
            	if (data.code == 0) {
            		$.each(data.data, function(index, item) {
            			if ($("#stateHid").val() == item.value) {
            				$("#state").append("<option value='" + item.value + "' selected='selected'>" + item.desc + "</option>");
						} else {
							$("#state").append("<option value='" + item.value + "'>" + item.desc + "</option>");
						}
                	});
            		form.render();
				} else {
					window.top.layer.msg('激活码状态查询失败',{icon:5,anim:2});
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
	
    form.render();
	
});
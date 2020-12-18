layui.config({
	base : '../../plugin/js/'
}).use(['layer', 'form', 'upload' ], function () {
	var $ = layui.jquery, 
	layerTips = parent.layer === undefined ? layui.layer : parent.layer, // 获取父窗口的layer对象
    layer = layui.layer,// 获取当前窗口的layer对象
    upload = layui.upload,
    form = layui.form;
	
	upload.render({
	      elem: '#test10',
	      url: getCtxPath() + '/file/upload',
	      before: function(obj){
	        //预读，不支持ie8
	        obj.preview(function(index, file, result){
	        	$('#demo2').find('img').remove();
	        	$('#demo2').append('<img src="'+ result +'" alt="'+ file.name +'" width="100px" height="100px" class="layui-upload-img layui-circle">');
	        });
	      },
	      done: function(res){
	    	  debugger;
	    	  if(res.code == "000000"){
	    		  $("#icon").val(res.data);
	    		  console.info($('#icon').val());
	    	  }else{
	    		  layer.msg(res.msg,{icon: 5,anim: 6});
	    	  }
	      }
	 });
	
	
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
					parent.layui.table.reload('pay_channel_list');
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
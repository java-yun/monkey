layui.config({
	base : '../../plugin/js/'
}).use(['layer', 'form', 'element', 'table'], function () {
	var $ = layui.jquery, 
	layerTips = parent.layer === undefined ? layui.layer : parent.layer, // 获取父窗口的layer对象
    layer = layui.layer, // 获取当前窗口的layer对象
    form = layui.form,
    element = layui.element,
    table = layui.table;
	
	//查询按钮绑定事件
    $('#search').on('click', function () {
    	getList();
    });
    
    form.render();
    
    //刷新页面
    $('#refresh').on('click', function () {
    	$("#appId").val("");
    	$("#sdkVersion").val("");
    	$("#osPlatform").val("");
    	$("#state").val("");
		form.render();
		getList();
    });
    
    /** 新增 start */
    $('#payChannelChildAdd').on('click', function () {
    	var payChannleId = $("#payChannelId").val();
	    layer.open({
	        type: 2,
	        title: '新增',// 设置false表示不显示
	        closeBtn: 1, // 0：不显示关闭按钮
	        shade: 0.4, // 遮罩透明度
	        area: ['800px', '580px'],
	        skin: 'layui-layer-rim', // 加上边框
	        fixed: false, // 不固定
	        maxmin: true, // 允许屏幕最小化
	        anim: 2,
	        content: ['toChildAdd?payChannelId=' + payChannleId] // iframe的url，no代表不显示滚动条
	    });
    });
    /** 新增 end */
	
	//加载数据
	getList();
	
	function getList() {
		var appId = $("#appId").val();
    	var sdkVersion = $("#sdkVersion").val();
    	var osPlatform = $("#osPlatform").val();
    	var state = $("#state").val();
    	var payChannelId = $("#payChannelId").val();
		table.render({
	   		 elem: '#payChannelChildList', 
	   		 url: getCtxPath() + "/payChannel/listChild?v=" + new Date().getTime(),
	   		 where: {
	   			appId : appId,
	   			sdkVersion : sdkVersion,
	   			osPlatform : osPlatform,
	   			payChannelId : payChannelId,
	   			state : state
		     },
	         method: 'get', 
	         loading: true,
	         id: 'pay_channel_child_list',
	         page: {
	           layout: ['count', 'prev', 'page', 'next', 'limit', 'skip'],
	           first: '首页',
	           last: '尾页'
	         },
	         limit: 20,
	         limits: [20, 50, 100],
	         height: 'full-185',
	         cols: [[
	        	 {type: 'numbers', align: 'center', title: '序号'},
	        	 {field: 'primaryName', align: 'center', title: '主体名称', width: '110'},
	        	 {field: 'subName', align: 'center', title: '支付方式名称', width: '130'},
	        	 {field: 'code', align: 'center', title: '支付方式编码', width: '130'},
	        	 {field: 'appName', align: 'center', title: '游戏名称', width: '160'},
	        	 {field: 'type', align: 'center', title: '类型', width: '60'},
	        	 {field: 'sdkVersion', align: 'center', title: 'SDK版本', width: '100'},
	        	 {field: 'osPlatform', align: 'center', title: '客户端类型', width: '120'},
	        	 {field: 'state', align: 'center', title: '状态', width: '100', templet: '<div>{{ layui.laytpl.showState(d.id, d.state, d.payChannelId) }}</div>'},
	        	 {field: 'createMan', align: 'center', title: '创建人', width: '110'},
	        	 {field: 'createTime', align: 'center', title: '创建时间', templet: '<div>{{ layui.laytpl.toDateStringWithBlank(d.createTime,"yyyy-MM-dd HH:mm:ss") }}</div>', width: '160'},
	        	 {field: 'modifyMan', align: 'center', title: '修改人', width: '110'},
	        	 {field: 'modifyTime', align: 'center', title: '修改时间', templet: '<div>{{ layui.laytpl.toDateStringWithBlank(d.modifyTime,"yyyy-MM-dd HH:mm:ss") }}</div>', width: '160'},
	        	 {field: 'remark', align: 'center', title: '备注', width: '180'},
	        	 {title: '操作',align: 'center', width: '15%', fixed: 'right', toolbar: "#toolBar"}
	         ]],
	         request: {
	        	 pageName: 'pageIndex',
	        	 limitName: 'pageSize'
	         },
	         done: function(res, curr, count){
	        	 //如果是异步请求数据方式，res即为你接口返回的信息。curr为当前页码。count为数据总量
	        	 _data = res.list;
	   		 }
		});
		
		//监听工具条
        table.on('tool(payChannelChild)', function (obj) {
            var data = obj.data;
            if (obj.event === 'edit') {
                update(data.id, true);
            }
            if (obj.event === 'view') {
            	update(data.id, false);
            }
            if (obj.event === 'del') {
                del(data.id);
            }
        });
        
	}
	
	layui.laytpl.showState = function (id, state, payChannelId) {
		if(state == 1) {
    		return '<input name="close" type="checkbox" lay-skin="switch" lay-filter="state" data-id="'+id+'" data-state="'+state+'" data-pid="'+payChannelId+'" lay-text="启用|禁用" checked>';
    	} else if(state == 0) {
    		return '<input name="close" type="checkbox" lay-skin="switch" lay-filter="state" data-id="'+id+'" data-state="'+state+'" data-pid="'+payChannelId+'" lay-text="启用|禁用">';
    	}
	}
	
	//开关按钮
	form.on('switch(state)', function (obj) {
		var id = $(this).data("id");
		var state = $(this).data("state");
		var payChannelId = $(this).data("pid");
        if (state == 1) {
        	updateStateById(id, 0, payChannelId);
		} else {
			updateStateById(id, 1, payChannelId);
		}
    });
	
	function updateStateById(id, state, payChannelId) {
    	$.ajax({
            url : getCtxPath() + "/payChannel/updateChild?v=" + new Date().getTime(),
            method : "post",
            data : {id : id, state : state, isOnlyUpdateState : true, payChannelId : payChannelId},
            dataType : "json",
            success: function (data) {
            	if (data.code == 0) {
            		// 获取父窗口的layer对象
                    var layerTips = parent.layer === undefined ? layui.layer : parent.layer;
                    window.top.layer.msg("修改成功", {
    					icon : 6
    				});// 提示信息
				} else {
					window.layer.msg(data.msg, {
						icon : 2
					});
				}
            	getList();
            },
            error: function () {
                layer.alert("修改失败");
            }
        });
    }
	
    //跳更新或查看页面
    function update(id, isUpdate) {
    	layer.open({
	        type: 2,
	        title: '编辑',// 设置false表示不显示
	        closeBtn: 1, // 0：不显示关闭按钮
	        shade: 0.4, // 遮罩透明度
	        area: ['800px', '580px'],
	        skin: 'layui-layer-rim', // 加上边框
	        fixed: false, // 不固定
	        maxmin: true, // 允许屏幕最小化
	        anim: 2,
	        content: ['toChildViewOrUpdate?id='+id+'&isUpdate='+isUpdate] // iframe的url，no代表不显示滚动条
	    });
    }
	
	//删除
    function del(id) {
    	layer.confirm('确定要删除吗？', function (index) {
            layer.close(index);
            $.ajax({
                url:getCtxPath() + "/payChannel/deleteChild?id="+id,
                method : "get",
                dataType : "json",
                success: function (data) {
                    // 获取父窗口的layer对象
                    var layerTips = parent.layer === undefined ? layui.layer : parent.layer;
                    window.top.layer.msg("删除成功", {
    					icon : 6
    				});// 提示信息
                    //刷新数据表格
                    table.reload('pay_channel_child_list');
                },
                error: function () {
                    layer.alert("数据删除失败");
                }
            });
        });
    }
	
});
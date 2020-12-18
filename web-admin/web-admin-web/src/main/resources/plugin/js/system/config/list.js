layui.config({
	base : '../../plugin/js/'
}).use(['layer', 'form', 'element', 'laydate', 'table'], function () {
	var $ = layui.jquery, 
	layerTips = parent.layer === undefined ? layui.layer : parent.layer, // 获取父窗口的layer对象
    layer = layui.layer, // 获取当前窗口的layer对象
    form = layui.form, 
    laydate = layui.laydate, 
    element = layui.element,
    table = layui.table;
	
	//查询按钮绑定事件
    $('#search').on('click', function () {
    	getList();
    });
    
    //刷新页面
    $('#refresh').on('click', function () {
    	$("#configName").val("");
    	$("#code").val("");
		form.render();
		getList();
    });
    
    /** 新增 start */
    $('#sysConfigAdd').on('click', function () {
	    layer.open({
	        type: 2,
	        title: '新增',// 设置false表示不显示
	        closeBtn: 1, // 0：不显示关闭按钮
	        shade: 0.4, // 遮罩透明度
	        area: ['700px', '480px'],
	        skin: 'layui-layer-rim', // 加上边框
	        fixed: false, // 不固定
	        maxmin: true, // 允许屏幕最小化
	        anim: 2,
	        content: ['toAdd?bizType=' + $("#bizType").val()] // iframe的url，no代表不显示滚动条
	    });
    });
    /** 新增 end */
	
	//加载数据
	getList();
	
	function getList() {
		var configName = $("#configName").val();
    	var code = $("#code").val();
    	var bizType = $("#bizType").val();
		table.render({
	   		 elem: '#sysConfigList', 
	   		 url: getCtxPath() + "/sysConfig/list?v=" + new Date().getTime(),
	   		 where: {
	   			configName : configName,
	   			bizType : bizType,
	   			code : code
		     },
	         method: 'get', 
	         loading: true,
	         id: 'sys_config_list_tb',
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
	        	 {field: 'id', align: 'center', title: '大类ID', width: '150'},
	        	 {field: 'configName', align: 'center', title: '大类名称', width: '180'},
	        	 {field: 'code', align: 'center', title: '编码', width: '150'},
	        	 {field: 'state', align: 'center', title: '状态', templet: '<div>{{ layui.laytpl.dealState(d.state) }}</div>', width: '120'},
	        	 {field: 'createMan', align: 'center', title: '创建人', width: '140'},
	        	 {field: 'createTime', align: 'center', title: '创建时间', templet: '<div>{{ layui.laytpl.toDateStringWithBlank(d.createTime,"yyyy-MM-dd HH:mm:ss") }}</div>', width: '180'},
	        	 {field: 'modifyMan', align: 'center', title: '修改人', width: '140'},
	        	 {field: 'modifyTime', align: 'center', title: '修改时间', templet: '<div>{{ layui.laytpl.toDateStringWithBlank(d.modifyTime,"yyyy-MM-dd HH:mm:ss") }}</div>', width: '180'},
	        	 {field: 'remark', align: 'center', title: '参数说明', width: '200'},
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
        table.on('tool(sysConfig)', function (obj) {
            var data = obj.data;
            if (obj.event === 'edit') {
                update(data.id, true);
            }
            if (obj.event === 'params') {
            	openSysConfigParams(data.id);
            }
            if (obj.event === 'del') {
                del(data.id);
            }
        });
        
	}
	
	layui.laytpl.dealState = function (state) {
		if (state == 0) {
			return "<font color='red'>无效</font>";
		} else if (state == 1) {
			return "<font color='green'>有效</font>";
		} else {
			return "未知";
		}
	}
	
    //跳更新或查看页面
    function update(id, isUpdate) {
    	layer.open({
	        type: 2,
	        title: '编辑',// 设置false表示不显示
	        closeBtn: 1, // 0：不显示关闭按钮
	        shade: 0.4, // 遮罩透明度
	        area: ['700px', '480px'],
	        skin: 'layui-layer-rim', // 加上边框
	        fixed: false, // 不固定
	        maxmin: true, // 允许屏幕最小化
	        anim: 2,
	        content: ['toViewOrUpdate?id='+id+'&isUpdate='+isUpdate] // iframe的url，no代表不显示滚动条
	    });
    }
    
    function openSysConfigParams(id) {
    	layer.open({
	        type: 2,
	        title: '编辑',// 设置false表示不显示
	        closeBtn: 1, // 0：不显示关闭按钮
	        shade: 0.4, // 遮罩透明度
	        area: [$(window).width() * 0.9 + 'px', $(window).height() - 50 + 'px'],
	        skin: 'layui-layer-rim', // 加上边框
	        fixed: false, // 不固定
	        maxmin: true, // 允许屏幕最小化
	        anim: 2,
	        content: [getCtxPath() + '/sysConfigParams/toList?configId='+id] // iframe的url，no代表不显示滚动条
	    });
    }
	
	//删除
    function del(id) {
    	layer.confirm('确定要删除吗？', function (index) {
            layer.close(index);
            $.ajax({
                url:getCtxPath() + "/sysConfig/delete?id="+id,
                method : "get",
                dataType : "json",
                success: function (data) {
                    // 获取父窗口的layer对象
                    var layerTips = parent.layer === undefined ? layui.layer : parent.layer;
                    window.top.layer.msg("删除成功", {
    					icon : 6
    				});// 提示信息
                    //刷新数据表格
                    table.reload('sys_config_list_tb');
                },
                error: function () {
                    layer.alert("数据删除失败");
                }
            });
        });
    }
	
});
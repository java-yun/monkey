layui.config({
	base : '../../plugin/js/'
}).use(['layer', 'form', 'element', 'table', 'upload'], function () {
	var $ = layui.jquery, 
	layerTips = parent.layer === undefined ? layui.layer : parent.layer, // 获取父窗口的layer对象
    layer = layui.layer, // 获取当前窗口的layer对象
    form = layui.form,
    upload = layui.upload,
    element = layui.element,
    table = layui.table;
	
	initActivationCodeStateSelect();
	
	//初始化黑名单类型select
	function initActivationCodeStateSelect() {
		$.ajax({
			url:getCtxPath() + "/activationCode/getActivationCodeState?v=" + new Date().getTime(),
            method : "get",
            dataType : "json",
            success: function (data) {
            	if (data.code == 0) {
            		$.each(data.data, function(index, item) {
        				$("#state").append("<option value='" + item.value + "'>" + item.desc + "</option>");
                	});
            		form.render();
				} else {
					window.top.layer.msg('激活码状态初始化失败',{icon:5,anim:2});
				}
            },
            error: function () {
            	window.layer.msg('请求失败', {
					icon : 2
				});
            }
        });
	}
	
	$('#export').on('click', function () {
	    layer.open({
	        type: 2,
	        title: '导出',// 设置false表示不显示
	        closeBtn: 1, // 0：不显示关闭按钮
	        shade: 0.4, // 遮罩透明度
	        area: ['700px', '480px'],
	        skin: 'layui-layer-rim', // 加上边框
	        fixed: false, // 不固定
	        maxmin: true, // 允许屏幕最小化
	        anim: 2,
	        content: ['toExport'] // iframe的url，no代表不显示滚动条
	    });
    });
	
	//查询按钮绑定事件
    $('#search').on('click', function () {
    	getList();
    });
    
    //刷新页面
    $('#refresh').on('click', function () {
    	$("#activationNo").val("");
    	$("#userId").val("");
    	$("#state").val("");
    	$("#startDate").val("");
    	$("#endDate").val("");
		form.render();
		getList();
    });
	
	//加载数据
	getList();
	
	function getList() {
		var activationNo = $("#activationNo").val();
    	var userId = $("#userId").val();
    	var state = $("#state").val();
    	var startDate = $("#startDate").val();
    	var endDate = $("#endDate").val();
		table.render({
	   		 elem: '#activationCodeList', 
	   		 url: getCtxPath() + "/activationCode/list?v=" + new Date().getTime(),
	   		 where: {
	   			activationNo : activationNo,
	   			state : state,
	   			startDate : startDate,
	   			endDate : endDate,
	   			userId : userId
		     },
	         method: 'get', 
	         loading: true,
	         id: 'activation_code_list_tb',
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
	        	 {field: 'activationNo', align: 'center', title: '激活码', width: '200'},
	        	 {field: 'state', align: 'center', title: '状态', width: '100', templet: '<div>{{ layui.laytpl.showState(d.state,"yyyy-MM-dd HH:mm:ss") }}</div>'},
	        	 {field: 'userId', align: 'center', title: '用户ID', width: '200'},
	        	 {field: 'useTime', align: 'center', title: '使用时间', templet: '<div>{{ layui.laytpl.toDateStringWithBlank(d.useTime,"yyyy-MM-dd HH:mm:ss") }}</div>', width: '180'},
	        	 {field: 'effectTime', align: 'center', title: '生效时间', templet: '<div>{{ layui.laytpl.toDateStringWithBlank(d.effectTime,"yyyy-MM-dd HH:mm:ss") }}</div>', width: '180'},
	        	 {field: 'deadTime', align: 'center', title: '过期时间', templet: '<div>{{ layui.laytpl.toDateStringWithBlank(d.deadTime,"yyyy-MM-dd HH:mm:ss") }}</div>', width: '180'},
	        	 {field: 'createMan', align: 'center', title: '创建人', width: '120'},
	        	 {field: 'createTime', align: 'center', title: '创建时间', templet: '<div>{{ layui.laytpl.toDateStringWithBlank(d.createTime,"yyyy-MM-dd HH:mm:ss") }}</div>', width: '180'},
	        	 {field: 'modifyMan', align: 'center', title: '创建人', width: '120'},
	        	 {field: 'modifyTime', align: 'center', title: '创建时间', templet: '<div>{{ layui.laytpl.toDateStringWithBlank(d.modifyTime,"yyyy-MM-dd HH:mm:ss") }}</div>', width: '180'},
	        	 {title: '操作',align: 'center', width: '8%', fixed: 'right', toolbar: "#toolBar"}
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
        table.on('tool(activationCode)', function (obj) {
            var data = obj.data;
            if (obj.event === 'view') {
            	getDetail(data.id);
            }
        });
        
	}
	
	//获取黑名单类型名称
	layui.laytpl.showState = function (state) {
		var stateName = "";
		$.ajax({
            url:getCtxPath() + "/activationCode/getActivationCodeState?",
            method : "get",
            dataType : "json",
            async : false,
            success: function (data) {
            	if (data.code == 0) {
            		$.each(data.data, function(index, item) {
        				if (item.value == state) {
        					stateName = item.desc;
        					return;
						}
                	});
				} else {
					window.top.layer.msg('激活码状态初查询异常',{icon:5,anim:2});
				}
            },
            error: function () {
            	window.layer.msg('请求失败', {
					icon : 2
				});
            }
        });
		return stateName;
	}
	
    //跳更新或查看页面
    function getDetail(id, isUpdate) {
    	layer.open({
	        type: 2,
	        title: '详情',// 设置false表示不显示
	        closeBtn: 1, // 0：不显示关闭按钮
	        shade: 0.4, // 遮罩透明度
	        area: ['700px', '480px'],
	        skin: 'layui-layer-rim', // 加上边框
	        fixed: false, // 不固定
	        maxmin: true, // 允许屏幕最小化
	        anim: 2,
	        content: ['getById?id='+id] // iframe的url，no代表不显示滚动条
	    });
    }
	
});
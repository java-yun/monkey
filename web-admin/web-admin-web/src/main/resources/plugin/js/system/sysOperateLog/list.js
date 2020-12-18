layui.config({
	base : '../../plugin/js/'
}).use(['layer', 'form', 'table'], function () {
	var $ = layui.jquery, 
	layerTips = parent.layer === undefined ? layui.layer : parent.layer, // 获取父窗口的layer对象
    layer = layui.layer, // 获取当前窗口的layer对象
    form = layui.form,
    table = layui.table;
	
	//查询按钮绑定事件
    $('#search').on('click', function () {
    	getList();
    });
    
    //刷新页面
    $('#refresh').on('click', function () {
    	$("#optUserName").val("");
    	$("#menuName").val("");
    	$("#buttonName").val("");
    	$("#startDate").val("");
    	$("#endDate").val("");
		form.render();
		getList();
    });
	
	//加载数据
	getList();
	
	function getList() {
		var optUserName = $("#optUserName").val();
    	var menuName = $("#menuName").val();
    	var buttonName = $("#buttonName").val();
    	var startDate = $("#startDate").val();
    	var endDate = $("#endDate").val();
		table.render({
	   		 elem: '#sysOperateLogList', 
	   		 url: getCtxPath() + "/sysOperateLog/list?v=" + new Date().getTime(),
	   		 where: {
	   			optUserName : optUserName,
	   			menuName : menuName,
	   			buttonName : buttonName,
	   			startDate : startDate,
	   			endDate : endDate
		     },
	         method: 'get', 
	         loading: true,
	         id: 'sys_operate_log_list_tb',
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
	        	 {field: 'optUserName', align: 'center', title: '用户名', width: '180'},
	        	 {field: 'menuName', align: 'center', title: '菜单名称', width: '200'},
	        	 {field: 'buttonName', align: 'center', title: '按钮名称', width: '180'},
	        	 {field: 'optTime', align: 'center', title: '创建时间', templet: '<div>{{ layui.laytpl.toDateStringWithBlank(d.optTime,"yyyy-MM-dd HH:mm:ss") }}</div>', width: '180'},
	        	 {field: 'optDesc', align: 'center', title: '操作描述', width: '200'},
	        	 {field: 'requestParams', align: 'center', title: '请求参数', width: '600'},
	        	 {title: '操作',align: 'center', width: '10%', fixed: 'right', toolbar: "#toolBar"}
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
        table.on('tool(sysOperateLog)', function (obj) {
            var data = obj.data;
            if (obj.event === 'view') {
                getSysOperateLogDetail(data.id);
            }
        });
        
	}
	
    function getSysOperateLogDetail(id) {
    	layer.open({
	        type: 2,
	        title: '查看',// 设置false表示不显示
	        closeBtn: 1, // 0：不显示关闭按钮
	        shade: 0.4, // 遮罩透明度
	        area: [$(window).width() * 0.9 + 'px', $(window).height() - 50 + 'px'],
	        skin: 'layui-layer-rim', // 加上边框
	        fixed: false, // 不固定
	        maxmin: true, // 允许屏幕最小化
	        anim: 2,
	        content: [getCtxPath() + '/sysOperateLog/getById?id='+id] // iframe的url，no代表不显示滚动条
	    });
    }
	
});
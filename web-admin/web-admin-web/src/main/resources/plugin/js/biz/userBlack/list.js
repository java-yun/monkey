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
	
	//模板下载
    $('#downloadTemplate').on('click', function () {
    	window.location.href = getCtxPath() + "/file/template/download?fileName=黑名单导入模板.xlsx&resourceFile=platUserBlackListTemplate.xlsx&v=" + new Date().getTime()
    });
    
    upload.render({
	      elem: '#import',
	      url: 'batchAdd',
	      accept : 'file',//默认image
	      exts : 'xlsx',//允许上传文件的后缀名，用  | 隔开
	      done: function(res){
	    	  if(res.code == "0"){
	    		  layer.msg(res.msg,{icon: 6,anim: 6});
	    		  getList();
	    	  }else{
	    		  window.top.layer.msg(res.msg, {
	    			  icon : 5
	    		  });// 提示信息
	    	  }
	      },
	      error: function(){
	    	  layer.msg("添加失败",{icon: 5,anim: 6});
	      }
	 });
	
	//查询按钮绑定事件
    $('#search').on('click', function () {
    	getList();
    });
    
    //刷新页面
    $('#refresh').on('click', function () {
    	$("#blackType").val("");
    	$("#blacklist").val("");
		form.render();
		getList();
    });
    
    /** 新增 start */
    $('#userBlackAdd').on('click', function () {
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
	        content: ['toAdd'] // iframe的url，no代表不显示滚动条
	    });
    });
    /** 新增 end */
	
	//加载数据
	getList();
	
	function getList() {
		var blackType = $("#blackType").val();
    	var blacklist = $("#blacklist").val();
		table.render({
	   		 elem: '#userBlackList', 
	   		 url: getCtxPath() + "/userBlack/list?v=" + new Date().getTime(),
	   		 where: {
	   			blackType : blackType,
	   			blacklist : blacklist
		     },
	         method: 'get', 
	         loading: true,
	         id: 'user_black_list_tb',
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
	        	 {field: 'blackType', align: 'center', title: '黑名单类型', width: '180', templet: '<div>{{ layui.laytpl.getBlackTypeName(d.blackType) }}</div>'},
	        	 {field: 'blacklist', align: 'center', title: '黑名单内容', width: '250'},
	        	 {field: 'createMan', align: 'center', title: '创建人', width: '150'},
	        	 {field: 'createTime', align: 'center', title: '创建时间', templet: '<div>{{ layui.laytpl.toDateStringWithBlank(d.createTime,"yyyy-MM-dd HH:mm:ss") }}</div>', width: '200'},
	        	 {field: 'modifyMan', align: 'center', title: '修改人', width: '150'},
	        	 {field: 'modifyTime', align: 'center', title: '修改时间', templet: '<div>{{ layui.laytpl.toDateStringWithBlank(d.modifyTime,"yyyy-MM-dd HH:mm:ss") }}</div>', width: '200'},
	        	 {field: 'remark', align: 'center', title: '备注', width: '320'},
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
        table.on('tool(userBlack)', function (obj) {
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
	
	//获取黑名单类型名称
	layui.laytpl.getBlackTypeName = function (blackType) {
		var blackTypeName = "";
		$.ajax({
            url:getCtxPath() + "/userBlack/getBlackTypeList?",
            method : "get",
            dataType : "json",
            async : false,
            success: function (data) {
            	if (data.code == 0) {
            		$.each(data.data, function(index, item) {
        				if (item.value == blackType) {
        					blackTypeName = item.desc;
        					return;
						}
                	});
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
		return blackTypeName;
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
	
	//删除
    function del(id) {
    	layer.confirm('确定要删除吗？', function (index) {
            layer.close(index);
            $.ajax({
                url:getCtxPath() + "/userBlack/delete?id="+id,
                method : "get",
                dataType : "json",
                success: function (data) {
                    // 获取父窗口的layer对象
                    var layerTips = parent.layer === undefined ? layui.layer : parent.layer;
                    window.top.layer.msg("删除成功", {
    					icon : 6
    				});// 提示信息
                    //刷新数据表格
                    table.reload('user_black_list_tb');
                },
                error: function () {
                    layer.alert("数据删除失败");
                }
            });
        });
    }
	
});
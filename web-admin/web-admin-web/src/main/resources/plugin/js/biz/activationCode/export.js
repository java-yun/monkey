layui.config({
    base: '../../../plugin/js/'
}).use(['layer', 'form', 'element'], function () {
    var form = layui.form;
    var _data;
    var $ = layui.jquery,
        layer = layui.layer;

	$('#close').click(function () {
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
        return false;
    });
	
	//导出excel报表
	$("#export").on("click", function () {
		debugger;
	    var exportNum = $("#exportNum").val();
	    var effectTime = $("#effectTime").val();
	    var expireTime = $("#expireTime").val();
	    var remark = $("#remark").val();
	    if (exportNum > parseInt($("#maxActCodeImportNum").val())) {
	    	window.layer.msg('一次导出数量限制在' + $("#maxActCodeImportNum").val(), {
	    		icon : 5
	    	});
	    	return false;
	    }
	    if (expireTime <= getTime()) {
	    	window.layer.msg('过期时间不能小于等于当前时间', {
				icon : 5
			});
	    	return false;
		}
	    var exportForm = $("#downloadFormTTT");
	    if (exportForm.length == 0) {
	    	exportForm = $("<form>")
	        $("body").append(exportForm);//将表单放置在web中
	    	exportForm.attr("style", "display:none");
	    	exportForm.attr("id", "downloadForm");
	    	exportForm.attr("target", "downLoadTarget");
	    	exportForm.attr("method", "post");//请求类型
	    	exportForm.attr("action", getCtxPath()+"/activationCode/export");//请求地址
	    }
	    exportForm.append(fittingInput("exportNum",exportNum));
	    exportForm.append(fittingInput("effectTime",effectTime));
	    exportForm.append(fittingInput("expireTime",expireTime));
	    exportForm.append(fittingInput("remark",remark));
	    exportForm.submit();//表单提交
	    console.log("form 提交成功");
	    var index = parent.layer.getFrameIndex(window.name);
	    parent.layer.close(index);
	    parent.layui.table.reload('activation_code_list_tb');
	    return false;
	});
});

function fittingInput(nameObj, valueObj) {
    var inputObj = $("<input>");
    inputObj.attr("type", "hidden");
    inputObj.attr("name", nameObj);
    inputObj.attr("value", valueObj);
    return inputObj;
}

function getTime() {
	var now = new Date();
    var yy = now.getFullYear(); //年
    var mm = now.getMonth() + 1; //月
    var dd = now.getDate(); //日
    var hh = now.getHours(); //时
    var ii = now.getMinutes(); //分
    var ss = now.getSeconds(); //秒
    var time = yy + "-";
    if (mm < 10) time += "0";
	time += mm + "-";
    if (dd < 10) time += "0";
	time += dd + " ";
    if (hh < 10) time += "0";
	time += hh + ":";
    if (ii < 10) time += '0';
	time += ii + ":";
    if (ss < 10) time += '0';
	time += ss;
	return time;
}
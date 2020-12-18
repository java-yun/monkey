function getCtxPath() {
    var curWwwPath = window.document.location.href;
    var pathName = window.document.location.pathname;
    var pos = curWwwPath.indexOf(pathName);
    var localhostPath = curWwwPath.substring(0, pos);
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    //return (localhostPath + projectName);
    return localhostPath;
}

// 请求到后台Controller
var hostIp = getCtxPath();
//时间格式化，时间为null、undefined时，返回当前时间
layui.laytpl.toDateString = function (d, format) {
    var date = new Date(d || new Date())
        , ymd = [
        this.digit(date.getFullYear(), 4)
        , this.digit(date.getMonth() + 1)
        , this.digit(date.getDate())
    ]
        , hms = [
        this.digit(date.getHours())
        , this.digit(date.getMinutes())
        , this.digit(date.getSeconds())
    ];

    format = format || 'yyyy-MM-dd HH:mm:ss';

    return format.replace(/yyyy/g, ymd[0])
        .replace(/MM/g, ymd[1])
        .replace(/dd/g, ymd[2])
        .replace(/HH/g, hms[0])
        .replace(/mm/g, hms[1])
        .replace(/ss/g, hms[2]);
};

//时间格式化，时间为null、undefined时，返回空串
layui.laytpl.toDateStringWithBlank = function (timeStamp, format) {
    if (timeStamp == null || timeStamp == undefined || timeStamp == "") {
        return "";
    }
    var date = new Date(timeStamp || new Date())
        , ymd = [
        this.digit(date.getFullYear(), 4)
        , this.digit(date.getMonth() + 1)
        , this.digit(date.getDate())
    ]
        , hms = [
        this.digit(date.getHours())
        , this.digit(date.getMinutes())
        , this.digit(date.getSeconds())
    ];

    format = format || 'yyyy-MM-dd HH:mm:ss';

    return format.replace(/yyyy/g, ymd[0])
        .replace(/MM/g, ymd[1])
        .replace(/dd/g, ymd[2])
        .replace(/HH/g, hms[0])
        .replace(/mm/g, hms[1])
        .replace(/ss/g, hms[2]);
};

//数字前置补零
layui.laytpl.digit = function (num, length, end) {
    var str = '';
    num = String(num);
    length = length || 2;
    for (var i = num.length; i < length; i++) {
        str += '0';
    }
    return num < Math.pow(10, length) ? str + (num | 0) : num;
};

// 回车提交表单
document.onkeydown = function (e) {
    var theEvent = window.event || e;
    var code = theEvent.keyCode || theEvent.which;
    if (code == 13) {
        $(".select .select-on").click();
    }
}
var enumJsons = new Object();

function initEnum(dicBigCode) {
    // 用于加载一次分页数据字典
    if (dicBigCode == "" || dicBigCode == null || dicBigCode == undefined) {
        alert("大类code为空！");
        return;
    }
    $.ajax({
        url: hostIp + "/mini/getScDictSmall",
        method: "post",
        dataType: "json",
        async: false, //必须设置同步，如果异步会偶尔出现下拉框填充的内容不显示
        data: {
            dicCode: dicBigCode
        },
        success: function (data) {
            // 获取后台加载的json值
            enumJsons = data.jsonArr;
        },
        error: function () {
            layer.msg("加载数据字典异常，请尝试F5刷新页面！", {icon: 2});//提示信息
        }
    });
}

/**
 * 加载数据字典小类,填充下拉框
 * @param dicBigCode 大类主键code
 * @param loadId 要填充的下拉框id选择器
 */
function initDicSmall(dicBigCode, loadId) {
    // 因为是list集合里是json，需要嵌套循环
    $.each(enumJsons, function (k, v) {
        if (v.dicCode == dicBigCode) {
            // 填充下拉框内容
            $("#" + loadId).append("<option value='" + v.value + "'>" + v.name + "</option>");
        }
    });


}

/**
 * 获取枚举值
 * @param dicBigCode 大类code
 * @param enumVal 需要枚举的值
 * @returns
 */
function getEnum(dicBigCode, enumVal) {
    var enums = "";
    // 因为是list集合里是json，需要嵌套循环
    $.each(enumJsons, function (k, v) {
        // 转型为字符串
        var value1 = (v.dicCode + "" + v.value);
        var value2 = (dicBigCode + "" + enumVal);
        // 匹配枚举
        if (value1 == value2) {
//				// 获取枚举值
            enums = v.name + "";
        }
    });
    return enums;
}
/**
 * 获取枚举值
 * @param dicBigCode 需要枚举的值
 * @returns
 */
function getGameEnum(dicBigCode) {
    var enums = "";
    // 因为是list集合里是json，需要嵌套循环
    $.each(enumJson, function (k, v) {
        // 转型为字符串
        var value1 = (v.code + "");
        var value2 = (dicBigCode + "");
        // 匹配枚举
        if (value1 == value2) {
//				// 获取枚举值
            enums = v.name + "";
        }
    });
    return enums;
}
/**
 * 获取枚举值
 * @param appId 需要枚举的值
 * @returns
 */
function getAppIdEnum(appId) {
    var enums = "";
    // 因为是list集合里是json，需要嵌套循环
    $.each(enumJson, function (k, v) {
        // 转型为字符串
        var value1 = (v.id + "");
        var value2 = (appId + "");
        // 匹配枚举
        if (value1 == value2) {
//				// 获取枚举值
            enums = v.appName + "";
        }
    });
    return enums;
}

/**
 * post提交表单，文件下载
 * @param url post请求提交地址
 * @param params 参数
 * @returns
 */
function formFileDownload(url, params) {
	if (params != undefined && !$.isEmptyObject(params)) {
		var form = $("#downloadForm");
		if (form.length == 0) {
			//构造一个form
			form = $("<form>")
			$("body").append(form);//将表单放置在web中
			form.attr("style", "display:none");
			form.attr("id", "downloadForm");
			form.attr("target", "downLoadTarget");
			form.attr("method", "post");//请求类型
			form.attr("action", url);//请求地址
		}
		for ( var key in params) {
			form.append(fittingInput(key, params[key]));
		}
		form.submit();//表单提交
		console.log("form 提交成功");
	}
}

/**
 * 构造input
 * @param nameObj
 * @param valueObj
 * @returns
 */
function fittingInput(nameObj, valueObj) {
    var inputObj = $("<input>");
    inputObj.attr("type", "hidden");
    inputObj.attr("name", nameObj);
    inputObj.attr("value", valueObj);
    return inputObj;
}
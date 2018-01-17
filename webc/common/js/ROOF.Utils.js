var ROOF;
if (!ROOF) {
	ROOF = new Object();
}
if (!ROOF.Utils) {
	ROOF.Utils = new Object();
}

ROOF.Utils.ns = function(path) {
	var arr = path.split(".");
	var ns = "";
	for ( var i = 0; i < arr.length; i++) {
		if (i > 0) {
			ns += ".";
		}
		ns += arr[i];
		eval("if(typeof(" + ns + ")=='undefined')" + ns + " = new Object();");
	}
};

ROOF.Utils.convertVal = function(val) {
	if (val == "0") {
		return "0";
	}
	return (!val) ? "" : (val + "").replace(/(^\s*)|(\s*$)/g, "");
};

ROOF.Utils.trim = function(val) {
	if (!val) {
		return "";
	}
	return (val + "").replace(/(^\s*)|(\s*$)/g, "");
};

ROOF.Utils.isImage = function(path) {
	var typeArr = [ ".jpg", ".jpeg", ".gif", ".bmp", ".png" ];
	return ROOF.Utils.isEndWith(path, typeArr);
};

ROOF.Utils.isExcel2003 = function(path) {
	var typeArr = [ ".xls" ];
	return ROOF.Utils.isEndWith(path, typeArr);
};

ROOF.Utils.isEndWith = function(path, typeArr) {
	var type = path.substring(path.lastIndexOf("."));
	type = type.toLowerCase();
	return (typeArr.join("").indexOf(type) != -1);
};

ROOF.Utils.checkAllBox = function(obj, name) {
	$("input[:checkbox][type='checkbox'][name^='" + name + "']").attr(
			"checked", obj.checked);
};

// 从当前下拉框列表中返回中文描述 [OPTION数组，下拉框值]
ROOF.Utils.getSelectText = function(optId, optVal) {
	var elements = $("#" + optId).find("option");
	var optText = "";
	$.each(elements, function(index, element) {
		if (element.value == optVal) {
			optText = element.text;// 获得中文描述
			return false;// break;
		}
	});
	return optText;
};

ROOF.Utils.getCheckedObj = function(name) {
	var arr = $("input:checked[:checkbox][type='checkbox'][name^='" + name
			+ "']");
	if (arr.length != 1) {
		alert("请先选中一条记录");
		return false;
	}
	return arr[0].value;
};

// 级联查询，子集元素的ID，默认下拉框的值，查询的URL，查询的JSON格式数据，默认选择的值
ROOF.Utils.cascadeQuery = function(subElementId, defaultOption, submitUrl,
		submitJsonData, selectedVal) {
	$("#" + subElementId).empty();
	$("#" + subElementId).append(
			'<option value="">' + defaultOption + '</option>');
	var data = null;
	try {
		data = eval("(" + submitJsonData + ")");
	} catch (e) {
		return;
	}
	if (!data) {
		return;
	}
	$
			.ajax({
				type : "POST",
				url : submitUrl,
				dataType : "json",
				data : data,
				error : function(msg) {
					alert("Ajax调用失败");
				},
				success : function(arr) {
					if (!arr) {
						return;
					}
					for ( var i = 0; i < arr.length; i++) {
						$("#" + subElementId)
								.append(
										'<option '
												+ ((selectedVal == arr[i].id) ? "selected"
														: "") + ' value="'
												+ arr[i].id + '">'
												+ arr[i].name + '</option>');
					}
				}
			});
};

ROOF.Utils.emptyForm = function(formObj, ignoreTypeArr) {
	if (!ignoreTypeArr) {
		ignoreTypeArr = new Array();
	}
	ignoreTypeArr[ignoreTypeArr.length] = "button";
	ignoreTypeArr[ignoreTypeArr.length] = "submit";
	ignoreTypeArr[ignoreTypeArr.length] = "reset";
	ignoreTypeArr[ignoreTypeArr.length] = "hidden";
	var findElementType = "input,select,textarea";// 表单中查找的元素标签
	var elements = formObj.find(findElementType);
	$.each(elements, function(index, element) {
		if (jQuery.inArray(element.type, ignoreTypeArr) != -1) {
			return true;// continue;
		}
		if (element.type == "check" || element.type == "radio" || element.type == "checkbox") {
			element.checked = false;
		} else {
			if (element.type.indexOf("select") != -1) {
				element.selectedIndex = 0;
			} else {
				$(element).val("");
			}
		}
	});
};

ROOF.Utils.readonlyForm = function(formObj, ignoreTypeArr) {
	if (!ignoreTypeArr) {
		ignoreTypeArr = new Array();
	}
	ignoreTypeArr[ignoreTypeArr.length] = "button";
	ignoreTypeArr[ignoreTypeArr.length] = "submit";
	ignoreTypeArr[ignoreTypeArr.length] = "reset";
	var findElementType = "input,select,textarea";// 表单中查找的元素标签
	var elements = formObj.find(findElementType);
	$.each(elements, function(index, element) {
		if (jQuery.inArray(element.type, ignoreTypeArr) != -1) {
			return true;// continue;
		}
		element.readonly = true;
		element.disabled = true;
	});
};

ROOF.Utils.enableForm = function(formObj, ignoreIdsArr) {
	if (!ignoreIdsArr) {
		ignoreIdsArr = new Array();
	}
	var ignoreTypeArr = new Array();
	ignoreTypeArr[ignoreTypeArr.length] = "button";
	ignoreTypeArr[ignoreTypeArr.length] = "submit";
	ignoreTypeArr[ignoreTypeArr.length] = "reset";
	var findElementType = "input,select,textarea";// 表单中查找的元素标签
	var elements = formObj.find(findElementType);
	$.each(elements, function(index, element) {
		if (jQuery.inArray(element.type, ignoreTypeArr) != -1) {
			return true;// continue;
		}
		if (jQuery.inArray(element.id, ignoreIdsArr) != -1) {
			return true;// continue;
		}
		element.readonly = false;
		element.disabled = false;
	});
};

// target:_self(本身),_parent(上级窗口),_top(最顶层窗口)
ROOF.Utils.back = function(paramInput, primary, action, target) {// 将参数字符串转换成隐藏的表单元素
	if (!target) {
		target = "_self";
	}
	var arg = $("#" + paramInput + "_paramString").val().substring(1);// 去掉字符串的?
	var argHidd = "";
	var args = arg.split("&");
	for ( var i = 0; i < args.length; i++) {
		var t = args[i].split("=");
		if (t[0] == primary) {
			continue;
		}
		argHidd += '<input type="hidden" name="' + t[0] + '" value="' + t[1]
				+ '" />';
	}
	var formStr = '<form id="' + paramInput
			+ '_argForm" method="post" target="' + target + '" action="'
			+ (action ? action : ($("#" + paramInput + "Form").attr("action")))
			+ '">';
	$("body").append(formStr + argHidd + '</form>');
	$("#" + paramInput + "_argForm").submit();
};
var datepicker_CurrentInput; 
ROOF.Utils.datepicker=function(objId,dateFormat){
	var obj = null;
	if (objId instanceof jQuery) {
		obj = objId;
	} else if (jQuery.type(objId) === "string") {
		obj = $("#" + objId);
	}else {
		obj = $(objId);
	}
	obj.datepicker({
		changeMonth : true,
		changeYear : true,
		showButtonPanel: true, 
		closeText: '清除',
		beforeShow: function (input, inst) { 
			datepicker_CurrentInput = input;
		} 
	});
	$(".ui-datepicker-close").live("click", function (){  
	     datepicker_CurrentInput.value = "";  
	  }); 
}

ROOF.Utils.datetimepicker=function(objId,timeFormat){
	var obj = null;
	if (objId instanceof jQuery) {
		obj = objId;
	} else if (jQuery.type(objId) === "string") {
		obj = $("#" + objId);
	}else {
		obj = $(objId);
	}
	obj.datetimepicker({
		controlType: 'select',
		timeFormat: timeFormat,
		changeMonth : true,
		changeYear : true,
		showButtonPanel: true, 
		closeText: '清除',
		beforeShow: function (input, inst) { 
			datepicker_CurrentInput = input;
		} 
	});
	$(".ui-datepicker-close").live("click", function (){  
	     datepicker_CurrentInput.value = "";  
	  }); 
}

ROOF.Utils.getCalendarProperty = function(dateFormat) {
	if (!dateFormat) {
		dateFormat = "yyyy-MM-dd hh:mm:ss";
	}
	var needTime = (dateFormat.length > "yyyy-MM-dd".length) ? true : false;
	var property = {
		divId : "demoCalendar",// 日历控件最外层DIV的ID
		needTime : needTime,// 是否需要显示精确到秒的时间选择器，即输出时间中是否需要精确到小时：分：秒 默认为FALSE可不填
		yearRange : [ 1930, 2030 ],// 可选年份的范围,数组第一个为开始年份，第二个为结束年份,如[1970,2030],可不填
		week : [ '日', '一', '二', '三', '四', '五', '六' ],// 数组，设定了周日至周六的显示格式,可不填
		month : [ '一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月',
				'十一月', '十二月' ],// 数组，设定了12个月份的显示格式,可不填
		format : dateFormat
	/* 设定日期的输出格式,可不填 */
	};
	return property;
};

ROOF.Utils.showBlockObj = function(blockObj) {// 显示遮罩层 [遮罩层对象]
	$.blockUI({
		message : blockObj
	});
};

ROOF.Utils.showBlock = function() {
	$
			.blockUI({
				allowBodyStretch : true,
				message : '<img alt="请等待，正在加载数据..." src="' + ROOF.Utils.projectName() + '/common/images/large-loading.gif">',
				css : {
					border : 'none',
					height : 0
				},
				overlayCSS : {
					opacity : .45,
					backgroundColor : '#000000'
				}
			});
};

ROOF.Utils.hideBlock = function() {
	$.unblockUI();
};

ROOF.Utils.StringBuffer = function() {// 模拟Java中的StringBuffer
	this._strings = new Array();
};
ROOF.Utils.StringBuffer.prototype.append = function(str) {
	this._strings.push(str);
};
ROOF.Utils.StringBuffer.prototype.toString = function(split) {
	return this._strings.join(split);
};
/*******************************************************************************
 * var buffer = new StringBuffer(); buffer.append("hello ");
 * buffer.append("world"); var result = buffer.toString();
 ******************************************************************************/

ROOF.Utils.ajaxcommon = function() {
	$(document).ajaxSend(function(evt, request, settings) {
		ROOF.Utils.showBlock();
	});
	$(document).ajaxSuccess(function(evt, request, settings) {
		ROOF.Utils.hideBlock();
		var data = jQuery.parseJSON(request.responseText);
		if (data && data.state) {
			if (data.state == 'error' || data.state == 'fail') {
				alert(data.message);
			}
		}
	});
};

ROOF.Utils.alert = function(message, dialogcloseCallBack, title) {
	var b = $('<div><p><span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>'
			+ message + '</p></div>');
	if (title) {
		b.attr('title', title);
	} else {
		b.attr('title', '网页消息');
	}
	b.attr('top', ($(window).height() - b.height()) / 2);
	b.attr('left', ($(window).width() - b.width()) / 2);
	b.dialog({
		resizable : false,
		height : 180,
		modal : true,
		show : {
			delay : 100
		},
		buttons : [ {
			text : "确定",
			click : function() {
				$(this).dialog("close");
			}
		} ]
	});
	b.on("dialogclose", function(event, ui) {
		if (dialogcloseCallBack) {
			dialogcloseCallBack.call(b, b);
		}
	});
};

ROOF.Utils.confirm = function(message, okCallback, cancelCallback, title) {
	var b = $('<div><p><span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>'
			+ message + '</p></div>');
	if (title) {
		b.attr('title', title);
	} else {
		b.attr('title', '网页消息');
	}
	b.dialog({
		resizable : false,
		height : 140,
		modal : true,
		show : {
			delay : 100
		},
		buttons : [ {
			text : "确定",
			click : function() {
				if (okCallback) {
					okCallback.call(b, b);
				}
				$(this).dialog("close");
			}
		}, {
			text : "取消",
			click : function() {
				if (cancelCallback) {
					cancelCallback.call(b, b);
				}
				$(this).dialog("close");
			}
		} ]
	});
};

ROOF.Utils.projectName = function() {
	var pathName = window.document.location.pathname;
	var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
	return projectName;
}

ROOF.Utils.openwindow = function(url, name, iWidth, iHeight, custFlg, level) {
	var url; // 转向网页的地址;
	var name; // 网页名称，可为空;
	if(!custFlg){
		iWidth=920; // 弹出窗口的宽度;
		iHeight=520; // 弹出窗口的高度;
	}
	var iTop = (window.screen.availHeight - 30 - iHeight) / 2; // 获得窗口的垂直位置;
	var iLeft = (window.screen.availWidth - 10 - iWidth) / 2; // 获得窗口的水平位置;
	return window
			.open(
					url,
					name,
					'height='
							+ iHeight
							+ ',innerHeight='
							+ iHeight
							+ ',width='
							+ iWidth
							+ ',innerWidth='
							+ iWidth
							+ ',top='
							+ iTop
							+ ',left='
							+ iLeft
							+ ',toolbar=no,menubar=no,scrollbars=auto,resizeable=no,location=no,status=no',
					level);
}
ROOF.Utils.openwindowscrollbarsyes = function(url, name, iWidth, iHeight, custFlg) {
	var url; // 转向网页的地址;
	var name; // 网页名称，可为空;
	if(!custFlg){
		iWidth=920; // 弹出窗口的宽度;
		iHeight=520; // 弹出窗口的高度;
	}
	var iTop = (window.screen.availHeight - 30 - iHeight) / 2; // 获得窗口的垂直位置;
	var iLeft = (window.screen.availWidth - 10 - iWidth) / 2; // 获得窗口的水平位置;
	return window
			.open(
					url,
					name,
					'height='
							+ iHeight
							+ ',,innerHeight='
							+ iHeight
							+ ',width='
							+ iWidth
							+ ',innerWidth='
							+ iWidth
							+ ',top='
							+ iTop
							+ ',left='
							+ iLeft
							+ ',toolbar=no,menubar=no,scrollbars=yes,resizeable=yes,location=no,status=no');
}

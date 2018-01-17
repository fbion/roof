$(function() {
	$('#mail_mail_form').validate({
		rules : {
			'id' : {
				number : true,
				required : true
			},
			'name' : {
				required : true
			},
			'content' : {
				required : true
			},
			'send_count' : {
				number : true,
				required : true
			},
			'stat' : {
				number : true,
				required : true
			}
		},
		messages : {
			'id' : {
				required : "id必填"
			},
			'name' : {
				required : "名称必填"
			},
			'content' : {
				required : "内容必填"
			},
			'send_count' : {
				required : "发送次数必填"
			},
			'stat' : {
				required : "状态必填"
			}
		},
		errorPlacement : com.sinopec.errorpacement
	});
	$("#mail_mail_form").ajaxForm({
		'type' : 'post',
		'cache' : false,
		'dataType' : 'json',
		'clearForm' : true,
		'success' : function(d) {
			alert(d.message);
			$("#mail_mail_update_close_btn").click();
		},
		'error' : function(d) {
			alert(d.statusText);
		}
	});

	$("#mail_mail_detail_close_btn").click(function() {
		reloadFun();
		return false;
	});

	$("#mail_mail_update_close_btn").click(function() {
		reloadFun();
		return false;
	});
});
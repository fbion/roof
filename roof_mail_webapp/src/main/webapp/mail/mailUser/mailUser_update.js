$(function() {

	$('#mailuser_mailUser_form').validate({
		rules : {
			'username' : {
				required : true
			},
			'mail' : {
				required : true,
				email : true
			}
		},
		messages : {
			'username' : {
				required : "用户名必填"
			},
			'mail' : {
				required : "邮箱必填"
			}
		},
		errorPlacement : com.sinopec.errorpacement
	});
	$("#mailuser_mailUser_form").ajaxForm({
		'type' : 'post',
		'cache' : false,
		'dataType' : 'json',
		'clearForm' : true,
		'success' : function(d) {
			alert(d.message);
			$("#mailuser_mailUser_update_close_btn").click();
		},
		'error' : function(d) {
			alert(d.statusText);
		}
	});

	$("#mailuser_mailUser_detail_close_btn").click(function() {
		reloadFun();
		return false;
	});

	$("#mailuser_mailUser_update_close_btn").click(function() {
		reloadFun();
		return false;
	});
});
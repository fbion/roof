$(function() {
	$('input[name="date"]').datetimepicker();
	$("#mail_name").attr("readonly", "readonly");
	$("#content").attr("readonly", "readonly");
	$('#mail_mail_form').validate({
		rules : {
			'to' : {
				email : true,
				required : true
			}
		},
		messages : {},
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
$(function() {
	$('#mailuser_mailUser_form').validate({
		rules : {
			'file' : {
				required : true
			}
		},
		messages : {},
		errorPlacement : com.sinopec.errorpacement
	});
	$("#mailuser_mailUser_form").ajaxForm({
		'type' : 'post',
		'cache' : false,
		'dataType' : 'json',
		'clearForm' : true,
		'beforeSubmit' : function() {
			ROOF.Utils.showBlock();
		},
		'success' : function(d) {
			alert(d.message);
			ROOF.Utils.hideBlock();
			$("#mailuser_mailUser_update_close_btn").click();
		},
		'error' : function(d) {
			alert(d.statusText);
			ROOF.Utils.hideBlock();
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
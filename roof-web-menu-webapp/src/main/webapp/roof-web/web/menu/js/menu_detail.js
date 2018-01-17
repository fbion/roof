$(document).ready(function() {
	$('#delSrcBtn').click(function() {
		$.post($(this).attr('href'), {

		}, function(d) {
			if (d.state == 'success') {
				ROOF.Utils.alert(d.message, function() {
					if (parent.reAsyncParentChildNodes) {
						parent.reAsyncParentChildNodes();
					}
				});
			}
		}, 'json');
		return false;
	});

	$('#saveBtn').click(function() {
		$('#mainForm').submit();
		return false;
	});

	$('#mainForm').ajaxForm({
		dateType : 'json',
		success : function(d) {
			ROOF.Utils.alert(d.message, function() {
				if (parent.reAsyncChildNodes) {
					parent.reAsyncParentChildNodes();
				}
			});
		}
	});
});
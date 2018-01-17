$(document).ready(
		function() {
			ROOF.Utils.ajaxcommon();
			$('#mainForm').validate({
				rules : {
					'menu.name' : {
						required : true
					}
				}
			});
			$('#mainForm').ajaxForm(
					{
						'dataType' : 'json',
						'clearForm' : true,
						'success' : function(d) {
							ROOF.Utils.alert(d.message, function() {
								if (parent.reAsyncChildNodes) {
									parent.reAsyncChildNodes();
								}
								var node = parent.getSeletedNode();
								if (node) {
									window.location.href = ROOF.Utils
											.projectName()
											+ "/menuAction/detail.action?id="
											+ node.id;
								}
							});
						}
					});

			$('#saveBtn').click(function() {
				$('#mainForm').submit();
				return false;
			});

		});
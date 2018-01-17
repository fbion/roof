$(function() {

	var height = $(window).height();// 高度按元素需要变更
	var width = $(window).width();

	var table = new ROOF.SelectableTable($('#mail_mail_table'));
	var page = new com.sinopec.PageBar.PageBar($('#mail_mail_search_form'));
	$("#mail_mail_create_btn").click(
			function() {
				var ref = ROOF.Utils.openwindow(ROOF.Utils.projectName()
						+ "/mail/mailAction/create_page.action", "邮件管理", width,
						height, true);
				return false;
			});
	$("#mail_mail_detail_btn").click(
			function() {
				var trs = table.getSelectedTrNoClone();
				if (trs.length == 0 || trs.length > 1) {
					alert("请选择一行记录");
					return false;
				}
				var id = trs[0].find(":input[name='id']").val();
				var ref = ROOF.Utils.openwindow(ROOF.Utils.projectName()
						+ "/mail/mailAction/detail_page.action?id=" + id,
						"t_mail管理", width, height, true);
				return false;
			});
	$("#mail_mail_test_btn").click(
			function() {
				var trs = table.getSelectedTrNoClone();
				if (trs.length == 0 || trs.length > 1) {
					alert("请选择一行记录");
					return false;
				}
				var id = trs[0].find(":input[name='id']").val();
				var ref = ROOF.Utils.openwindow(ROOF.Utils.projectName()
						+ "/mail/mailAction/test_send_page.action?id=" + id,
						"测试发送", width, height, true);
				return false;
			});
	$("#mail_mail_batch_btn").click(
			function() {
				var trs = table.getSelectedTrNoClone();
				if (trs.length == 0 || trs.length > 1) {
					alert("请选择一行记录");
					return false;
				}
				var id = trs[0].find(":input[name='id']").val();
				var ref = ROOF.Utils.openwindow(ROOF.Utils.projectName()
						+ "/mail/mailAction/batch_send_page.action?id=" + id,
						"批量发送", width, height, true);
				return false;
			});
	
	$("#mail_mail_schedule_btn").click(
			function() {
				var trs = table.getSelectedTrNoClone();
				if (trs.length == 0 || trs.length > 1) {
					alert("请选择一行记录");
					return false;
				}
				var id = trs[0].find(":input[name='id']").val();
				var ref = ROOF.Utils.openwindow(ROOF.Utils.projectName()
						+ "/mail/mailAction/schedule_send_page.action?id=" + id,
						"定时发送", width, height, true);
				return false;
			});
	$("#mail_mail_update_btn").click(
			function() {
				var trs = table.getSelectedTrNoClone();
				if (trs.length == 0 || trs.length > 1) {
					alert("请选择一行记录");
					return false;
				}
				var id = trs[0].find(":input[name='id']").val();
				var ref = ROOF.Utils.openwindow(ROOF.Utils.projectName()
						+ "/mail/mailAction/update_page.action?id=" + id,
						"t_mail管理", width, height, true);
				return false;
			});
	$("#mail_mail_delete_btn").click(function() {
		var trs = table.getSelectedTrNoClone();
		if (trs.length == 0 || trs.length > 1) {
			alert("请选择一行记录");
			return false;
		}
		if (!confirm("确定删除该条记录吗？")) {
			return false;
		}
		var id = trs[0].find(":input[name='id']").val();
		$.ajax({
			async : false,
			url : basePathConst + "/mail/mailAction/delete.action",
			data : {
				"id" : id
			},
			type : 'post',
			dataType : 'json',
			cache : false,
			beforeSubmit : function(formData) {
			},
			beforeSend : function() {
			},
			complete : function() {
			},
			error : function() {
				alert('Ajax信息加载出错，请重试！');
			},
			success : function(d) {
				alert(d.message);
				window.location.reload();
			}
		});

	});
});
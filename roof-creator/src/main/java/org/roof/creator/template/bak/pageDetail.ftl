<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>查看${tableDisplay}信息</title>
<%@include file="/crm/sinopec_crm_head.jsp"%>
<script type="text/javascript" src="${includeBase}/crm/${sysName}/${alias}/${alias}_update.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		ROOF.Utils.readonlyForm($("#${sysName}_${alias}_form"));
	});
</script>
</head>

<body>
	<div class="dialog_box view_cont" id="v_c">
		<div class="title">
			查看${tableDisplay}信息
		</div>
		<form id="${sysName}_${alias}_form" action="" method="post">
			<div class="content">
				<%@include file="${alias}_form.jsp"%>
				<div class="bot">
					<input id="${sysName}_${alias}_detail_close_btn" type="button" value="取 消" class="btn btn_tj">
				</div>
			</div>
		</form>
	</div>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>修改${tableDisplay}信息</title>
<%@include file="/crm/sinopec_crm_head.jsp"%>
<script type="text/javascript" src="${includeBase}/crm/${sysName}/${alias}/${alias}_update.js"></script>
</head>

<body>
	<div class="dialog_box view_cont" id="v_c">
		<div class="title">
			修改${tableDisplay}信息
		</div>
		<form id="${sysName}_${alias}_form" action="${includeBase}/${actionName}Action/update.json" method="post">
			<div class="content">
				<%@include file="${alias}_form.jsp"%>
				<div class="bot">
					<input type="submit" value="提 交" class="btn btn_tj"> 
					<a id="${sysName}_${alias}_update_close_btn" class="cancel" href="javascript:void(0)">取消</a>
				</div>
			</div>
		</form>
	</div>
</body>
</html>

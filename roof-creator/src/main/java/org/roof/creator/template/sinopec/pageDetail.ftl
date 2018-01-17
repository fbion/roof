<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>查看${tableDisplay}信息</title>
<%@include file="/crm/sinopec_crm_head_boot.jsp"%>
<script type="text/javascript" src="${includeBase}/crm/${sysName}/${alias}/${alias}_update.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		ROOF.Utils.readonlyForm($("#${sysName}_${alias}_form"));
	});
</script>
</head>
<body>
<div class="conte">
		<form id="${sysName}_${alias}_form" action="" method="post">
		<%@include file="${alias}_form.jsp"%>
  
  <div class="container text-center form-group">
    <div class="text-center form-group"><a id="${sysName}_${alias}_detail_close_btn"  class="btn btn-default" href="javascript:void(0)">取消</a></div>
  </div>
  </form>
</div>
</body>
</html>
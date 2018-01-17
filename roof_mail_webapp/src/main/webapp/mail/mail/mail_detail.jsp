<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>查看t_mail信息</title>
<%@include file="/head_boot.jsp"%>
<script type="text/javascript" src="${basePath}/mail/mail/mail_update.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		ROOF.Utils.readonlyForm($("#mail_mail_form"));
	});
</script>
</head>
<body>
<div class="conte">
		<form id="mail_mail_form" action="" method="post">
		<%@include file="mail_form.jsp"%>
  
  <div class="container text-center form-group">
    <div class="text-center form-group"><a id="mail_mail_detail_close_btn"  class="btn btn-default btn-block" href="javascript:void(0)">取消</a></div>
  </div>
  </form>
</div>
</body>
</html>
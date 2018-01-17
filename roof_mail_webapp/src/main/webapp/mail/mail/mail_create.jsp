<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>新增邮件信息</title>
<%@include file="/head_boot.jsp"%>

<script type="text/javascript"
	src="${basePath}/mail/mail/mail_update.js"></script>
</head>
<body>
	<div class="conte">
		<form id="mail_mail_form"
			action="${basePath}/mail/mailAction/create.json" method="post">
			<%@include file="mail_form.jsp"%>
			<div class="container text-center form-group">
				<div class="col-xs-6 col-md-2 col-md-offset-4">
					<input type="submit" value="提交" class="btn btn-primary btn-block">
				</div>
				<div class="col-xs-6 col-md-2">
					<a id="mail_mail_update_close_btn" href="javascript:void(0)"
						class="btn btn-default btn-block">取消</a>
				</div>
			</div>
		</form>
	</div>
</body>
</html>
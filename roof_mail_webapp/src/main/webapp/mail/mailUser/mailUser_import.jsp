<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>导入邮件地址</title>
<%@include file="/head_boot.jsp"%>
<script type="text/javascript"
	src="${basePath}/mail/mailUser/mailUser_import.js"></script>
</head>
<body>
	<div class="conte">
		<form id="mailuser_mailUser_form"
			action="${basePath}/mail/mailuserAction/importByXls.json"
			enctype="multipart/form-data" method="post">
			<div class="panel panel-default">
				<div class="panel-body">
					<div class="col-md-3 col-xs-12 noPadding">
						<div class="form-group">
							<label class="col-xs-4 noPadding">选择文件<span class='red'>*</span>
								:
							</label>
							<div class="col-xs-8 noPadding">
								<input type="file" name="file" class="form-control input-sm">
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="container text-center form-group">
				<div class="col-xs-6 col-md-2 col-md-offset-4">
					<input type="submit" value="提交" class="btn btn-primary btn-block">
				</div>
				<div class="col-xs-6 col-md-2">
					<a id="mailuser_mailUser_update_close_btn"
						href="javascript:void(0)" class="btn btn-default btn-block">取消</a>
				</div>
			</div>
		</form>
	</div>
</body>
</html>
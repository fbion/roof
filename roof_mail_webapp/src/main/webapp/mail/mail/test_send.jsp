<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>查看t_mail信息</title>
<%@include file="/head_boot.jsp"%>
<script type="text/javascript" src="${basePath}/mail/mail/test_send.js"></script>
</head>
<body>
	<div class="conte">
		<form id="mail_mail_form"
			action="${basePath}/mail/mailAction/test_send.json" method="post">
			<input type="hidden" id="mail_id" name="id" value="${mail.id }" />
			<div class="panel panel-default">
				<div class="panel-body">
					<div class="col-md-12 col-xs-12 noPadding">
						<div class="form-group">
							<label class="col-xs-2 noPadding">收件人<span class='red'>*</span>
								:
							</label>
							<div class="col-xs-10 noPadding">
								<input type="text" id="to" maxlength="255" name="to" value=""
									class="form-control input-sm">
							</div>
						</div>
					</div>
					<div class="col-md-12 col-xs-12 noPadding">
						<div class="form-group">
							<label class="col-xs-2 noPadding">主题<span class='red'>*</span>
								:
							</label>
							<div class="col-xs-10 noPadding">
								<input type="text" id="mail_name" maxlength="255" name="name"
									value="${mail.name }" class="form-control input-sm">
							</div>
						</div>
					</div>
					<div class="col-md-12 col-xs-12 noPadding">
						<div class="form-group">
							<label class="col-xs-2 noPadding">内容<span class='red'>*</span>
								:
							</label>
							<div class="col-xs-10 noPadding">
								<script id="content" name="content" type="text/plain"
									height="height">${mail.content }</script>
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
					<a id="mail_mail_update_close_btn" href="javascript:void(0)"
						class="btn btn-default btn-block">取消</a>
				</div>
			</div>
		</form>
	</div>
	<script type="text/javascript"
		src="${basePath }/ueditor/ueditor.config.js"></script>
	<script type="text/javascript"
		src="${basePath }/ueditor/ueditor.all.js"></script>
	<script type="text/javascript">
		var ue = UE.getEditor('content', {
			height : 800
		});
	</script>
</body>
</html>
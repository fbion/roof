<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>订阅</title>
<%@include file="/head_boot.jsp"%>
</head>
<body>
	<div class="conte">
		<div class="panel panel-default" style="display: none;">
			<div class="panel-body">
				<div class="col-md-3 col-xs-12 noPadding">
					<div class="form-group">
						<label class="col-xs-6 noPadding">请输入邮箱<span class='red'>*</span>
							:
						</label>
						<div class="col-xs-6 noPadding">
							<input type="text" id="mailMD5" maxlength="255" name="mailMD5"
								value="${mailMD5 }" class="form-control input-sm">
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="container text-center form-group">
			<div class="col-xs-6 col-md-2 col-md-offset-4">
				<input id="submit" type="submit" value="退订邮件"
					class="btn btn-primary btn-block">
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$(function() {
			$("#submit").click(
					function() {
						var url = ROOF.Utils.projectName()
								+ "/mail/mailuserAction/cancel/"
								+ $("#mailMD5").val() + ".json";
						$.post(url, {}, function(d) {
							alert(d.message);
						});
					});
		})
	</script>
</body>
</html>
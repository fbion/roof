<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>邮件管理</title>
<%@include file="/head_boot.jsp"%>
<script type="text/javascript"
	src="${basePath}/mail/mailUser/mailUser_list.js"></script>
</head>
<body>
	<div class="wrap">
		<!--右侧内容-->
		<div class="col-xs-12 main">
			<div class="panel panel-primary">
				<div class="panel-heading">邮件用户列表</div>
				<div class="panel-body">
					<form id="mailuser_mailUser_search_form" method="post"
						action="${basePath}/mail/mailuserAction/list.action">
						<%@include file="/page_bar_cond.jsp"%>
						<div class="section row">
							<div class="col-md-10 col-xs-12 noPadding">
								<div class="col-md-3 col-xs-12 noPadding">
									<div class="form-group">
										<label class="col-xs-4 noPadding">用户名:</label>
										<div class="col-xs-8 noPadding">
											<input id="mailUser_username" name="username" type="text"
												class="form-control input-sm" value="${mailUser.username }">
										</div>
									</div>
								</div>
								<div class="col-md-3 col-xs-12 noPadding">
									<div class="form-group">
										<label class="col-xs-4 noPadding">邮箱:</label>
										<div class="col-xs-8 noPadding">
											<input id="mailUser_mail" name="mail" type="text"
												class="form-control input-sm" value="${mailUser.mail }">
										</div>
									</div>
								</div>
								<div class="col-md-3 col-xs-12 noPadding">
									<div class="form-group">
										<label class="col-xs-4 noPadding">订阅:</label>
										<div class="col-xs-8 noPadding">
											<select d="mailUser_enabled" name="enabled"
												class="form-control input-sm">
												<c:choose>
													<c:when test="${mailUser.enabled == true }">
														<option value="true" selected="selected">是</option>
													</c:when>
													<c:otherwise>
														<option value="true">是</option>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${mailUser.enabled == false }">
														<option value="false" selected="selected">否</option>
													</c:when>
													<c:otherwise>
														<option value="false">否</option>
													</c:otherwise>
												</c:choose>
											</select>
										</div>
									</div>
								</div>
							</div>
							<div class="col-md-2 col-xs-12 noPadding">
								<div class="form-group">
									<input type="submit" value="查询"
										class="btn btn-primary btn-sm btn-block">
								</div>
							</div>
						</div>
					</form>

					<div class="section row">
						<div class=" well well-sm"
							style="margin-bottom: 0; background: #ffffff; border: 0;">
							<a id="mailuser_mailUser_detail_btn"
								class="btn btn-sm btn-primary" href="javascript:void(0)"> <span
								class="glyphicon glyphicon-list-alt"></span> 查看
							</a> <a id="mailuser_mailUser_create_btn"
								class="btn btn-sm btn-primary" href="javascript:void(0)"> <span
								class="glyphicon glyphicon-plus"></span> 新建
							</a> <a id="mailuser_mailUser_update_btn"
								class="btn btn-sm btn-primary" href="javascript:void(0)"> <span
								class="glyphicon glyphicon-edit"></span> 修改
							</a> <a id="mailuser_mailUser_delete_btn"
								class="btn btn-sm btn-primary" href="javascript:void(0)"> <span
								class="glyphicon glyphicon-remove"></span> 删除
							</a> <a id="mailuser_mailUser_import_btn"
								class="btn btn-sm btn-primary" href="javascript:void(0)"> <span
								class="glyphicon glyphicon-remove"></span> 导入
							</a>
						</div>
					</div>
					<div class="table-responsive">
						<table class="table table-hover table-condensed"
							id="mailuser_mailUser_table">
							<thead>
								<tr class='active'>
									<th>选择</th>
									<th>用户名</th>
									<th>邮箱</th>
									<th>订阅</th>
									<th>失败次数</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="mailUser" items="${page.dataList }"
									varStatus="status">
									<tr id="maintr">
										<td><input type="checkbox" name="id" class="ck"
											value="${mailUser.id }"
											${status.count==1?'checked="true"':'' }></td>
										<td>${mailUser.username }</td>
										<td>${mailUser.mail }</td>
										<td>${mailUser.enabled == true? '是':'否'}</td>
										<td>${mailUser.fail_count }</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<%@include file="/page_bar_boot.jsp"%>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
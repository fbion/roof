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
<script type="text/javascript" src="${basePath}/mail/mail/mail_list.js"></script>
</head>
<body>
	<div class="wrap">
		<!--右侧内容-->
		<div class="col-xs-12 main">
			<div class="panel panel-primary">
				<div class="panel-heading">邮件列表</div>
				<div class="panel-body">
					<form id="mail_mail_search_form" method="post"
						action="${basePath}/mail/mailAction/list.action">
						<%@include file="/page_bar_cond.jsp"%>
						<div class="section row">
							<div class="col-md-10 col-xs-12 noPadding">
								<div class="col-md-3 col-xs-12 noPadding">
									<div class="form-group">
										<label class="col-xs-4 noPadding">名称:</label>
										<div class="col-xs-8 noPadding">
											<input id="mail_name" maxlength="255" name="name" type="text"
												class="form-control input-sm" value="${mail.name }">
										</div>
									</div>
								</div>
								<div class="col-md-3 col-xs-12 noPadding">
									<div class="form-group">
										<label class="col-xs-4 noPadding">状态:</label>
										<div class="col-xs-8 noPadding">

											<select id="mail_stat" name="stat"
												class="form-control input-sm">
												<c:choose>
													<c:when test="${mail.stat == 0 }">
														<option value="0" selected="selected">新建</option>
													</c:when>
													<c:otherwise>
														<option value="0">新建</option>
													</c:otherwise>
												</c:choose>

												<c:choose>
													<c:when test="${mail.stat == 1 }">
														<option value="1" selected="selected">发送中</option>
													</c:when>
													<c:otherwise>
														<option value="1">发送中</option>
													</c:otherwise>
												</c:choose>

												<c:choose>
													<c:when test="${mail.stat == 2 }">
														<option value="2" selected="selected">发送完成</option>
													</c:when>
													<c:otherwise>
														<option value="2">发送完成</option>
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
							<a id="mail_mail_detail_btn" class="btn btn-sm btn-primary"
								href="javascript:void(0)"> <span
								class="glyphicon glyphicon-list-alt"></span> 查看
							</a> <a id="mail_mail_create_btn" class="btn btn-sm btn-primary"
								href="javascript:void(0)"> <span
								class="glyphicon glyphicon-plus"></span> 新建
							</a> <a id="mail_mail_update_btn" class="btn btn-sm btn-primary"
								href="javascript:void(0)"> <span
								class="glyphicon glyphicon-edit"></span> 修改
							</a> <a id="mail_mail_delete_btn" class="btn btn-sm btn-primary"
								href="javascript:void(0)"> <span
								class="glyphicon glyphicon-remove"></span> 删除
							</a> <a id="mail_mail_test_btn" class="btn btn-sm btn-primary"
								href="javascript:void(0)"> <span
								class="glyphicon glyphicon-remove"></span> 测试发送
							</a> <a id="mail_mail_batch_btn" class="btn btn-sm btn-primary"
								href="javascript:void(0)"> <span
								class="glyphicon glyphicon-remove"></span> 立即发送
							</a> <a id="mail_mail_schedule_btn" class="btn btn-sm btn-primary"
								href="javascript:void(0)"> <span
								class="glyphicon glyphicon-remove"></span> 定时发送
							</a>
						</div>
					</div>
					<div class="table-responsive">
						<table class="table table-hover table-condensed"
							id="mail_mail_table">
							<thead>
								<tr class='active'>
									<th>选择</th>
									<th>主题</th>
									<th>发送次数</th>
									<th>状态</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="mail" items="${page.dataList }"
									varStatus="status">
									<tr>
										<td><input type="checkbox" name="id" class="ck"
											value="${mail.id }"></td>
										<td>${mail.name }</td>
										<td>${mail.send_count }</td>
										<td><c:choose>
												<c:when test="${mail.stat == 0 }">新建</c:when>
												<c:when test="${mail.stat == 1 }">发送中</c:when>
												<c:when test="${mail.stat == 2 }">发送完成</c:when>
											</c:choose></td>
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
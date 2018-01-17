<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<script type="text/javascript">
$(document).ready(function(){
	var currP = ${page.currentPage };
	var currC = ${page.pageCount };
	if(currP > currC){
		dispatcherPage(1);
	}
});
	function prePage(){
		var c = ${page.currentPage - 1};
		$("#${pageForm}_currentPage").val(c);
		goPage();
	}
	function nextPage(){
		var c = ${page.currentPage + 1};
		$("#${pageForm}_currentPage").val(c);
		goPage();
	}
	function goPage(){
		var c = $("#${pageForm}_currentPage").val();
		if(isNaN(c)){
			c = 1;
		}
		if(c > ${page.pageCount }){
			c = ${page.pageCount };
		}
		dispatcherPage(c);
	}
	function dispatcherPage(pageIndex){
		if(isNaN(pageIndex)){
			pageIndex = 1;
		}
		if(pageIndex < 1){
			pageIndex = 1;
		}
		if(pageIndex > ${page.pageCount }){
			pageIndex = ${page.pageCount };
		}
		$("#${pageForm}_currentPage").val(pageIndex);
		if(pageIndex == ${page.currentPage }){
			return;
		}
		if(pageIndex == 0){
			return;
		}
		var formId = "${pageForm}";
		var pageUrl = "${pageUrl}";
		var formObj = $("form").eq(0);
		if(formId){
			formObj = $("#${pageForm}");
		}
		if(pageUrl){
			formObj.attr("action",pageUrl);
		}
		formObj.submit();
	}
</script>
<div class="ui-pagebox">
	<div class="pagination">
	<input type="hidden" id="${pageForm}_limit" name="limit" value="${page.limit }">
	<input type="hidden" id="${pageForm}_currentPage" value="${page.currentPage }" name="currentPage" />
		<c:choose>
			<c:when test="${page.currentPage <= 1 }">
				<span class="disabled">&lt;&lt;上一页</span>
			</c:when>
			<c:otherwise>
				<a href="javascript:void(0)" onclick="prePage()">&lt;&lt;上一页</a>
			</c:otherwise>
		</c:choose>
		<c:forEach var="item" varStatus="status" begin="${pageBar.pageStart}" end="${pageBar.pageEnd}">
			<c:if test="${status.index == page.currentPage}">
				<span class="current">${page.currentPage }</span>
			</c:if>
			<c:if test="${status.index != page.currentPage}">
				<a href="javascript:void(0)" onclick="dispatcherPage(${status.index})">${status.index}</a>
			</c:if>
		</c:forEach>
		<c:choose>
			<c:when test="${page.currentPage >= page.pageCount }">
				<span class="disabled">下一页 >></span>
			</c:when>
			<c:otherwise>
				<a href="javascript:void(0)" onclick="nextPage()">下一页 >></a>
			</c:otherwise>
		</c:choose>
		&nbsp;&nbsp;总共<span class="">${page.total }</span>条记录
	</div>
</div>
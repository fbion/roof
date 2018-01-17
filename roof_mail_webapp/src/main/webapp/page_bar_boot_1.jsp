<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="text-center">
	<nav>
		<ul class="pagination pagination-sm">

	<c:choose>
		<c:when test="${page.currentPage > 1 }">
			<li><a href="javascript:void(0)"  id="pre_page_li"  aria-label="Previous">
			<span aria-hidden="true">上页</span>
			</a></li>
		</c:when>
		<c:otherwise>
			<li class="disabled"><a href="javascript:void(0)"  aria-label="Previous">
			<span aria-hidden="true">上页</span>
			</a></li>
		</c:otherwise>
	</c:choose>

	<c:forEach var="a" begin="${pageStart }" end="${pageEnd }">
		<c:choose>
			<c:when test="${a== page.currentPage}">
				<li class="active"><a name="cur_page_li" href="javascript:void(0)"> ${a } <span class="sr-only">(current)</span>
			</a></li>
			</c:when>
			<c:otherwise>
				<li><a  name="page_li" href="javascript:void(0)">${a }</a></li>
			</c:otherwise>
		</c:choose>
	</c:forEach>
	
	<c:choose>
		<c:when test="${page.currentPage < page.pageCount }">
			<li><a  id="next_page_li"  href="javascript:void(0)" aria-label="Next"> <span aria-hidden="true">下页</span>
			</a></li>
		</c:when>
		<c:otherwise>
			<li class="disabled"><a  href="javascript:void(0)" aria-label="Next"> <span aria-hidden="true">下页</span>
			</a></li>
		</c:otherwise>
	</c:choose>
			
		</ul>
	</nav>
</div>
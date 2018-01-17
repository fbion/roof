<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<nav style="padding: 0 15px;">
	<ul class="pagination pagination-sm" style="float: right;">


		<c:choose>
			<c:when test="${page.currentPage > 1 }">
				<li><a id="pre_page_li" href="javascript:void(0)"
					aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
				</a></li>
			</c:when>
			<c:otherwise>
				<li class="disabled"><a href="javascript:void(0)"
					aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
				</a></li>
			</c:otherwise>
		</c:choose>

		<c:forEach var="a" begin="${pageStart }" end="${pageEnd }">
			<c:choose>
				<c:when test="${a== page.currentPage}">
					<li class="active"><a href="javascript:void(0)"
						name="cur_page_li">${a }</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="javascript:void(0)" name="page_li">${a }</a></li>
				</c:otherwise>
			</c:choose>
		</c:forEach>

		<c:choose>
			<c:when test="${page.currentPage < page.pageCount }">
				<li><a id="next_page_li" href="javascript:void(0)"
					aria-label="Next"> <span aria-hidden="true">&raquo;</span>
				</a></li>
			</c:when>
			<c:otherwise>
				<li class="disabled"><a href="javascript:void(0)"
					aria-label="Next"> <span aria-hidden="true">&raquo;</span>
				</a></li>
			</c:otherwise>
		</c:choose>

	</ul>
</nav>
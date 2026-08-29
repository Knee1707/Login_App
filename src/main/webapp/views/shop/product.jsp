<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!doctype html>
<html lang="vi">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Tất cả sản phẩm - MyShop</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
	<%@ include file="navbar.jspf" %>

	<div class="container" style="max-width:1080px;">
		<h1 class="section-title">Tất cả sản phẩm</h1>

		<c:if test="${empty listproduct}">
			<div class="alert alert-info">Chưa có sản phẩm nào.</div>
		</c:if>

		<div class="grid">
			<c:forEach items="${listproduct}" var="p">
				<%@ include file="product-card.jspf" %>
			</c:forEach>
		</div>

		<c:if test="${totalPages > 1}">
			<div class="pagination">
				<c:choose>
					<c:when test="${currentPage > 1}">
						<a href="${pageContext.request.contextPath}/product?page=${currentPage - 1}">‹</a>
					</c:when>
					<c:otherwise><span class="disabled">‹</span></c:otherwise>
				</c:choose>

				<c:forEach begin="1" end="${totalPages}" var="i">
					<c:choose>
						<c:when test="${i == currentPage}"><span class="active">${i}</span></c:when>
						<c:otherwise><a href="${pageContext.request.contextPath}/product?page=${i}">${i}</a></c:otherwise>
					</c:choose>
				</c:forEach>

				<c:choose>
					<c:when test="${currentPage < totalPages}">
						<a href="${pageContext.request.contextPath}/product?page=${currentPage + 1}">›</a>
					</c:when>
					<c:otherwise><span class="disabled">›</span></c:otherwise>
				</c:choose>
			</div>
		</c:if>
	</div>
</body>
</html>

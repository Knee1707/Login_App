<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!doctype html>
<html lang="vi">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Trang chủ - MyShop</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
	<%@ include file="navbar.jspf" %>

	<div class="container" style="max-width:1080px;">
		<h1 class="section-title">Sản phẩm mới nhất</h1>

		<c:if test="${empty listnewest}">
			<div class="alert alert-info">Chưa có sản phẩm nào. Hãy vào trang <a href="${pageContext.request.contextPath}/admin/products">Quản lý</a> để thêm.</div>
		</c:if>

		<div class="grid">
			<c:forEach items="${listnewest}" var="p">
				<%@ include file="product-card.jspf" %>
			</c:forEach>
		</div>

		<c:if test="${not empty listnewest}">
			<div style="text-align:center; margin-top:26px;">
				<a class="btn btn-secondary" href="${pageContext.request.contextPath}/product">Xem tất cả sản phẩm →</a>
			</div>
		</c:if>
	</div>
</body>
</html>

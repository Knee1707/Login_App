<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value="vi_VN" />
<!doctype html>
<html lang="vi">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>${prod.productName} - MyShop</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
	<%@ include file="navbar.jspf" %>

	<div class="container" style="max-width:1000px;">
		<div class="auth-links" style="text-align:left; margin:0 0 14px;">
			<a href="${pageContext.request.contextPath}/product">← Quay lại danh sách</a>
		</div>

		<c:choose>
			<c:when test="${fn:startsWith(prod.image, 'https')}"><c:set var="dimg" value="${prod.image}" /></c:when>
			<c:otherwise><c:url value="/image?fname=${prod.image}" var="dimg" /></c:otherwise>
		</c:choose>

		<div class="card">
			<div class="detail">
				<div>
					<img class="detail-img" src="${dimg}" alt="${prod.productName}" />
				</div>
				<div>
					<h1 style="margin-top:0;">${prod.productName}</h1>
					<div class="detail-price"><fmt:formatNumber value="${prod.price}" type="number" maxFractionDigits="0" /> đ</div>
					<dl>
						<dt>Danh mục</dt><dd>${prod.category.categoryname}</dd>
						<dt>Số lượng</dt><dd>${prod.quantity}</dd>
						<dt>Trạng thái</dt>
						<dd>
							<c:choose>
								<c:when test="${prod.status == 1}"><span class="badge badge-on">Còn hàng</span></c:when>
								<c:otherwise><span class="badge badge-off">Ngừng bán</span></c:otherwise>
							</c:choose>
						</dd>
					</dl>
					<div class="detail-desc">
						<b>Mô tả:</b><br/>
						<c:choose>
							<c:when test="${empty prod.description}"><span class="muted">(Chưa có mô tả)</span></c:when>
							<c:otherwise>${prod.description}</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>

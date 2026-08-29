<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!doctype html>
<html lang="vi">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Quản lý sản phẩm</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
	<div class="container">
		<div class="card">
			<div class="toolbar">
				<h2>Quản lý sản phẩm</h2>
				<div>
					<a class="btn btn-secondary btn-sm" href="${pageContext.request.contextPath}/admin/categories">Danh mục</a>
					<a class="btn btn-secondary btn-sm" href="${pageContext.request.contextPath}/home">Trang chủ</a>
					<a class="btn btn-secondary btn-sm" href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
					<a class="btn btn-success" href="${pageContext.request.contextPath}/admin/product/add">+ Thêm sản phẩm</a>
				</div>
			</div>

			<div class="table-wrap">
			<table class="table">
				<thead>
					<tr>
						<th>STT</th>
						<th>Ảnh</th>
						<th>Tên sản phẩm</th>
						<th>Giá</th>
						<th>SL</th>
						<th>Danh mục</th>
						<th>Trạng thái</th>
						<th>Thao tác</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${listproduct}" var="p" varStatus="STT">
						<c:choose>
							<c:when test="${fn:startsWith(p.image, 'https')}"><c:set var="imgUrl" value="${p.image}" /></c:when>
							<c:otherwise><c:url value="/image?fname=${p.image}" var="imgUrl" /></c:otherwise>
						</c:choose>
						<tr>
							<td>${STT.index + 1}</td>
							<td><img src="${imgUrl}" alt="img" /></td>
							<td>${p.productName}</td>
							<td><fmt:formatNumber value="${p.price}" type="number" maxFractionDigits="0" /> đ</td>
							<td>${p.quantity}</td>
							<td>${p.category.categoryname}</td>
							<td>
								<c:choose>
									<c:when test="${p.status == 1}"><span class="badge badge-on">Hoạt động</span></c:when>
									<c:otherwise><span class="badge badge-off">Khóa</span></c:otherwise>
								</c:choose>
							</td>
							<td>
								<a class="btn btn-primary btn-sm" href="<c:url value='/admin/product/edit?id=${p.productId}'/>">Sửa</a>
								<a class="btn btn-danger btn-sm" href="<c:url value='/admin/product/delete?id=${p.productId}'/>"
									onclick="return confirm('Bạn chắc chắn muốn xóa?');">Xóa</a>
							</td>
						</tr>
					</c:forEach>
					<c:if test="${empty listproduct}">
						<tr><td colspan="8" class="muted">Chưa có sản phẩm nào.</td></tr>
					</c:if>
				</tbody>
			</table>
			</div>
		</div>
	</div>
</body>
</html>

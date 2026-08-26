<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!doctype html>
<html lang="vi">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Danh sách danh mục (JPA)</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
	<div class="container">
		<div class="card">
			<div class="toolbar">
				<h2>Quản lý danh mục (JPA)</h2>
				<a class="btn btn-success" href="${pageContext.request.contextPath}/admin/category/add">+ Thêm danh mục</a>
			</div>

			<div class="table-wrap">
			<table class="table">
				<thead>
					<tr>
						<th>STT</th>
						<th>Ảnh</th>
						<th>Tên danh mục</th>
						<th>Trạng thái</th>
						<th>Thao tác</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${listcate}" var="cate" varStatus="STT">
						<c:choose>
							<c:when test="${fn:startsWith(cate.images, 'https')}">
								<c:set var="imgUrl" value="${cate.images}" />
							</c:when>
							<c:otherwise>
								<c:url value="/image?fname=${cate.images}" var="imgUrl" />
							</c:otherwise>
						</c:choose>
						<tr>
							<td>${STT.index + 1}</td>
							<td><img src="${imgUrl}" alt="img" /></td>
							<td>${cate.categoryname}</td>
							<td>
								<c:choose>
									<c:when test="${cate.status == 1}"><span class="badge badge-on">Hoạt động</span></c:when>
									<c:otherwise><span class="badge badge-off">Khóa</span></c:otherwise>
								</c:choose>
							</td>
							<td>
								<a class="btn btn-primary btn-sm" href="<c:url value='/admin/category/edit?id=${cate.categoryid}'/>">Sửa</a>
								<a class="btn btn-danger btn-sm" href="<c:url value='/admin/category/delete?id=${cate.categoryid}'/>"
									onclick="return confirm('Bạn chắc chắn muốn xóa?');">Xóa</a>
							</td>
						</tr>
					</c:forEach>
					<c:if test="${empty listcate}">
						<tr><td colspan="5" class="muted">Chưa có danh mục nào.</td></tr>
					</c:if>
				</tbody>
			</table>
			</div>
		</div>
	</div>
</body>
</html>

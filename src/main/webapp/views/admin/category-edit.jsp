<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!doctype html>
<html lang="vi">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Sửa danh mục</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
	<div class="container container--narrow">
		<div class="card">
			<h2>Sửa danh mục</h2>
			<c:if test="${not empty errors}">
				<div class="alert alert-error">Vui lòng kiểm tra lại thông tin.</div>
			</c:if>
			<form action="${pageContext.request.contextPath}/admin/category/update" method="post" enctype="multipart/form-data">
				<input type="hidden" name="categoryid" value="${cate.categoryid}" />
				<div class="form-group">
					<label>Tên danh mục</label>
					<input class="form-control" type="text" name="categoryname" value="${cate.categoryname}" />
					<c:if test="${not empty errors.categoryname}">
						<small style="color:#c0392b">${errors.categoryname}</small>
					</c:if>
				</div>
				<div class="form-group">
					<label>Link ảnh (URL)</label>
					<input class="form-control" type="text" name="images" value="${cate.images}" />
				</div>
				<div class="form-group">
					<label>Ảnh hiện tại</label><br>
					<c:choose>
						<c:when test="${fn:startsWith(cate.images, 'https')}">
							<c:set var="imgUrl" value="${cate.images}" />
						</c:when>
						<c:otherwise>
							<c:url value="/image?fname=${cate.images}" var="imgUrl" />
						</c:otherwise>
					</c:choose>
					<img src="${imgUrl}" alt="img" style="max-height:100px;border-radius:8px" />
				</div>
				<div class="form-group">
					<label>Upload ảnh mới (để trống nếu giữ nguyên)</label>
					<input class="form-control" type="file" name="images1" accept="image/*" />
				</div>
				<div class="form-group">
					<label>Trạng thái</label>
					<div class="radio-row">
						<input type="radio" id="ston" name="status" value="1" ${cate.status == 1 ? 'checked' : ''} />
						<label for="ston">Hoạt động</label>
					</div>
					<div class="radio-row">
						<input type="radio" id="stoff" name="status" value="0" ${cate.status != 1 ? 'checked' : ''} />
						<label for="stoff">Khóa</label>
					</div>
				</div>
				<button type="submit" class="btn btn-primary">Cập nhật</button>
				<a class="btn btn-secondary" href="${pageContext.request.contextPath}/admin/categories">Hủy</a>
			</form>
		</div>
	</div>
</body>
</html>

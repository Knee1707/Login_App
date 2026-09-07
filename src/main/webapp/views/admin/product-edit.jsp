<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!doctype html>
<html lang="vi">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Sửa sản phẩm</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
	<div class="container container--narrow">
		<div class="card">
			<h2>Sửa sản phẩm</h2>
			<c:if test="${not empty errors}">
				<div class="alert alert-error">Vui lòng kiểm tra lại thông tin.</div>
			</c:if>
			<form action="${pageContext.request.contextPath}/admin/product/update" method="post" enctype="multipart/form-data">
				<input type="hidden" name="productid" value="${prod.productId}" />
				<div class="form-group">
					<label>Tên sản phẩm</label>
					<input class="form-control" type="text" name="productname" value="${prod.productName}" />
					<c:if test="${not empty errors.productName}"><small style="color:#c0392b">${errors.productName}</small></c:if>
				</div>
				<div class="form-group">
					<label>Giá (đ)</label>
					<input class="form-control" type="number" name="price" min="0" step="1000" value="${prod.price}" required />
				</div>
				<div class="form-group">
					<label>Số lượng</label>
					<input class="form-control" type="number" name="quantity" min="0" value="${prod.quantity}" required />
				</div>
				<div class="form-group">
					<label>Danh mục</label>
					<select class="form-control" name="categoryid" required>
						<c:forEach items="${listcate}" var="cate">
							<option value="${cate.categoryid}" ${prod.category != null && prod.category.categoryid == cate.categoryid ? 'selected' : ''}>${cate.categoryname}</option>
						</c:forEach>
					</select>
				</div>
				<div class="form-group">
					<label>Mô tả</label>
					<textarea class="form-control" name="description" rows="4">${prod.description}</textarea>
				</div>

				<div class="form-group">
					<label>Ảnh hiện tại</label><br/>
					<c:choose>
						<c:when test="${fn:startsWith(prod.image, 'https')}"><c:set var="curImg" value="${prod.image}" /></c:when>
						<c:otherwise><c:url value="/image?fname=${prod.image}" var="curImg" /></c:otherwise>
					</c:choose>
					<img src="${curImg}" alt="img" style="max-height:90px; border-radius:8px;" />
				</div>
				<div class="form-group">
					<label>Link ảnh mới (URL, tùy chọn)</label>
					<input class="form-control" type="text" name="images" placeholder="https://... (để trống nếu giữ nguyên)" />
				</div>
				<div class="form-group">
					<label>Hoặc upload ảnh mới</label>
					<input class="form-control" type="file" name="images1" accept="image/*" />
				</div>

				<div class="form-group">
					<label>Trạng thái</label>
					<div class="radio-row">
						<input type="radio" id="ston" name="status" value="1" ${prod.status == 1 ? 'checked' : ''} />
						<label for="ston">Hoạt động</label>
					</div>
					<div class="radio-row">
						<input type="radio" id="stoff" name="status" value="0" ${prod.status == 0 ? 'checked' : ''} />
						<label for="stoff">Khóa</label>
					</div>
				</div>
				<button type="submit" class="btn btn-primary">Cập nhật</button>
				<a class="btn btn-secondary" href="${pageContext.request.contextPath}/admin/products">Hủy</a>
			</form>
		</div>
	</div>
</body>
</html>

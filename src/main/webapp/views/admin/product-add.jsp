<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="vi">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Thêm sản phẩm</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
	<div class="container container--narrow">
		<div class="card">
			<h2>Thêm sản phẩm mới</h2>
			<c:if test="${not empty errors}">
				<div class="alert alert-error">Vui lòng kiểm tra lại thông tin.</div>
			</c:if>
			<form action="${pageContext.request.contextPath}/admin/product/insert" method="post" enctype="multipart/form-data">
				<div class="form-group">
					<label>Tên sản phẩm</label>
					<input class="form-control" type="text" name="productname" value="${prod.productName}" />
					<c:if test="${not empty errors.productName}"><small style="color:#c0392b">${errors.productName}</small></c:if>
				</div>
				<div class="form-group">
					<label>Giá (đ)</label>
					<input class="form-control" type="number" name="price" min="0" step="1000" value="${empty prod ? 0 : prod.price}" />
					<c:if test="${not empty errors.price}"><small style="color:#c0392b">${errors.price}</small></c:if>
				</div>
				<div class="form-group">
					<label>Số lượng</label>
					<input class="form-control" type="number" name="quantity" min="0" value="${empty prod ? 0 : prod.quantity}" />
					<c:if test="${not empty errors.quantity}"><small style="color:#c0392b">${errors.quantity}</small></c:if>
				</div>
				<div class="form-group">
					<label>Danh mục</label>
					<select class="form-control" name="categoryid">
						<option value="">-- Chọn danh mục --</option>
						<c:forEach items="${listcate}" var="cate">
							<option value="${cate.categoryid}" ${prod.category != null && prod.category.categoryid == cate.categoryid ? 'selected' : ''}>${cate.categoryname}</option>
						</c:forEach>
					</select>
					<c:if test="${not empty errors.category}"><small style="color:#c0392b">${errors.category}</small></c:if>
				</div>
				<div class="form-group">
					<label>Mô tả</label>
					<textarea class="form-control" name="description" rows="4">${prod.description}</textarea>
				</div>
				<div class="form-group">
					<label>Link ảnh (URL, tùy chọn)</label>
					<input class="form-control" type="text" name="images" placeholder="https://..." />
				</div>
				<div class="form-group">
					<label>Hoặc upload ảnh</label>
					<input class="form-control" type="file" name="images1" accept="image/*" />
				</div>
				<div class="form-group">
					<label>Trạng thái</label>
					<div class="radio-row">
						<input type="radio" id="ston" name="status" value="1" checked />
						<label for="ston">Hoạt động</label>
					</div>
					<div class="radio-row">
						<input type="radio" id="stoff" name="status" value="0" />
						<label for="stoff">Khóa</label>
					</div>
				</div>
				<button type="submit" class="btn btn-success">Thêm</button>
				<a class="btn btn-secondary" href="${pageContext.request.contextPath}/admin/products">Hủy</a>
			</form>
		</div>
	</div>
</body>
</html>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="vi">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Thêm danh mục</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
	<div class="container container--narrow">
		<div class="card">
			<h2>Thêm danh mục mới</h2>
			<form action="${pageContext.request.contextPath}/admin/category/insert" method="post" enctype="multipart/form-data">
				<div class="form-group">
					<label>Tên danh mục</label>
					<input class="form-control" type="text" name="categoryname" required />
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
				<a class="btn btn-secondary" href="${pageContext.request.contextPath}/admin/categories">Hủy</a>
			</form>
		</div>
	</div>
</body>
</html>

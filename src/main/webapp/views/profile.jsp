<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="vi">
<head>
	<meta charset="UTF-8">
	<title>Hồ sơ cá nhân</title>
</head>
<body>
<div class="row justify-content-center">
	<div class="col-lg-7">
		<h1 class="h3 mb-4">Hồ sơ cá nhân</h1>

		<c:if test="${not empty flash}">
			<div class="alert alert-success"><i class="bi bi-check-circle"></i> ${flash}</div>
		</c:if>
		<c:if test="${not empty errors}">
			<div class="alert alert-danger"><i class="bi bi-exclamation-triangle"></i> Vui lòng kiểm tra lại thông tin.</div>
		</c:if>

		<div class="card shadow-sm">
			<div class="card-body">
				<div class="d-flex align-items-center mb-4">
					<c:choose>
						<c:when test="${not empty user.avatar}">
							<img src="${pageContext.request.contextPath}/image?fname=${user.avatar}"
							     alt="Avatar" class="rounded-circle border"
							     style="width:96px;height:96px;object-fit:cover;">
						</c:when>
						<c:otherwise>
							<span class="d-inline-flex align-items-center justify-content-center rounded-circle bg-secondary-subtle border text-secondary"
							      style="width:96px;height:96px;">
								<i class="bi bi-person" style="font-size:2.5rem;"></i>
							</span>
						</c:otherwise>
					</c:choose>
					<div class="ms-3">
						<div class="fw-semibold fs-5">${empty user.fullname ? user.email : user.fullname}</div>
						<div class="text-muted small">${user.email}</div>
					</div>
				</div>

				<form action="${pageContext.request.contextPath}/profile" method="post"
				      enctype="multipart/form-data" novalidate>

					<div class="mb-3">
						<label class="form-label">Email</label>
						<input type="text" class="form-control" value="${user.email}" disabled>
						<div class="form-text">Email đăng nhập không thể thay đổi.</div>
					</div>

					<div class="mb-3">
						<label for="fullname" class="form-label">Họ tên <span class="text-danger">*</span></label>
						<input type="text" id="fullname" name="fullname"
						       class="form-control ${not empty errors.fullname ? 'is-invalid' : ''}"
						       value="${user.fullname}" maxlength="100" placeholder="Nguyễn Văn A">
						<c:if test="${not empty errors.fullname}">
							<div class="invalid-feedback">${errors.fullname}</div>
						</c:if>
					</div>

					<div class="mb-3">
						<label for="phone" class="form-label">Số điện thoại <span class="text-danger">*</span></label>
						<input type="text" id="phone" name="phone"
						       class="form-control ${not empty errors.phone ? 'is-invalid' : ''}"
						       value="${user.phone}" maxlength="20" placeholder="0901234567">
						<c:if test="${not empty errors.phone}">
							<div class="invalid-feedback">${errors.phone}</div>
						</c:if>
					</div>

					<div class="mb-3">
						<label for="avatar" class="form-label">Ảnh đại diện</label>
						<input type="file" id="avatar" name="avatar" accept="image/*"
						       class="form-control ${not empty errors.avatar ? 'is-invalid' : ''}">
						<div class="form-text">Chấp nhận JPG, PNG, GIF; tối đa 2MB. Để trống nếu không đổi ảnh.</div>
						<c:if test="${not empty errors.avatar}">
							<div class="invalid-feedback d-block">${errors.avatar}</div>
						</c:if>
					</div>

					<div class="d-flex gap-2">
						<button type="submit" class="btn btn-primary"><i class="bi bi-save"></i> Lưu thay đổi</button>
						<a href="${pageContext.request.contextPath}/home" class="btn btn-outline-secondary">Hủy</a>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
</body>
</html>

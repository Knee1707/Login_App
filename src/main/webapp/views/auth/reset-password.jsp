<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="vi">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Đặt lại mật khẩu</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
	<div class="auth-wrap">
		<div class="card">
			<h2>Đặt lại mật khẩu</h2>
			<p class="auth-sub">Nhập OTP đã gửi tới <b>${email}</b> và mật khẩu mới.</p>

			<c:if test="${not empty flash}"><div class="alert alert-success">${flash}</div></c:if>
			<c:if test="${not empty error}"><div class="alert alert-error">${error}</div></c:if>
			<c:if test="${not empty devOtp}"><div class="alert alert-info">Chế độ dev (chưa cấu hình email): mã OTP là <code>${devOtp}</code></div></c:if>

			<form action="${pageContext.request.contextPath}/reset-password" method="post">
				<input type="hidden" name="email" value="${email}" />
				<div class="form-group">
					<label>Mã OTP</label>
					<input class="form-control" type="text" name="otp" maxlength="6" pattern="[0-9]{6}" required />
				</div>
				<div class="form-group">
					<label>Mật khẩu mới</label>
					<input class="form-control" type="password" name="password" required />
				</div>
				<div class="form-group">
					<label>Nhập lại mật khẩu mới</label>
					<input class="form-control" type="password" name="confirm" required />
				</div>
				<button type="submit" class="btn btn-success btn-block">Đặt lại mật khẩu</button>
			</form>

			<div class="auth-links"><a href="${pageContext.request.contextPath}/login">← Về đăng nhập</a></div>
		</div>
	</div>
</body>
</html>

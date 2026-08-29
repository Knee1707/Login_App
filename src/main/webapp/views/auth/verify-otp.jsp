<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="vi">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Kích hoạt tài khoản</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
	<div class="auth-wrap">
		<div class="card">
			<h2>Nhập mã OTP</h2>
			<p class="auth-sub">Mã kích hoạt đã được gửi tới <b>${email}</b></p>

			<c:if test="${not empty flash}"><div class="alert alert-success">${flash}</div></c:if>
			<c:if test="${not empty error}"><div class="alert alert-error">${error}</div></c:if>
			<c:if test="${not empty devOtp}"><div class="alert alert-info">Chế độ dev (chưa cấu hình email): mã OTP là <code>${devOtp}</code></div></c:if>

			<form action="${pageContext.request.contextPath}/verify-otp" method="post">
				<input type="hidden" name="email" value="${email}" />
				<div class="form-group">
					<label>Mã OTP (6 chữ số)</label>
					<input class="form-control" type="text" name="otp" maxlength="6" pattern="[0-9]{6}" required />
				</div>
				<button type="submit" class="btn btn-success btn-block">Kích hoạt</button>
			</form>

			<form action="${pageContext.request.contextPath}/resend-otp" method="post">
				<input type="hidden" name="email" value="${email}" />
				<div class="auth-links">
					Không nhận được mã?
					<button type="submit" class="btn btn-secondary btn-sm">Gửi lại OTP</button>
				</div>
			</form>
			<div class="auth-links"><a href="${pageContext.request.contextPath}/login">← Về đăng nhập</a></div>
		</div>
	</div>
</body>
</html>

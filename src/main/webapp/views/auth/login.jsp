<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="vi">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Đăng nhập</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
	<div class="auth-wrap">
		<div class="card">
			<h2>Đăng nhập</h2>
			<p class="auth-sub">Chào mừng bạn quay lại 👋</p>

			<c:if test="${not empty flash}"><div class="alert alert-success">${flash}</div></c:if>
			<c:if test="${not empty error}"><div class="alert alert-error">${error}</div></c:if>
			<c:if test="${not empty devOtp}"><div class="alert alert-info">Chế độ dev: mã OTP là <code>${devOtp}</code></div></c:if>

			<form action="${pageContext.request.contextPath}/login" method="post">
				<div class="form-group">
					<label>Email</label>
					<input class="form-control" type="email" name="email" value="${email}" required />
				</div>
				<div class="form-group">
					<label>Mật khẩu</label>
					<input class="form-control" type="password" name="password" required />
				</div>
				<button type="submit" class="btn btn-primary btn-block">Đăng nhập</button>
			</form>

			<div class="auth-links">
				<a href="${pageContext.request.contextPath}/forgot-password">Quên mật khẩu?</a>
				&nbsp;·&nbsp;
				Chưa có tài khoản? <a href="${pageContext.request.contextPath}/register">Đăng ký</a>
			</div>
			<div class="auth-links"><a href="${pageContext.request.contextPath}/home">← Về trang chủ</a></div>
		</div>
	</div>
</body>
</html>

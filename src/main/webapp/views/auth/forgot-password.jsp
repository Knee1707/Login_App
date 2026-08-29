<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="vi">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Quên mật khẩu</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
	<div class="auth-wrap">
		<div class="card">
			<h2>Quên mật khẩu</h2>
			<p class="auth-sub">Nhập email để nhận mã OTP đặt lại mật khẩu.</p>

			<c:if test="${not empty flash}"><div class="alert alert-error">${flash}</div></c:if>

			<form action="${pageContext.request.contextPath}/forgot-password" method="post">
				<div class="form-group">
					<label>Email</label>
					<input class="form-control" type="email" name="email" required />
				</div>
				<button type="submit" class="btn btn-primary btn-block">Gửi mã OTP</button>
			</form>

			<div class="auth-links"><a href="${pageContext.request.contextPath}/login">← Về đăng nhập</a></div>
		</div>
	</div>
</body>
</html>

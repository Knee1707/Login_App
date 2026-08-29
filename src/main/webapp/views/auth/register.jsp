<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="vi">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Đăng ký</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
	<div class="auth-wrap">
		<div class="card">
			<h2>Tạo tài khoản</h2>
			<p class="auth-sub">Đăng ký rồi kích hoạt qua mã OTP gửi tới email.</p>

			<c:if test="${not empty error}"><div class="alert alert-error">${error}</div></c:if>

			<form action="${pageContext.request.contextPath}/register" method="post">
				<div class="form-group">
					<label>Họ và tên</label>
					<input class="form-control" type="text" name="fullname" value="${fullname}" required />
				</div>
				<div class="form-group">
					<label>Email</label>
					<input class="form-control" type="email" name="email" value="${email}" required />
				</div>
				<div class="form-group">
					<label>Mật khẩu</label>
					<input class="form-control" type="password" name="password" required />
				</div>
				<div class="form-group">
					<label>Nhập lại mật khẩu</label>
					<input class="form-control" type="password" name="confirm" required />
				</div>
				<button type="submit" class="btn btn-success btn-block">Đăng ký</button>
			</form>

			<div class="auth-links">
				Đã có tài khoản? <a href="${pageContext.request.contextPath}/login">Đăng nhập</a>
			</div>
		</div>
	</div>
</body>
</html>

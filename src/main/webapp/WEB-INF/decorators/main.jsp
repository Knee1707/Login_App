<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="vi">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title><sitemesh:write property="title">MyShop</sitemesh:write></title>

	<!-- Muc 1: Template Bootstrap 5 dung chung cho moi trang (qua SiteMesh) -->
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	      rel="stylesheet"
	      integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	      crossorigin="anonymous">
	<link rel="stylesheet"
	      href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

	<sitemesh:write property="head"/>
</head>
<body class="bg-body-tertiary d-flex flex-column min-vh-100">

	<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
		<div class="container">
			<a class="navbar-brand fw-bold" href="${pageContext.request.contextPath}/home">
				<i class="bi bi-shop"></i> MyShop
			</a>
			<button class="navbar-toggler" type="button" data-bs-toggle="collapse"
			        data-bs-target="#nav" aria-controls="nav" aria-expanded="false" aria-label="Menu">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="nav">
				<ul class="navbar-nav me-auto">
					<li class="nav-item">
						<a class="nav-link" href="${pageContext.request.contextPath}/home">Trang chủ</a>
					</li>
					<li class="nav-item">
						<a class="nav-link" href="${pageContext.request.contextPath}/product">Sản phẩm</a>
					</li>
					<c:if test="${not empty sessionScope.account}">
						<li class="nav-item dropdown">
							<a class="nav-link dropdown-toggle" href="#" role="button"
							   data-bs-toggle="dropdown" aria-expanded="false">Quản lý</a>
							<ul class="dropdown-menu">
								<li><a class="dropdown-item" href="${pageContext.request.contextPath}/admin/categories">Danh mục</a></li>
								<li><a class="dropdown-item" href="${pageContext.request.contextPath}/admin/products">Sản phẩm</a></li>
							</ul>
						</li>
					</c:if>
				</ul>
				<ul class="navbar-nav">
					<c:choose>
						<c:when test="${not empty sessionScope.account}">
							<li class="nav-item">
								<a class="nav-link" href="${pageContext.request.contextPath}/profile">
									<i class="bi bi-person-circle"></i>
									${empty sessionScope.account.fullname ? sessionScope.account.email : sessionScope.account.fullname}
								</a>
							</li>
							<li class="nav-item">
								<a class="btn btn-outline-light btn-sm mt-1"
								   href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
							</li>
						</c:when>
						<c:otherwise>
							<li class="nav-item">
								<a class="nav-link" href="${pageContext.request.contextPath}/login">Đăng nhập</a>
							</li>
							<li class="nav-item">
								<a class="btn btn-light btn-sm mt-1"
								   href="${pageContext.request.contextPath}/register">Đăng ký</a>
							</li>
						</c:otherwise>
					</c:choose>
				</ul>
			</div>
		</div>
	</nav>

	<main class="container py-4 flex-grow-1">
		<sitemesh:write property="body"/>
	</main>

	<footer class="text-center text-muted py-3 border-top mt-auto">
		<small>MyShop &middot; Jakarta Servlet + JSP + JPA + SiteMesh 3 &middot; Bootstrap 5</small>
	</footer>

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
	        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
	        crossorigin="anonymous"></script>
</body>
</html>

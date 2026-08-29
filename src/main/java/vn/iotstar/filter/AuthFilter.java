package vn.iotstar.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Bắt buộc đăng nhập mới vào được khu quản lý /admin/*.
 */
@WebFilter(urlPatterns = { "/admin/*" })
public class AuthFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		HttpSession session = req.getSession(false);
		boolean loggedIn = session != null && session.getAttribute("account") != null;

		if (loggedIn) {
			chain.doFilter(request, response);
			return;
		}

		// Lưu lại URL muốn vào để sau khi đăng nhập quay lại đúng chỗ
		String target = req.getRequestURI();
		if (req.getQueryString() != null) {
			target += "?" + req.getQueryString();
		}
		HttpSession newSession = req.getSession(true);
		newSession.setAttribute("redirectAfterLogin", target);
		newSession.setAttribute("flash", "Vui lòng đăng nhập để vào trang quản lý.");
		resp.sendRedirect(req.getContextPath() + "/login");
	}
}

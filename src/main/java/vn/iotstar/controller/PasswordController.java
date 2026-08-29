package vn.iotstar.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.service.IUserService;
import vn.iotstar.service.impl.UserServiceImpl;
import vn.iotstar.util.MailUtil;

/**
 * Quên mật khẩu: gửi OTP qua mail rồi đặt lại mật khẩu.
 */
@WebServlet(urlPatterns = { "/forgot-password", "/reset-password" })
public class PasswordController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final IUserService userService = new UserServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		pullFlash(req);
		if (req.getRequestURI().endsWith("/forgot-password")) {
			req.getRequestDispatcher("/views/auth/forgot-password.jsp").forward(req, resp);
		} else {
			req.setAttribute("email", req.getParameter("email"));
			req.getRequestDispatcher("/views/auth/reset-password.jsp").forward(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		if (req.getRequestURI().endsWith("/forgot-password")) {
			handleForgot(req, resp);
		} else {
			handleReset(req, resp);
		}
	}

	private void handleForgot(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String email = trim(req.getParameter("email"));
		String otp = userService.startPasswordReset(email);
		if (otp == null) {
			req.getSession(true).setAttribute("flash", "Email không tồn tại trong hệ thống.");
			resp.sendRedirect(req.getContextPath() + "/forgot-password");
			return;
		}
		HttpSession session = req.getSession(true);
		session.setAttribute("flash", "Đã gửi mã OTP đặt lại mật khẩu tới " + email + ".");
		if (!MailUtil.isConfigured()) {
			session.setAttribute("devOtp", otp);
		}
		resp.sendRedirect(req.getContextPath() + "/reset-password?email=" + enc(email));
	}

	private void handleReset(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String email = trim(req.getParameter("email"));
		String otp = trim(req.getParameter("otp"));
		String password = req.getParameter("password");
		String confirm = req.getParameter("confirm");

		if (password == null || password.isEmpty() || !password.equals(confirm)) {
			forwardResetError(req, resp, email, "Mật khẩu nhập lại không khớp hoặc để trống.");
			return;
		}
		if (userService.resetPassword(email, otp, password)) {
			req.getSession(true).setAttribute("flash", "Đặt lại mật khẩu thành công! Mời bạn đăng nhập.");
			resp.sendRedirect(req.getContextPath() + "/login");
		} else {
			forwardResetError(req, resp, email, "Mã OTP không đúng hoặc đã hết hạn.");
		}
	}

	private void forwardResetError(HttpServletRequest req, HttpServletResponse resp,
			String email, String error) throws ServletException, IOException {
		req.setAttribute("email", email);
		req.setAttribute("error", error);
		req.getRequestDispatcher("/views/auth/reset-password.jsp").forward(req, resp);
	}

	private void pullFlash(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		if (session == null) {
			return;
		}
		Object flash = session.getAttribute("flash");
		if (flash != null) {
			req.setAttribute("flash", flash);
			session.removeAttribute("flash");
		}
		Object devOtp = session.getAttribute("devOtp");
		if (devOtp != null) {
			req.setAttribute("devOtp", devOtp);
			session.removeAttribute("devOtp");
		}
	}

	private static String trim(String s) {
		return s == null ? "" : s.trim();
	}

	private static String enc(String s) {
		return java.net.URLEncoder.encode(s, java.nio.charset.StandardCharsets.UTF_8);
	}
}

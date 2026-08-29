package vn.iotstar.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.entity.User;
import vn.iotstar.service.IUserService;
import vn.iotstar.service.impl.UserServiceImpl;
import vn.iotstar.util.MailUtil;

/**
 * Đăng ký + kích hoạt OTP + đăng nhập + đăng xuất.
 */
@WebServlet(urlPatterns = { "/register", "/verify-otp", "/resend-otp", "/login", "/logout" })
public class AccountController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final IUserService userService = new UserServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String uri = req.getRequestURI();
		pullFlash(req);

		if (uri.endsWith("/register")) {
			req.getRequestDispatcher("/views/auth/register.jsp").forward(req, resp);
		} else if (uri.endsWith("/verify-otp")) {
			req.setAttribute("email", req.getParameter("email"));
			req.getRequestDispatcher("/views/auth/verify-otp.jsp").forward(req, resp);
		} else if (uri.endsWith("/logout")) {
			HttpSession session = req.getSession(false);
			if (session != null) {
				session.invalidate();
			}
			resp.sendRedirect(req.getContextPath() + "/home");
		} else { // /login
			req.getRequestDispatcher("/views/auth/login.jsp").forward(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String uri = req.getRequestURI();

		if (uri.endsWith("/register")) {
			handleRegister(req, resp);
		} else if (uri.endsWith("/verify-otp")) {
			handleVerify(req, resp);
		} else if (uri.endsWith("/resend-otp")) {
			handleResend(req, resp);
		} else if (uri.endsWith("/login")) {
			handleLogin(req, resp);
		} else {
			resp.sendRedirect(req.getContextPath() + "/home");
		}
	}

	private void handleRegister(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String fullname = trim(req.getParameter("fullname"));
		String email = trim(req.getParameter("email"));
		String password = req.getParameter("password");
		String confirm = req.getParameter("confirm");

		if (email.isEmpty() || password == null || password.isEmpty()) {
			renderRegisterError(req, resp, fullname, email, "Vui lòng nhập đầy đủ thông tin.");
			return;
		}
		if (!password.equals(confirm)) {
			renderRegisterError(req, resp, fullname, email, "Mật khẩu nhập lại không khớp.");
			return;
		}
		if (userService.isEmailTaken(email)) {
			renderRegisterError(req, resp, fullname, email, "Email này đã được đăng ký.");
			return;
		}

		String otp = userService.register(fullname, email, password);
		setFlashAfterOtp(req, otp, "Đã gửi mã OTP kích hoạt tới email " + email + ".");
		resp.sendRedirect(req.getContextPath() + "/verify-otp?email=" + enc(email));
	}

	private void handleVerify(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String email = trim(req.getParameter("email"));
		String otp = trim(req.getParameter("otp"));

		if (userService.activate(email, otp)) {
			flash(req, "Kích hoạt tài khoản thành công! Mời bạn đăng nhập.");
			resp.sendRedirect(req.getContextPath() + "/login");
		} else {
			req.setAttribute("email", email);
			req.setAttribute("error", "Mã OTP không đúng hoặc đã hết hạn.");
			req.getRequestDispatcher("/views/auth/verify-otp.jsp").forward(req, resp);
		}
	}

	private void handleResend(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String email = trim(req.getParameter("email"));
		String otp = userService.resendActivationOtp(email);
		if (otp != null) {
			setFlashAfterOtp(req, otp, "Đã gửi lại mã OTP tới email " + email + ".");
		} else {
			flash(req, "Không thể gửi lại OTP (email không tồn tại hoặc đã kích hoạt).");
		}
		resp.sendRedirect(req.getContextPath() + "/verify-otp?email=" + enc(email));
	}

	private void handleLogin(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String email = trim(req.getParameter("email"));
		String password = req.getParameter("password");

		User user = userService.authenticate(email, password);
		if (user == null) {
			req.setAttribute("email", email);
			req.setAttribute("error", "Email hoặc mật khẩu không đúng.");
			req.getRequestDispatcher("/views/auth/login.jsp").forward(req, resp);
			return;
		}
		if (user.getStatus() != 1) {
			flash(req, "Tài khoản chưa được kích hoạt. Vui lòng nhập OTP để kích hoạt.");
			resp.sendRedirect(req.getContextPath() + "/verify-otp?email=" + enc(email));
			return;
		}

		HttpSession session = req.getSession(true);
		session.setAttribute("account", user);

		String redirect = (String) session.getAttribute("redirectAfterLogin");
		session.removeAttribute("redirectAfterLogin");
		if (redirect != null && !redirect.isEmpty()) {
			resp.sendRedirect(redirect);
		} else {
			resp.sendRedirect(req.getContextPath() + "/home");
		}
	}

	private void renderRegisterError(HttpServletRequest req, HttpServletResponse resp,
			String fullname, String email, String error) throws ServletException, IOException {
		req.setAttribute("fullname", fullname);
		req.setAttribute("email", email);
		req.setAttribute("error", error);
		req.getRequestDispatcher("/views/auth/register.jsp").forward(req, resp);
	}

	// Đặt thông báo + (chế độ dev) hiển thị OTP để test không cần email thật
	private void setFlashAfterOtp(HttpServletRequest req, String otp, String msg) {
		HttpSession session = req.getSession(true);
		session.setAttribute("flash", msg);
		if (!MailUtil.isConfigured() && otp != null) {
			session.setAttribute("devOtp", otp);
		}
	}

	private void flash(HttpServletRequest req, String msg) {
		req.getSession(true).setAttribute("flash", msg);
	}

	// Chuyển flash từ session sang request rồi xóa (hiển thị 1 lần)
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

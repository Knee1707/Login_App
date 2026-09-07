package vn.iotstar.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import vn.iotstar.entity.User;
import vn.iotstar.service.IUserService;
import vn.iotstar.service.impl.UserServiceImpl;
import vn.iotstar.util.Constant;

/**
 * Muc 3: Ho so ca nhan - cap nhat fullname, phone, avatar (upload multipart) bang JPA.
 * - GET  /profile : hien thi form ho so cua nguoi dung dang dang nhap.
 * - POST /profile : validate (Muc 2), luu anh va cap nhat CSDL (mau PRG).
 * Yeu cau dang nhap; neu chua thi chuyen ve /login.
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 2L * 1024 * 1024, maxRequestSize = 5L * 1024 * 1024)
@WebServlet("/profile")
public class ProfileController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Set<String> ALLOWED_EXT = Set.of("jpg", "jpeg", "png", "gif");
	private static final long MAX_AVATAR_SIZE = 2L * 1024 * 1024; // 2MB

	private final IUserService userService = new UserServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		User account = currentAccount(req);
		if (account == null) {
			redirectToLogin(req, resp);
			return;
		}
		// Lay ban moi nhat tu CSDL
		req.setAttribute("user", userService.findById(account.getUserId()));
		pullFlash(req);
		req.getRequestDispatcher("/views/profile.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		User account = currentAccount(req);
		if (account == null) {
			redirectToLogin(req, resp);
			return;
		}

		String fullname = trim(req.getParameter("fullname"));
		String phone = trim(req.getParameter("phone"));
		Part avatarPart = req.getPart("avatar");

		Map<String, String> errors = validate(fullname, phone, avatarPart);
		if (!errors.isEmpty()) {
			User user = userService.findById(account.getUserId());
			user.setFullname(fullname);
			user.setPhone(phone);
			req.setAttribute("user", user);
			req.setAttribute("errors", errors);
			req.getRequestDispatcher("/views/profile.jsp").forward(req, resp);
			return;
		}

		String avatarFileName = hasFile(avatarPart) ? saveAvatar(avatarPart) : null;
		User updated = userService.updateProfile(account.getUserId(), fullname, phone, avatarFileName);

		// Cap nhat lai session de navbar hien ten moi
		req.getSession().setAttribute("account", updated);
		req.getSession().setAttribute("flash", "Cập nhật hồ sơ thành công!");
		resp.sendRedirect(req.getContextPath() + "/profile");
	}

	// ---------- Validation (Muc 2) ----------

	private Map<String, String> validate(String fullname, String phone, Part avatarPart) {
		Map<String, String> errors = new HashMap<>();

		if (fullname.isEmpty()) {
			errors.put("fullname", "Họ tên không được để trống.");
		} else if (fullname.length() < 2 || fullname.length() > 100) {
			errors.put("fullname", "Họ tên phải từ 2 đến 100 ký tự.");
		}

		if (phone.isEmpty()) {
			errors.put("phone", "Số điện thoại không được để trống.");
		} else if (!phone.matches("^0\\d{9,10}$")) {
			errors.put("phone", "Số điện thoại không hợp lệ (10-11 số, bắt đầu bằng 0).");
		}

		if (hasFile(avatarPart)) {
			if (avatarPart.getSize() > MAX_AVATAR_SIZE) {
				errors.put("avatar", "Kích thước ảnh vượt quá 2MB.");
			}
			String ext = extensionOf(avatarPart.getSubmittedFileName());
			if (ext == null || !ALLOWED_EXT.contains(ext)) {
				errors.put("avatar", "Chỉ chấp nhận ảnh JPG, JPEG, PNG hoặc GIF.");
			}
		}
		return errors;
	}

	// ---------- Upload ----------

	/** Luu file avatar vao thu muc upload (Constant.DIR), tra ve ten file da luu. */
	private String saveAvatar(Part avatarPart) throws IOException {
		File dir = new File(Constant.DIR);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String ext = extensionOf(avatarPart.getSubmittedFileName());
		String fileName = "avatar_" + System.currentTimeMillis() + "." + ext;
		avatarPart.write(Constant.DIR + "/" + fileName);
		return fileName;
	}

	// ---------- Tien ich ----------

	/** Chuyen thong bao flash tu session sang request roi xoa (hien thi 1 lan). */
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
	}

	private User currentAccount(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		return session == null ? null : (User) session.getAttribute("account");
	}

	private void redirectToLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.sendRedirect(req.getContextPath() + "/login");
	}

	private static boolean hasFile(Part part) {
		return part != null && part.getSize() > 0;
	}

	private static String trim(String s) {
		return s == null ? "" : s.trim();
	}

	private static String extensionOf(String fileName) {
		if (fileName == null) {
			return null;
		}
		String name = Paths.get(fileName).getFileName().toString();
		int dot = name.lastIndexOf('.');
		if (dot < 0 || dot == name.length() - 1) {
			return null;
		}
		return name.substring(dot + 1).toLowerCase(Locale.ROOT);
	}
}

package vn.iotstar.service;

import vn.iotstar.entity.User;

public interface IUserService {

	User findByEmail(String email);

	User findById(int id);

	boolean isEmailTaken(String email);

	/**
	 * Tạo tài khoản mới (trạng thái chưa kích hoạt), sinh OTP, gửi mail kích hoạt.
	 * Trả về mã OTP (phục vụ hiển thị ở chế độ dev). Gọi sau khi đã kiểm tra email chưa tồn tại.
	 */
	String register(String fullname, String email, String rawPassword);

	/** Gửi lại OTP kích hoạt cho tài khoản chưa kích hoạt. Trả về OTP hoặc null nếu không hợp lệ. */
	String resendActivationOtp(String email);

	/** Kích hoạt tài khoản bằng OTP. Trả về true nếu thành công. */
	boolean activate(String email, String otp);

	/** Xác thực đăng nhập. Trả về User nếu đúng mật khẩu (kể cả chưa kích hoạt), null nếu sai. */
	User authenticate(String email, String rawPassword);

	/** Bắt đầu quên mật khẩu: sinh OTP + gửi mail. Trả về OTP, hoặc null nếu email không tồn tại. */
	String startPasswordReset(String email);

	/** Đặt lại mật khẩu bằng OTP. Trả về true nếu thành công. */
	boolean resetPassword(String email, String otp, String newRawPassword);

	/**
	 * Muc 3 - Cập nhật hồ sơ: fullname, phone và (tùy chọn) tên file avatar.
	 * Nếu avatarFileName = null thì giữ nguyên avatar cũ.
	 * @return User sau khi cập nhật, hoặc null nếu không tìm thấy.
	 */
	User updateProfile(int userId, String fullname, String phone, String avatarFileName);
}

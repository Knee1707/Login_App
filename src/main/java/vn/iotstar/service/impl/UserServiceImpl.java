package vn.iotstar.service.impl;

import vn.iotstar.dao.IUserDao;
import vn.iotstar.dao.impl.UserDao;
import vn.iotstar.entity.User;
import vn.iotstar.service.IUserService;
import vn.iotstar.util.MailUtil;
import vn.iotstar.util.OtpUtil;
import vn.iotstar.util.PasswordUtil;

public class UserServiceImpl implements IUserService {

	private final IUserDao userDao = new UserDao();

	@Override
	public User findByEmail(String email) {
		return userDao.findByEmail(email);
	}

	@Override
	public User findById(int id) {
		return userDao.findById(id);
	}

	@Override
	public boolean isEmailTaken(String email) {
		return userDao.findByEmail(email) != null;
	}

	@Override
	public String register(String fullname, String email, String rawPassword) {
		String otp = OtpUtil.generate();

		User user = new User();
		user.setFullname(fullname);
		user.setEmail(email);
		user.setPassword(PasswordUtil.hash(rawPassword));
		user.setStatus(0); // chưa kích hoạt
		user.setOtp(otp);
		user.setOtpExpiry(System.currentTimeMillis() + OtpUtil.OTP_TTL_MILLIS);
		userDao.insert(user);

		MailUtil.send(email, "Kích hoạt tài khoản",
				MailUtil.buildOtpBody("Kích hoạt tài khoản của bạn", otp));
		return otp;
	}

	@Override
	public String resendActivationOtp(String email) {
		User user = userDao.findByEmail(email);
		if (user == null || user.getStatus() == 1) {
			return null;
		}
		String otp = OtpUtil.generate();
		user.setOtp(otp);
		user.setOtpExpiry(System.currentTimeMillis() + OtpUtil.OTP_TTL_MILLIS);
		userDao.update(user);

		MailUtil.send(email, "Mã kích hoạt mới",
				MailUtil.buildOtpBody("Mã kích hoạt tài khoản (gửi lại)", otp));
		return otp;
	}

	@Override
	public boolean activate(String email, String otp) {
		User user = userDao.findByEmail(email);
		if (!isOtpValid(user, otp)) {
			return false;
		}
		user.setStatus(1);
		user.setOtp(null);
		user.setOtpExpiry(0);
		userDao.update(user);
		return true;
	}

	@Override
	public User authenticate(String email, String rawPassword) {
		User user = userDao.findByEmail(email);
		if (user != null && PasswordUtil.matches(rawPassword, user.getPassword())) {
			return user;
		}
		return null;
	}

	@Override
	public String startPasswordReset(String email) {
		User user = userDao.findByEmail(email);
		if (user == null) {
			return null;
		}
		String otp = OtpUtil.generate();
		user.setOtp(otp);
		user.setOtpExpiry(System.currentTimeMillis() + OtpUtil.OTP_TTL_MILLIS);
		userDao.update(user);

		MailUtil.send(email, "Đặt lại mật khẩu",
				MailUtil.buildOtpBody("Yêu cầu đặt lại mật khẩu", otp));
		return otp;
	}

	@Override
	public boolean resetPassword(String email, String otp, String newRawPassword) {
		User user = userDao.findByEmail(email);
		if (!isOtpValid(user, otp)) {
			return false;
		}
		user.setPassword(PasswordUtil.hash(newRawPassword));
		user.setOtp(null);
		user.setOtpExpiry(0);
		// Đặt lại mật khẩu thành công cũng coi như tài khoản đã xác thực email
		user.setStatus(1);
		userDao.update(user);
		return true;
	}

	@Override
	public User updateProfile(int userId, String fullname, String phone, String avatarFileName) {
		User user = userDao.findById(userId);
		if (user == null) {
			return null;
		}
		user.setFullname(fullname);
		user.setPhone(phone);
		if (avatarFileName != null) {
			user.setAvatar(avatarFileName);
		}
		userDao.update(user);
		return user;
	}

	private boolean isOtpValid(User user, String otp) {
		return user != null
				&& user.getOtp() != null
				&& !user.getOtp().isEmpty()
				&& user.getOtp().equals(otp)
				&& System.currentTimeMillis() <= user.getOtpExpiry();
	}
}

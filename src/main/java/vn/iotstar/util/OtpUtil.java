package vn.iotstar.util;

import java.security.SecureRandom;

public class OtpUtil {

	private static final SecureRandom RANDOM = new SecureRandom();

	// OTP có hiệu lực 5 phút
	public static final long OTP_TTL_MILLIS = 5 * 60 * 1000L;

	/** Sinh mã OTP 6 chữ số. */
	public static String generate() {
		int number = RANDOM.nextInt(1_000_000);
		return String.format("%06d", number);
	}
}

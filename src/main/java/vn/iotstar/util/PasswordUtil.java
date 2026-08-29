package vn.iotstar.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * Băm mật khẩu bằng SHA-256 (hex). Đủ dùng cho bài tập; không lưu mật khẩu thô.
 */
public class PasswordUtil {

	public static String hash(String raw) {
		if (raw == null) {
			raw = "";
		}
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] digest = md.digest(raw.getBytes(StandardCharsets.UTF_8));
			StringBuilder sb = new StringBuilder(digest.length * 2);
			for (byte b : digest) {
				sb.append(Character.forDigit((b >> 4) & 0xF, 16));
				sb.append(Character.forDigit(b & 0xF, 16));
			}
			return sb.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static boolean matches(String raw, String hashed) {
		if (hashed == null) {
			return false;
		}
		return hash(raw).equalsIgnoreCase(hashed);
	}
}

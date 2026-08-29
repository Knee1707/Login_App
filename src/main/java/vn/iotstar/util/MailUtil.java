package vn.iotstar.util;

import java.io.InputStream;
import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

/**
 * Gửi email OTP qua SMTP (mặc định Gmail). Nếu chưa cấu hình username/password
 * thì tự động chuyển sang "chế độ dev": in OTP ra log server thay vì gửi mail.
 *
 * Ưu tiên cấu hình theo thứ tự:
 *   1. Biến môi trường: MAIL_USERNAME, MAIL_PASSWORD, MAIL_FROM, MAIL_HOST, MAIL_PORT
 *   2. File classpath: /email.properties
 */
public class MailUtil {

	private static final String HOST;
	private static final String PORT;
	private static final String USERNAME;
	private static final String PASSWORD;
	private static final String FROM;

	static {
		Properties props = new Properties();
		try (InputStream in = MailUtil.class.getResourceAsStream("/email.properties")) {
			if (in != null) {
				props.load(in);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		HOST = firstNonBlank(System.getenv("MAIL_HOST"), props.getProperty("mail.smtp.host"), "smtp.gmail.com");
		PORT = firstNonBlank(System.getenv("MAIL_PORT"), props.getProperty("mail.smtp.port"), "587");
		USERNAME = firstNonBlank(System.getenv("MAIL_USERNAME"), props.getProperty("mail.smtp.username"), "");
		PASSWORD = firstNonBlank(System.getenv("MAIL_PASSWORD"), props.getProperty("mail.smtp.password"), "");
		FROM = firstNonBlank(System.getenv("MAIL_FROM"), props.getProperty("mail.smtp.from"), USERNAME);
	}

	private static String firstNonBlank(String... values) {
		for (String v : values) {
			if (v != null && !v.trim().isEmpty()) {
				return v.trim();
			}
		}
		return "";
	}

	/** Đã cấu hình đủ tài khoản gửi mail thật hay chưa. */
	public static boolean isConfigured() {
		return !USERNAME.isEmpty() && !PASSWORD.isEmpty();
	}

	/**
	 * Gửi email. Trả về true nếu đã gửi thật qua SMTP, false nếu chỉ in ra log
	 * (chế độ dev do chưa cấu hình).
	 */
	public static boolean send(String to, String subject, String htmlBody) {
		if (!isConfigured()) {
			System.out.println("========================================");
			System.out.println("[MailUtil - DEV MODE] Chưa cấu hình SMTP, không gửi mail thật.");
			System.out.println("To     : " + to);
			System.out.println("Subject: " + subject);
			System.out.println("Body   : " + htmlBody.replaceAll("<[^>]+>", " ").trim());
			System.out.println("========================================");
			return false;
		}

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", HOST);
		props.put("mail.smtp.port", PORT);
		props.put("mail.smtp.ssl.protocols", "TLSv1.2");

		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(USERNAME, PASSWORD);
			}
		});

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(FROM));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(subject, "UTF-8");
			message.setContent(htmlBody, "text/html; charset=UTF-8");
			Transport.send(message);
			System.out.println("[MailUtil] Đã gửi mail tới " + to);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			// Nếu gửi lỗi, vẫn in OTP ra log để không chặn luồng test
			System.out.println("[MailUtil] Gửi mail lỗi, nội dung: " + htmlBody.replaceAll("<[^>]+>", " ").trim());
			return false;
		}
	}

	/** Tạo nội dung HTML cho email OTP. */
	public static String buildOtpBody(String heading, String otp) {
		return "<div style='font-family:Segoe UI,Arial,sans-serif;font-size:15px;color:#1c1e21'>"
				+ "<h2 style='color:#1a5276'>" + heading + "</h2>"
				+ "<p>Mã OTP của bạn là:</p>"
				+ "<p style='font-size:28px;font-weight:700;letter-spacing:4px;color:#2874a6'>" + otp + "</p>"
				+ "<p>Mã có hiệu lực trong 5 phút. Vui lòng không chia sẻ mã này cho người khác.</p>"
				+ "</div>";
	}
}

package vn.iotstar.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "UserId")
	private int userId;

	@Column(name = "Fullname", columnDefinition = "nvarchar(100)")
	private String fullname;

	@Column(name = "Email", length = 150, nullable = false, unique = true)
	private String email;

	@Column(name = "Password", length = 255, nullable = false)
	private String password;

	@Column(name = "Otp", length = 10)
	private String otp;

	// Thời điểm OTP hết hạn (epoch millis)
	@Column(name = "OtpExpiry")
	private long otpExpiry;

	// 0 = chưa kích hoạt, 1 = đã kích hoạt
	@Column(name = "Status")
	private int status;

	public User() {
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public long getOtpExpiry() {
		return otpExpiry;
	}

	public void setOtpExpiry(long otpExpiry) {
		this.otpExpiry = otpExpiry;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}

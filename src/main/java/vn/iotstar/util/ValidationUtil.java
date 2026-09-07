package vn.iotstar.util;

import java.util.LinkedHashMap;
import java.util.Map;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

/**
 * Muc 2: Tien ich kiem tra du lieu (Bean Validation / Hibernate Validator).
 * Dua tren cac annotation (@NotEmpty, ...) khai bao tren entity.
 */
public final class ValidationUtil {

	private static final ValidatorFactory FACTORY = Validation.buildDefaultValidatorFactory();
	private static final Validator VALIDATOR = FACTORY.getValidator();

	private ValidationUtil() {
	}

	/**
	 * Kiem tra mot bean theo cac rang buoc annotation.
	 * @return Map[tenTruong -> thongBaoLoi]; rong neu hop le.
	 */
	public static <T> Map<String, String> validate(T bean) {
		Map<String, String> errors = new LinkedHashMap<>();
		for (ConstraintViolation<T> v : VALIDATOR.validate(bean)) {
			errors.putIfAbsent(v.getPropertyPath().toString(), v.getMessage());
		}
		return errors;
	}
}

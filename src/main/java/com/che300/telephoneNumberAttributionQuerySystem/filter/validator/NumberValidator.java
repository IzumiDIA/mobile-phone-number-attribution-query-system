package com.che300.telephoneNumberAttributionQuerySystem.filter.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class NumberValidator implements ConstraintValidator<LegitimateNumber, String> {
	private byte length;
	
	@Override
	public void initialize(LegitimateNumber constraintAnnotation) {
		this.length = constraintAnnotation.value();
	}
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if ( Objects.isNull(value) ) return false;
		else {
			final var bytes = value.getBytes(StandardCharsets.US_ASCII);
			if ( bytes.length != length ) return false;
			for (final var n : bytes) if ( n < 48 || n > 57 ) return false;
		}
		return true;
	}
}

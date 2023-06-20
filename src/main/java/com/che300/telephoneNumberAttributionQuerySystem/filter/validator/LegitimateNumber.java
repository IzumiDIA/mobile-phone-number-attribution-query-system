package com.che300.telephoneNumberAttributionQuerySystem.filter.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.RECORD_COMPONENT})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = NumberValidator.class)
public @interface LegitimateNumber {
	
	/**
	 * @return 校验出错时默认返回的消息
	 */
	String message() default "Number format validation failed!";
	
	/**
	 * @return 分组校验
	 */
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
	
	/**
	 * @return length
	 */
	byte value() default 11;
}

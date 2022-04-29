package com.kms.byslboot.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.kms.byslboot.common.validator.StartEndDateValidator;

@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StartEndDateValidator.class)
public @interface DateValid {
	
	String message() default "시작 날짜는 끝 날짜와 같거나 이전이여야 합니다.";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
}

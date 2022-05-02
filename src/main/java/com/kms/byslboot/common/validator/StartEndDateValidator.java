package com.kms.byslboot.common.validator;

import java.lang.reflect.Field;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerErrorException;

import com.kms.byslboot.calendar.dto.CalendarRequest;
import com.kms.byslboot.common.annotation.DateValid;

@Component
public class StartEndDateValidator implements ConstraintValidator<DateValid, Object>{
	private String startDate;
	private String endDate;
	
	@Override
	public void initialize(DateValid constraintAnnotaion) {
		startDate = constraintAnnotaion.startDate();
		endDate = constraintAnnotaion.endDate();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		Date startDateValue = getFieldValue(value, startDate);
		Date endDateValue = getFieldValue(value, endDate);
		return (startDateValue.compareTo(endDateValue) <= 0);
	}
	
	public Date getFieldValue(Object value, String fieldName) {
		Class<?> getClass = value.getClass();
		Field dateField;
		try {
			dateField = getClass.getDeclaredField(fieldName);
			dateField.setAccessible(true);
			Object target = dateField.get(value);
			if(!(target instanceof String)) {
				throw new ClassCastException("casting exception");
			}
			return Date.valueOf((String) target);
		}catch(Exception e) {
			throw new ServerErrorException("not found field");
		}
		
	}
}

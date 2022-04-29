package com.kms.byslboot.common.validator;

import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

import com.kms.byslboot.calendar.dto.CalendarRequest;
import com.kms.byslboot.common.annotation.DateValid;

@Component
public class StartEndDateValidator implements ConstraintValidator<DateValid, Object>{

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		CalendarRequest dto = (CalendarRequest) value;
		
		return (Date.valueOf(dto.getStartDate()).compareTo(Date.valueOf(dto.getEndDate())) <= 0);
	}
	
}

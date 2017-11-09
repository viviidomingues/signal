package com.sample.dao;

import javax.persistence.AttributeConverter;

public class BooleanTrueFalseConverter implements AttributeConverter<Boolean, String> {

	@Override
	public String convertToDatabaseColumn(Boolean value) {
		String result = "F";
		if (Boolean.TRUE.equals(value)) {
			result = "T";
		}
		return result;
	}

	@Override
	public Boolean convertToEntityAttribute(String value) {
		return "T".equals(value);
	}

}

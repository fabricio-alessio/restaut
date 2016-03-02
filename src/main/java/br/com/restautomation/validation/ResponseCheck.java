package br.com.restautomation.validation;

import java.util.Map;

public class ResponseCheck {

	private int httpCode;

	private Map<String, FieldCheck> fieldChecks;

	public int getHttpCode() {
		return httpCode;
	}

	public void setHttpCode(int httpCode) {
		this.httpCode = httpCode;
	}

	public Map<String, FieldCheck> getFieldChecks() {
		return fieldChecks;
	}

	public void setFieldChecks(Map<String, FieldCheck> fieldChecks) {
		this.fieldChecks = fieldChecks;
	}
}

package br.com.restautomation.validation;

public class Validation {

	private ValidationStatus status;

	private String test = "";

	private String error = "";

	public ValidationStatus getStatus() {
		return status;
	}

	public void setStatus(ValidationStatus status) {
		this.status = status;
	}

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}

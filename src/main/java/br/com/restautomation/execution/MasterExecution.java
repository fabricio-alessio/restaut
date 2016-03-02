package br.com.restautomation.execution;

import java.util.Date;
import java.util.UUID;

import br.com.restautomation.validation.ValidationStatus;

public class MasterExecution {

	private String id;

	private Date date;

	private ValidationStatus validationStatus;

	public String getId() {
		if (id == null) {
			id = UUID.randomUUID().toString();
		}
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public ValidationStatus getValidationStatus() {
		return validationStatus;
	}

	public void setValidationStatus(ValidationStatus validationStatus) {
		this.validationStatus = validationStatus;
	}
}

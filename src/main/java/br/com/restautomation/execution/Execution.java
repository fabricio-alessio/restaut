package br.com.restautomation.execution;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import br.com.restautomation.script.Request;
import br.com.restautomation.script.Response;
import br.com.restautomation.validation.Validation;
import br.com.restautomation.validation.ValidationStatus;

public class Execution {

	private String id;

	private Date date;

	private String scriptId;

	private String masterId;

	private Request request;

	private Response response;

	private List<Execution> preExecutions;

	private List<Validation> validations;

	private String userName;

	private int level;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getScriptId() {
		return scriptId;
	}

	public void setScriptId(String scriptId) {
		this.scriptId = scriptId;
	}

	public String getMasterId() {
		return masterId;
	}

	public void setMasterId(String masterId) {
		this.masterId = masterId;
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	public List<Execution> getPreExecutions() {
		return preExecutions;
	}

	public void setPreExecutions(List<Execution> preExecutions) {
		this.preExecutions = preExecutions;
	}

	public List<Validation> getValidations() {
		return validations;
	}

	public void setValidations(List<Validation> validations) {
		this.validations = validations;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getId() {
		if (id == null) {
			id = UUID.randomUUID().toString();
		}
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ValidationStatus getStatus() {

		if (getValidations() == null) {
			return ValidationStatus.SUCCESS;
		}

		ValidationStatus status = ValidationStatus.SUCCESS;
		for (Validation validation : getValidations()) {
			if (validation.getStatus().equals(ValidationStatus.FAILURE)) {
				status = ValidationStatus.FAILURE;
			}
			if (validation.getStatus().equals(ValidationStatus.ERROR)) {
				return ValidationStatus.ERROR;
			}
		}
		return status;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void calculateLevels() {

		startLevels(0);
	}

	private void startLevels(int level) {

		setLevel(level);
		if (preExecutions == null) {
			return;
		}
		for (Execution execution : preExecutions) {
			execution.startLevels(level + 1);
		}
	}

	@Override
	public String toString() {
		return getScriptId();
	}
}

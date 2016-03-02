package br.com.restautomation.script;

import java.util.List;

import br.com.restautomation.validation.ResponseCheck;

public class Script {

	private String id;

	private String description;

	private List<String> tags;

	private List<String> preConditions;

	private Request request;

	private ResponseCheck responseCheck;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public List<String> getPreConditions() {
		return preConditions;
	}

	public void setPreConditions(List<String> preConditions) {
		this.preConditions = preConditions;
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public ResponseCheck getResponseCheck() {
		return responseCheck;
	}

	public void setResponseCheck(ResponseCheck response) {
		this.responseCheck = response;
	}
}

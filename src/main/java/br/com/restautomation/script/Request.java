package br.com.restautomation.script;

import java.util.Map;
import java.util.Map.Entry;

public class Request {

	private String url;

	private String method;

	private Map<String, String> params;

	private Map<String, String> headers;

	private String body;

	private Authorization authorization;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public Authorization getAuthorization() {

		if (authorization == null) {
			authorization = new AuthorizationNone();
		}
		return authorization;
	}

	public void setAuthorization(Authorization authorization) {
		this.authorization = authorization;
	}

	public String getFlatParams() {

		if (getParams() == null || getParams().size() == 0) {
			return "";
		}

		String flat = "";
		for (Entry<String, String> entry : getParams().entrySet()) {
			flat += entry.getKey() + "=" + entry.getValue() + "&";
		}
		return flat;
	}

	public String getFullUrl() {

		String params = getFlatParams();
		if (params.equals("")) {
			return getUrl();
		}

		return getUrl() + "?" + params;
	}
}

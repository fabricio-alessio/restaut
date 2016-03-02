package br.com.restautomation.script;

public class Response {

	public static int UNDEFINED = -2;

	private int httpCode = UNDEFINED;

	private String body = "";

	public int getHttpCode() {
		return httpCode;
	}

	public void setHttpCode(int httpCode) {
		this.httpCode = httpCode;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
}

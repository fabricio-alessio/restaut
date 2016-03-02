package br.com.restautomation.infra;

public class AutomationException extends Exception {

	private static final long serialVersionUID = -3688962082795860775L;

	public AutomationException(String msg) {
		super(msg);
	}

	public AutomationException(String msg, Throwable cause) {
		super(msg, cause);
	}
}

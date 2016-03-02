package br.com.restautomation.infra;

public class AdapterException extends RuntimeException {

	private static final long serialVersionUID = -5763115854709419053L;

	public AdapterException(String msg) {
		super(msg);
	}

	public AdapterException(String msg, Throwable cause) {
		super(msg, cause);
	}
}

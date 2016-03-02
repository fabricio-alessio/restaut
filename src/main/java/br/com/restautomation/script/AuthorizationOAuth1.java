package br.com.restautomation.script;

public class AuthorizationOAuth1 extends Authorization {

	private String realm;

	private String consumerKey;

	private String nonce;

	private SignatureMethod signatureMethod;

	public AuthorizationOAuth1() {
		setType(AuthorizationType.OAUTH1);
	}

	public String getRealm() {
		return realm;
	}

	public void setRealm(String realm) {
		this.realm = realm;
	}

	public String getConsumerKey() {
		return consumerKey;
	}

	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	public SignatureMethod getSignatureMethod() {
		return signatureMethod;
	}

	public void setSignatureMethod(SignatureMethod signatureMethod) {
		this.signatureMethod = signatureMethod;
	}
}

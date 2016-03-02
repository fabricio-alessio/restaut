package br.com.restautomation.script;

import org.bson.Document;

import br.com.restautomation.infra.AdapterException;

public class AuthorizationAdapter {

	public Document adapt(Authorization auth) {

		if (auth == null) {
			return null;
		}

		if (AuthorizationType.OAUTH1.equals(auth.getType())) {
			return adaptOAuth1(auth);
		} else if (AuthorizationType.BASIC.equals(auth.getType())) {
			return adaptBasic(auth);
		} else if (AuthorizationType.NONE.equals(auth.getType())) {
			return null;
		} else {
			throw new AdapterException("Adapter authorization of type " + auth.getType().name() + " not implemented!");
		}
	}

	private Document adaptBasic(Authorization auth) {

		if (!(auth instanceof AuthorizationBasic)) {
			throw new AdapterException("Type of class is different of type property " + auth.getType().name());
		}

		AuthorizationBasic authBasic = (AuthorizationBasic) auth;
		Document doc = new Document();
		doc.append("type", authBasic.getType().name());
		doc.append("username", authBasic.getUsername());
		doc.append("password", authBasic.getPassword());

		return doc;
	}

	private Document adaptOAuth1(Authorization auth) {

		if (!(auth instanceof AuthorizationOAuth1)) {
			throw new AdapterException("Type of class is different of type property " + auth.getType().name());
		}

		AuthorizationOAuth1 oauth1 = (AuthorizationOAuth1) auth;
		Document doc = new Document();
		doc.append("type", oauth1.getType().name());
		doc.append("consumerKey", oauth1.getConsumerKey());
		doc.append("nonce", oauth1.getNonce());
		doc.append("realm", oauth1.getRealm());
		doc.append("signatureMethod", oauth1.getSignatureMethod().name());

		return doc;
	}

	public Authorization adapt(Document doc) {

		if (doc == null) {
			return null;
		}

		String authType = doc.getString("type");
		if (authType == null) {
			throw new AdapterException("Can't adapt authorization without type");
		}

		if (AuthorizationType.OAUTH1.name().equals(authType)) {
			return adaptOAuth1(doc);
		} else if (AuthorizationType.BASIC.name().equals(authType)) {
			return adaptBasic(doc);
		} else {
			throw new AdapterException("Adapter authorization of type " + authType + " not implemented!");
		}
	}

	private Authorization adaptBasic(Document doc) {

		AuthorizationBasic authBasic = new AuthorizationBasic();
		authBasic.setUsername(doc.getString("username"));
		authBasic.setPassword(doc.getString("password"));

		return authBasic;
	}

	private Authorization adaptOAuth1(Document doc) {

		AuthorizationOAuth1 oAuth1 = new AuthorizationOAuth1();
		oAuth1.setConsumerKey(doc.getString("consumerKey"));
		oAuth1.setNonce(doc.getString("nonce"));
		oAuth1.setRealm(doc.getString("realm"));
		oAuth1.setSignatureMethod(SignatureMethod.valueOf(doc.getString("signatureMethod")));
		return oAuth1;
	}
}

package br.com.restautomation.assembly;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

import br.com.restautomation.infra.AdapterException;
import br.com.restautomation.script.Authorization;
import br.com.restautomation.script.AuthorizationBasic;
import br.com.restautomation.script.AuthorizationOAuth1;
import br.com.restautomation.script.AuthorizationType;

public class AuthorizationAssembler {

	public Map<String, String> assembleHeader(Authorization auth) {

		Map<String, String> map = new HashMap<>();

		if (AuthorizationType.NONE.equals(auth.getType())) {
			return map;
		}

		map.put("Authorization", assembleAuthorization(auth));

		return map;
	}

	private String assembleAuthorization(Authorization auth) {

		if (AuthorizationType.OAUTH1.equals(auth.getType()) && auth instanceof AuthorizationOAuth1) {
			return assembleAuthorizationOAuth1((AuthorizationOAuth1) auth);
		} else if (AuthorizationType.BASIC.equals(auth.getType()) && auth instanceof AuthorizationBasic) {
			return assembleAuthorizationBasic((AuthorizationBasic) auth);
		}

		throw new AdapterException("Assembler " + auth.getType().name() + " not implemented");
	}

	private String assembleAuthorizationBasic(AuthorizationBasic auth) {

		String data = auth.getUsername() + ":" + auth.getPassword();
		return "Basic " + Base64.encodeBase64String(data.getBytes());
	}

	private String assembleAuthorizationOAuth1(AuthorizationOAuth1 auth) {

		String timeStamp = "" + new Date().getTime();

		String data = "OAuth ";
		data += "realm=" + auth.getRealm();
		data += ",oauth_consumer_key=" + auth.getConsumerKey();
		data += ",oauth_nonce=" + auth.getNonce();
		data += ",oauth_signature=" + getSignatureOAuth1(auth, timeStamp);
		data += ",oauth_signature_method=" + auth.getSignatureMethod().name();
		data += ",oauth_timestamp=" + timeStamp;
		data += ",oauth_version=1.0";
		return data;
	}

	private String getSignatureOAuth1(AuthorizationOAuth1 auth, String timeStamp) {

		String signature = "oauth_consumer_key=" + auth.getConsumerKey();
		signature += "&oauth_nonce=" + auth.getNonce();
		signature += "&oauth_signature_method=" + auth.getSignatureMethod().name();
		signature += "&oauth_timestamp=" + timeStamp;
		signature += "&oauth_version=1.0";

		return Base64.encodeBase64String(signature.getBytes());
	}
}

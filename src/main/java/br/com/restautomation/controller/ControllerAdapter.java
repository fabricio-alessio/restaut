package br.com.restautomation.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import spark.Request;
import br.com.restautomation.Environment;
import br.com.restautomation.script.Authorization;
import br.com.restautomation.script.AuthorizationBasic;
import br.com.restautomation.script.AuthorizationOAuth1;
import br.com.restautomation.script.AuthorizationType;
import br.com.restautomation.script.Script;
import br.com.restautomation.script.SignatureMethod;
import br.com.restautomation.validation.Check;
import br.com.restautomation.validation.FieldCheck;
import br.com.restautomation.validation.ResponseCheck;

public class ControllerAdapter {

	public String adaptPreConditions(List<String> preConditions) {
		String preCond = "";
		if (preConditions == null) {
			return preCond;
		}
		for (String cond : preConditions) {
			preCond += cond + "\n";
		}
		return preCond;
	}

	public Script adaptScript(Request request) {

		Script script = new Script();
		script.setId(request.queryParams("id"));
		if ("".equals(request.queryParams("preConditions")) || request.queryParams("preConditions") == null) {
			script.setPreConditions(null);
		} else {
			script.setPreConditions(Arrays.asList(request.queryParams("preConditions").split("\r\n")));
		}
		if ("".equals(request.queryParams("tags")) || request.queryParams("tags") == null) {
			script.setTags(null);
		} else {
			script.setTags(Arrays.asList(request.queryParams("tags").split(" ")));
		}

		script.setDescription(request.queryParams("description"));

		br.com.restautomation.script.Request req = new br.com.restautomation.script.Request();
		req.setBody(request.queryParams("request-body"));
		req.setHeaders(adaptEntries("header-key-", "header-val-", request));
		req.setMethod(request.queryParams("request-method"));
		req.setUrl(request.queryParams("request-url"));
		req.setParams(adaptEntries("param-key-", "param-val-", request));
		req.setAuthorization(adaptAutorization(request));
		script.setRequest(req);

		ResponseCheck responseCheck = new ResponseCheck();
		responseCheck.setHttpCode(Integer.valueOf(request.queryParams("response-http-code")));
		responseCheck.setFieldChecks(adaptChecks(request));
		script.setResponseCheck(responseCheck);

		return script;
	}

	private Authorization adaptAutorization(Request request) {

		if (AuthorizationType.OAUTH1.name().equals(request.queryParams("authorization-type"))) {
			return adaptAuthOAuth1(request);
		} else if (AuthorizationType.BASIC.name().equals(request.queryParams("authorization-type"))) {
			return adaptAuthBasic(request);
		}
		return null;
	}

	private Authorization adaptAuthBasic(Request request) {

		AuthorizationBasic basic = new AuthorizationBasic();
		basic.setUsername(request.queryParams("authorization-username"));
		basic.setPassword(request.queryParams("authorization-password"));

		return basic;
	}

	private Authorization adaptAuthOAuth1(Request request) {

		AuthorizationOAuth1 oAuth1 = new AuthorizationOAuth1();
		oAuth1.setConsumerKey(request.queryParams("authorization-consumer-key"));
		oAuth1.setNonce(request.queryParams("authorization-nonce"));
		oAuth1.setRealm(request.queryParams("authorization-realm"));
		oAuth1.setSignatureMethod(SignatureMethod.valueOf(request.queryParams("authorization-signature-method")));

		return oAuth1;
	}

	private Map<String, FieldCheck> adaptChecks(Request request) {

		Map<String, FieldCheck> map = new HashMap<>();
		for (int i = 0; i < request.contentLength(); i++) {
			String key = request.queryParams("field-key-" + i);
			if (key != null) {
				FieldCheck fieldCheck = new FieldCheck();
				fieldCheck.setCheck(Check.valueOf(request.queryParams("check-" + i)));
				fieldCheck.setExpectedValue(request.queryParams("check-value-" + i));
				map.put(key, fieldCheck);
			}
		}

		return map;
	}

	public Environment adaptEnvironment(Request request) {

		Environment environment = new Environment();
		environment.setId(request.queryParams("id"));
		environment.setEntries(adaptEntries("entry-key-", "entry-val-", request));

		return environment;
	}

	private Map<String, String> adaptEntries(String keyPrefix, String valPrefix, Request request) {

		Map<String, String> map = new HashMap<>();
		for (int i = 0; i < request.contentLength(); i++) {
			String key = request.queryParams(keyPrefix + i);
			if (key != null) {
				map.put(key, request.queryParams(valPrefix + i));
			}
		}
		return map;
	}
}

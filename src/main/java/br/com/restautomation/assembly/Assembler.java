package br.com.restautomation.assembly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bson.Document;

import br.com.restautomation.Environment;
import br.com.restautomation.execution.Execution;
import br.com.restautomation.infra.AdapterException;
import br.com.restautomation.infra.AutomationException;
import br.com.restautomation.infra.HelperAdapter;
import br.com.restautomation.script.Authorization;
import br.com.restautomation.script.AuthorizationBasic;
import br.com.restautomation.script.AuthorizationNone;
import br.com.restautomation.script.AuthorizationOAuth1;
import br.com.restautomation.script.AuthorizationType;
import br.com.restautomation.script.Request;

public class Assembler {

	private final HelperAdapter helperAdapter = new HelperAdapter();

	private final AuthorizationAssembler authAssembler = new AuthorizationAssembler();

	private final AttributeExtractor attributeExtractor = new AttributeExtractor();

	public Request assembleRequest(final Request requestIn, Environment env, List<Execution> preExecutions) throws AutomationException {

		Request requestOut = new Request();
		requestOut.setUrl(assemble(requestIn.getUrl(), env, preExecutions));
		requestOut.setMethod(requestIn.getMethod());

		Map<String, String> headers = assemble(requestIn.getHeaders(), env, preExecutions);
		Authorization auth = assembleAuthorization(requestIn.getAuthorization(), env, preExecutions);
		headers.putAll(authAssembler.assembleHeader(auth));
		requestOut.setHeaders(headers);

		requestOut.setParams(assemble(requestIn.getParams(), env, preExecutions));
		requestOut.setBody(assemble(requestIn.getBody(), env, preExecutions));

		return requestOut;
	}

	private Authorization assembleAuthorization(Authorization auth, Environment env, List<Execution> preExecutions)
			throws AutomationException {

		if (AuthorizationType.OAUTH1.equals(auth.getType()) && auth instanceof AuthorizationOAuth1) {
			return assembleAuthorizationOAuth((AuthorizationOAuth1) auth, env, preExecutions);
		} else if (AuthorizationType.BASIC.equals(auth.getType()) && auth instanceof AuthorizationBasic) {
			return assembleAuthorizationBasic((AuthorizationBasic) auth, env, preExecutions);
		} else if (AuthorizationType.NONE.equals(auth.getType())) {
			return new AuthorizationNone();
		}
		throw new AdapterException("Assembler " + auth.getType().name() + " not implemented");
	}

	private Authorization assembleAuthorizationBasic(AuthorizationBasic auth, Environment env, List<Execution> preExecutions)
			throws AutomationException {

		AuthorizationBasic basic = new AuthorizationBasic();
		basic.setUsername(assemble(auth.getUsername(), env, preExecutions));
		basic.setPassword(assemble(auth.getPassword(), env, preExecutions));

		return basic;
	}

	private Authorization assembleAuthorizationOAuth(AuthorizationOAuth1 auth, Environment env, List<Execution> preExecutions)
			throws AutomationException {

		AuthorizationOAuth1 oAuth1 = new AuthorizationOAuth1();
		oAuth1.setConsumerKey(assemble(auth.getConsumerKey(), env, preExecutions));
		oAuth1.setNonce(assemble(auth.getNonce(), env, preExecutions));
		oAuth1.setRealm(assemble(auth.getRealm(), env, preExecutions));
		oAuth1.setSignatureMethod(auth.getSignatureMethod());

		return oAuth1;
	}

	private Map<String, String> assemble(Map<String, String> entriesIn, Environment env, List<Execution> preExecutions)
			throws AutomationException {

		if (entriesIn == null) {
			return null;
		}

		Map<String, String> entriesOut = new HashMap<>();
		for (Entry<String, String> entry : entriesIn.entrySet()) {
			entriesOut.put(entry.getKey(), assemble(entry.getValue(), env, preExecutions));
		}
		return entriesOut;
	}

	protected String assemble(String word, Environment env, List<Execution> preExecutions) throws AutomationException {

		List<Mark> marks = convertToMarks(word);

		String ret = "";
		for (Mark mark : marks) {
			if (mark.getValue() != null) {
				ret += mark.getValue();
			} else {
				String value = env.getValue(mark.getParam());
				if (value == "") {
					value = generateValue(mark.getParam());
				}
				if (value == "") {
					value = getFromPreExecutions(preExecutions, mark.getParam());
				}
				if (value == "") {
					throw new AutomationException("O parâmetro " + mark.getParam() + " não existe");
				}
				ret += value;
			}
		}

		return ret;
	}

	private String getFromPreExecutions(List<Execution> preExecutions, String param) {

		if (preExecutions == null) {
			return "";
		}

		for (Execution execution : preExecutions) {
			String value = getFromPreExecution(execution, param);
			if (!value.equals("")) {
				return value;
			}
		}

		return "";
	}

	private String getFromPreExecution(Execution execution, String param) {

		Document doc = helperAdapter.adaptBody(execution.getResponse().getBody());
		String value = attributeExtractor.getAsString(doc, param);
		if (value != null) {
			return value;
		}

		return getFromPreExecutions(execution.getPreExecutions(), param);
	}

	private String generateValue(String param) {

		try {
			Generator generator = Generator.valueOf(param);
			return generator.generate();
		} catch (IllegalArgumentException e) {
			return "";
		}
	}

	private List<Mark> convertToMarks(String word) {

		List<Mark> marks = new ArrayList<Mark>();
		Mark lastMark = new Mark();
		do {
			lastMark = getNextMark(word, lastMark.getPos());
			marks.add(lastMark);
		} while (lastMark.getPos() < word.length());

		return marks;
	}

	private Mark getNextMark(String word, int fromIndex) {

		int indexOf = word.indexOf("{{", fromIndex);
		Mark mark = new Mark();
		if (indexOf == -1) {
			mark.setValue(word.substring(fromIndex));
			mark.setPos(word.length());
			return mark;
		}

		if (fromIndex < indexOf) {
			mark.setValue(word.substring(fromIndex, indexOf));
			mark.setPos(indexOf);
			return mark;
		}

		int indexEnd = word.indexOf("}}", indexOf);
		if (indexEnd == -1) {
			mark.setValue(word.substring(fromIndex));
			mark.setPos(word.length());
			return mark;
		}

		mark.setParam(word.substring(indexOf + 2, indexEnd));
		mark.setPos(indexEnd + 2);
		return mark;
	}
}

package br.com.restautomation.script;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.bson.Document;

import br.com.restautomation.infra.HelperAdapter;
import br.com.restautomation.validation.Check;
import br.com.restautomation.validation.FieldCheck;
import br.com.restautomation.validation.ResponseCheck;

public class ScriptAdapter {

	private final RequestAdapter requestAdapter = new RequestAdapter();

	private final HelperAdapter helper = new HelperAdapter();

	public List<Script> adapt(List<Document> docs) {

		if (docs == null) {
			return null;
		}
		List<Script> scripts = new ArrayList<>();
		for (Document doc : docs) {
			scripts.add(adapt(doc));
		}
		return scripts;
	}

	public Script adapt(Document doc) {

		if (doc == null) {
			return null;
		}

		Script script = new Script();
		script.setId(doc.getString("_id"));
		String description = doc.getString("description");
		script.setTags(helper.adaptStrings((ArrayList) doc.get("tags")));
		script.setDescription(description != null ? description : StringUtils.EMPTY);
		script.setRequest(requestAdapter.adapt(doc.get("request")));
		script.setResponseCheck(adaptResponseCheck((Document) doc.get("responseCheck")));
		script.setPreConditions(helper.adaptStrings((ArrayList) doc.get("preConditions")));
		return script;
	}

	private ResponseCheck adaptResponseCheck(Document doc) {

		if (doc == null) {
			return null;
		}
		ResponseCheck responseCheck = new ResponseCheck();
		responseCheck.setHttpCode(doc.getInteger("httpCode"));
		responseCheck.setFieldChecks(adaptFieldChecks((Document) doc.get("fieldChecks")));

		return responseCheck;
	}

	public Map<String, FieldCheck> adaptFieldChecks(Document doc) {

		if (doc == null) {
			return null;
		}
		Map<String, FieldCheck> entries = new HashMap<String, FieldCheck>();
		for (Entry<String, Object> objEntry : doc.entrySet()) {
			entries.put(objEntry.getKey(), adaptFieldCheck((Document) objEntry.getValue()));
		}
		return entries;
	}

	private FieldCheck adaptFieldCheck(Document doc) {

		if (doc == null) {
			return null;
		}
		FieldCheck check = new FieldCheck();
		check.setCheck(Check.valueOf(doc.getString("check")));
		check.setExpectedValue(doc.getString("expectedValue"));
		return check;
	}

	public Document adapt(Script script) {

		Document doc = new Document();
		doc.append("_id", script.getId());
		doc.append("description", script.getDescription());
		doc.append("tags", script.getTags());
		doc.append("request", requestAdapter.adapt(script.getRequest()));
		doc.append("responseCheck", adaptResponseCheck(script.getResponseCheck()));
		doc.append("preConditions", script.getPreConditions());

		return doc;
	}

	private Document adaptResponseCheck(ResponseCheck responseCheck) {

		if (responseCheck == null) {
			return null;
		}

		Document doc = new Document();
		doc.append("httpCode", responseCheck.getHttpCode());
		Document fieldChecks = adaptFieldChecks(responseCheck.getFieldChecks());
		if (fieldChecks != null) {
			doc.append("fieldChecks", fieldChecks);
		}

		return doc;
	}

	private Document adaptFieldChecks(Map<String, FieldCheck> fieldChecks) {

		if (fieldChecks == null) {
			return null;
		}
		Document doc = new Document();
		for (Entry<String, FieldCheck> entry : fieldChecks.entrySet()) {
			doc.append(entry.getKey(), adaptFieldCheck(entry.getValue()));
		}
		return doc;
	}

	private Document adaptFieldCheck(FieldCheck fieldCheck) {

		if (fieldCheck == null) {
			return null;
		}
		Document doc = new Document();
		doc.append("check", fieldCheck.getCheck().name());
		doc.append("expectedValue", fieldCheck.getExpectedValue());

		return doc;
	}
}

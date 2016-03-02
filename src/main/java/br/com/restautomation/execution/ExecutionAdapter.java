package br.com.restautomation.execution;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import br.com.restautomation.infra.AdapterException;
import br.com.restautomation.infra.HelperAdapter;
import br.com.restautomation.script.RequestAdapter;
import br.com.restautomation.script.Response;
import br.com.restautomation.validation.Validation;
import br.com.restautomation.validation.ValidationStatus;

public class ExecutionAdapter {

	private final RequestAdapter requestAdapter = new RequestAdapter();

	private final HelperAdapter helperAdapter = new HelperAdapter();

	public Document adapt(Execution execution) {

		Document doc = new Document();

		doc.append("date", execution.getDate());
		doc.append("scriptId", execution.getScriptId());
		if (execution.getMasterId() != null) {
			doc.append("masterId", execution.getMasterId());
		}
		doc.append("request", requestAdapter.adapt(execution.getRequest()));
		doc.append("response", adaptResponse(execution.getResponse()));
		doc.append("userName", execution.getUserName());
		doc.append("validations", adaptValidations(execution.getValidations()));
		doc.append("preExecutions", adapt(execution.getPreExecutions()));
		doc.append("_id", execution.getId());

		return doc;
	}

	public Execution adapt(Document doc) {

		Execution execution = new Execution();
		execution.setDate(doc.getDate("date"));
		execution.setScriptId(doc.getString("scriptId"));
		execution.setMasterId(doc.getString("masterId"));
		execution.setRequest(requestAdapter.adapt(doc.get("request")));
		execution.setResponse(adaptResponse(doc.get("response")));
		execution.setUserName(doc.getString("userName"));
		execution.setValidations(adaptValidations(doc.get("validations")));
		execution.setPreExecutions(adapt(doc.get("preExecutions")));
		execution.setId(doc.getString("_id"));

		return execution;
	}

	public List<Document> adapt(List<Execution> executions) {

		if (executions == null) {
			return null;
		}

		List<Document> docs = new ArrayList<>();
		for (Execution execution : executions) {
			Document doc = adapt(execution);
			docs.add(doc);
		}
		return docs;
	}

	private List<Execution> adapt(Object objs) {

		List<Document> documents = helperAdapter.adaptDocuments(objs);
		return adaptDocs(documents);
	}

	public List<Execution> adaptDocs(List<Document> docs) {

		if (docs == null) {
			return null;
		}

		List<Execution> executions = new ArrayList<>();
		for (Document doc : docs) {
			executions.add(adapt(doc));
		}

		return executions;
	}

	private List<Document> adaptValidations(List<Validation> validations) {

		List<Document> list = new ArrayList<>();

		for (Validation validation : validations) {
			list.add(adaptValidation(validation));
		}

		return list;
	}

	private List<Validation> adaptValidations(Object objs) {

		List<Document> docs = helperAdapter.adaptDocuments(objs);

		List<Validation> list = new ArrayList<>();

		for (Document doc : docs) {
			list.add(adaptValidation(doc));
		}

		return list;
	}

	private Document adaptValidation(Validation validation) {

		Document doc = new Document();

		doc.append("status", validation.getStatus().name());
		doc.append("test", validation.getTest());
		doc.append("error", validation.getError());

		return doc;
	}

	private Validation adaptValidation(Object obj) {

		Document doc = helperAdapter.adaptDocument(obj);
		if (doc == null) {
			return null;
		}

		Validation validation = new Validation();
		validation.setStatus(ValidationStatus.valueOf(doc.getString("status")));
		validation.setTest(doc.getString("test"));
		validation.setError(doc.getString("error"));

		return validation;
	}

	private Document adaptResponse(Response response) {

		if (response == null) {
			return null;
		}
		Document doc = new Document();

		doc.append("httpCode", response.getHttpCode());

		try {
			doc.append("body", helperAdapter.adaptBody(response.getBody()));
		} catch (AdapterException e) {
			doc.append("body", response.getBody());
		}

		return doc;
	}

	private Response adaptResponse(Object obj) {

		Document doc = helperAdapter.adaptDocument(obj);
		if (doc == null) {
			return null;
		}

		Response response = new Response();

		Object objBody = doc.get("body");
		if (objBody instanceof Document) {
			response.setBody(helperAdapter.adaptBody(objBody));
		} else if (objBody instanceof String) {
			response.setBody((String) objBody);
		} else if (objBody == null) {
			response.setBody(null);
		} else {
			throw new AdapterException("Can't adapt response body");
		}

		response.setHttpCode(doc.getInteger("httpCode"));

		return response;
	}
}

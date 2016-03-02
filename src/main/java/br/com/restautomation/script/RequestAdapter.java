package br.com.restautomation.script;

import org.bson.Document;

import br.com.restautomation.infra.HelperAdapter;

public class RequestAdapter {

	private final HelperAdapter helper = new HelperAdapter();

	private final AuthorizationAdapter authAdapter = new AuthorizationAdapter();

	public Document adapt(Request request) {

		if (request == null) {
			return null;
		}

		Document doc = new Document();

		doc.append("url", request.getUrl());
		doc.append("method", request.getMethod());
		doc.append("body", helper.adaptBody(request.getBody()));
		doc.append("headers", helper.adaptMap(request.getHeaders()));
		doc.append("params", helper.adaptMap(request.getParams()));
		doc.append("authorization", authAdapter.adapt(request.getAuthorization()));

		return doc;
	}

	public Request adapt(Object obj) {

		if (obj == null) {
			return null;
		}

		Document doc = helper.adaptDocument(obj);

		Request request = new Request();
		request.setUrl(doc.getString("url"));
		request.setMethod(doc.getString("method"));
		request.setBody(helper.adaptBody(doc.get("body")));
		request.setHeaders(helper.adaptMap(doc.get("headers")));
		request.setParams(helper.adaptMap(doc.get("params")));

		Object objAuth = doc.get("authorization");
		if (objAuth != null) {
			request.setAuthorization(authAdapter.adapt(helper.adaptDocument(objAuth)));
		}

		return request;
	}
}

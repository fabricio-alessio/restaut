package br.com.restautomation.controller;

import java.io.IOException;
import java.io.Writer;

import spark.Request;
import spark.Response;
import br.com.restautomation.script.AuthorizationBasic;
import br.com.restautomation.script.AuthorizationOAuth1;
import br.com.restautomation.script.AuthorizationType;
import br.com.restautomation.script.Script;
import br.com.restautomation.script.ScriptService;
import br.com.restautomation.script.SignatureMethod;
import br.com.restautomation.validation.Check;

import com.mongodb.client.MongoDatabase;

import freemarker.template.SimpleHash;
import freemarker.template.TemplateException;

public class ScriptDuplicateRoute extends LoggedRoute {

	private final ScriptService scriptService;

	private final ControllerAdapter adapter = new ControllerAdapter();

	public ScriptDuplicateRoute(MongoDatabase db) throws IOException {
		super("/scripts/duplicate/:id", "script_edit.ftl", db);
		scriptService = new ScriptService(db);
	}

	@Override
	public void doHandleLogged(Request request, Response response, Writer writer) throws IOException, TemplateException {

		String scriptId = request.params(":id");

		Script script = scriptService.getById(scriptId);
		script.setId(scriptId + "Duplicated");

		SimpleHash root = new SimpleHash();
		root.put("new", true);
		root.put("script", script);
		root.put("scriptIds", scriptService.getAllIdsWithoutPreConditions(script));
		root.put("preConditions", adapter.adaptPreConditions(script.getPreConditions()));
		root.put("authTypeSelected", script.getRequest().getAuthorization().getType());
		root.put("authorizationTypes", AuthorizationType.values());
		root.put("signatureMethods", SignatureMethod.values());
		root.put("checks", Check.values());

		if (AuthorizationType.OAUTH1.equals(script.getRequest().getAuthorization().getType())) {
			root.put("authOAuth1", (AuthorizationOAuth1) script.getRequest().getAuthorization());
		} else if (AuthorizationType.BASIC.equals(script.getRequest().getAuthorization().getType())) {
			root.put("authBasic", (AuthorizationBasic) script.getRequest().getAuthorization());
		}

		template.process(root, writer);
	}
}

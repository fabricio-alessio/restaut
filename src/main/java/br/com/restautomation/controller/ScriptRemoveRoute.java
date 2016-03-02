package br.com.restautomation.controller;

import java.io.IOException;
import java.io.Writer;

import spark.Request;
import spark.Response;
import br.com.restautomation.infra.AutomationException;
import br.com.restautomation.script.ScriptService;

import com.mongodb.client.MongoDatabase;

import freemarker.template.TemplateException;

public class ScriptRemoveRoute extends LoggedRoute {

	private final ScriptService scriptService;

	public ScriptRemoveRoute(MongoDatabase db) throws IOException {
		super("/scripts/remove", "", db);
		scriptService = new ScriptService(db);
	}

	@Override
	public void doHandleLogged(Request request, Response response, Writer writer) throws IOException, TemplateException {

		String scriptId = request.queryParams("id");
		try {
			scriptService.remove(scriptId);
			response.redirect("/");
		} catch (AutomationException e) {
			e.printStackTrace();
			response.redirect("/internal_error");
		}
	}
}

package br.com.restautomation.controller;

import java.io.IOException;
import java.io.Writer;

import spark.Request;
import spark.Response;
import br.com.restautomation.EnvironmentService;
import br.com.restautomation.infra.AutomationException;

import com.mongodb.client.MongoDatabase;

import freemarker.template.TemplateException;

public class EnvironmentRemoveRoute extends LoggedRoute {

	final private EnvironmentService environmentService;

	public EnvironmentRemoveRoute(MongoDatabase db) throws IOException {
		super("/environments/remove", "", db);
		environmentService = new EnvironmentService(db);
	}

	@Override
	public void doHandleLogged(Request request, Response response, Writer writer) throws IOException, TemplateException {

		String envId = request.queryParams("id");
		try {
			environmentService.remove(envId);
			response.redirect("/");
		} catch (AutomationException e) {
			e.printStackTrace();
			response.redirect("/internal_error");
		}
	}
}

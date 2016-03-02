package br.com.restautomation.controller;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import spark.Request;
import spark.Response;
import br.com.restautomation.Environment;
import br.com.restautomation.EnvironmentService;

import com.mongodb.client.MongoDatabase;

import freemarker.template.SimpleHash;
import freemarker.template.TemplateException;

public class ExecutionNewRoute extends LoggedRoute {

	private final EnvironmentService environmentService;

	public ExecutionNewRoute(MongoDatabase db) throws IOException {
		super("/executions/new/:scriptId", "execution_new.ftl", db);
		environmentService = new EnvironmentService(db);
	}

	@Override
	public void doHandleLogged(Request request, Response response, Writer writer) throws IOException, TemplateException {

		List<Environment> environments = environmentService.getAll();
		String scriptId = request.params("scriptId");

		SimpleHash root = new SimpleHash();
		root.put("environments", environments);
		root.put("scriptId", scriptId);

		template.process(root, writer);
	}
}

package br.com.restautomation.controller;

import java.io.IOException;
import java.io.Writer;

import spark.Request;
import spark.Response;
import br.com.restautomation.Environment;
import br.com.restautomation.EnvironmentService;

import com.mongodb.client.MongoDatabase;

import freemarker.template.SimpleHash;
import freemarker.template.TemplateException;

public class EnvironmentDuplicateRoute extends LoggedRoute {

	final private EnvironmentService environmentService;

	public EnvironmentDuplicateRoute(MongoDatabase db) throws IOException {
		super("/environments/duplicate/:id", "environment_edit.ftl", db);
		environmentService = new EnvironmentService(db);
	}

	@Override
	public void doHandleLogged(Request request, Response response, Writer writer) throws IOException, TemplateException {

		String envId = request.params(":id");

		Environment environment = environmentService.getById(envId);
		environment.setId(envId + "Duplicated");

		SimpleHash root = new SimpleHash();
		root.put("new", true);
		root.put("environment", environment);

		template.process(root, writer);
	}
}

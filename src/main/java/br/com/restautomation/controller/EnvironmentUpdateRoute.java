package br.com.restautomation.controller;

import java.io.IOException;
import java.io.Writer;

import spark.Request;
import spark.Response;
import br.com.restautomation.Environment;
import br.com.restautomation.EnvironmentService;
import br.com.restautomation.infra.AdapterException;
import br.com.restautomation.infra.AutomationException;

import com.mongodb.client.MongoDatabase;

import freemarker.template.SimpleHash;
import freemarker.template.TemplateException;

public class EnvironmentUpdateRoute extends LoggedRoute {

	final private EnvironmentService environmentService;

	private final ControllerAdapter adapter = new ControllerAdapter();

	public EnvironmentUpdateRoute(MongoDatabase db) throws IOException {
		super("/environments/update", "environment_edit.ftl", db);
		environmentService = new EnvironmentService(db);
	}

	@Override
	public void doHandleLogged(Request request, Response response, Writer writer) throws IOException, TemplateException {

		boolean newEnv = "true".equals(request.queryParams("new"));
		Environment environment = adapter.adaptEnvironment(request);

		SimpleHash root = new SimpleHash();
		try {
			if (newEnv) {
				root.put("new", true);
				environmentService.insert(environment);
			} else {
				environmentService.update(environment);
			}
			root.put("message", "Environment saved");
		} catch (AutomationException | AdapterException e) {
			root.put("message", e.getMessage());
			root.put("error", true);
		}

		root.put("environment", environment);

		template.process(root, writer);
	}
}

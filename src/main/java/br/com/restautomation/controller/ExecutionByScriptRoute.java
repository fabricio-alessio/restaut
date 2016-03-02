package br.com.restautomation.controller;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import spark.Request;
import spark.Response;
import br.com.restautomation.execution.Execution;
import br.com.restautomation.execution.ExecutionService;

import com.mongodb.client.MongoDatabase;

import freemarker.template.SimpleHash;
import freemarker.template.TemplateException;

public class ExecutionByScriptRoute extends LoggedRoute {

	private final ExecutionService executionService;

	public ExecutionByScriptRoute(MongoDatabase db) throws IOException {
		super("/executions/:scriptId", "execution_list.ftl", db);
		executionService = new ExecutionService(db);
	}

	@Override
	public void doHandleLogged(Request request, Response response, Writer writer) throws IOException, TemplateException {

		String scriptId = request.params(":scriptId");

		List<Execution> executions = executionService.getByScriptId(scriptId);
		for (Execution execution : executions) {
			execution.calculateLevels();
		}

		SimpleHash root = new SimpleHash();
		root.put("executions", executions);
		root.put("title", "script id " + scriptId);

		template.process(root, writer);
	}
}

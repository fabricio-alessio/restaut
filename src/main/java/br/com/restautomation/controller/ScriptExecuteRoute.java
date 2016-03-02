package br.com.restautomation.controller;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import spark.Request;
import spark.Response;
import br.com.restautomation.execution.Execution;
import br.com.restautomation.execution.Executor;

import com.mongodb.client.MongoDatabase;

import freemarker.template.SimpleHash;
import freemarker.template.TemplateException;

public class ScriptExecuteRoute extends LoggedRoute {

	final private Executor executor;

	public ScriptExecuteRoute(MongoDatabase db) throws IOException {
		super("/scripts/execute/:id", "_execution_list.ftl", db);
		executor = new Executor(db);
	}

	@Override
	public void doHandleLogged(Request request, Response response, Writer writer) throws IOException, TemplateException {

		String scriptId = request.params(":id");
		String masterId = request.queryParams("masterId");
		String environmentId = request.queryParams("environmentId");

		Execution execution = executor.executeScriptId(scriptId, masterId, environmentId, "fabricio");
		execution.calculateLevels();
		List<Execution> executions = new ArrayList<>();
		executions.add(execution);

		SimpleHash root = new SimpleHash();
		root.put("executions", executions);

		template.process(root, writer);
	}
}

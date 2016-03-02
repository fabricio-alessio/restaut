package br.com.restautomation.controller;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import spark.Request;
import spark.Response;
import br.com.restautomation.Environment;
import br.com.restautomation.EnvironmentService;
import br.com.restautomation.execution.MasterExecution;
import br.com.restautomation.execution.MasterExecutionService;

import com.mongodb.client.MongoDatabase;

import freemarker.template.SimpleHash;
import freemarker.template.TemplateException;

public class MasterExecutionNewRoute extends LoggedRoute {

	private final MasterExecutionService masterExecutionService;

	private final EnvironmentService environmentService;

	public MasterExecutionNewRoute(MongoDatabase db) throws IOException {
		super("/masterExecutions/new", "master_execution_new.ftl", db);
		masterExecutionService = new MasterExecutionService(db);
		environmentService = new EnvironmentService(db);
	}

	@Override
	public void doHandleLogged(Request request, Response response, Writer writer) throws IOException, TemplateException {

		MasterExecution execution = masterExecutionService.createNewMaster();
		List<Environment> environments = environmentService.getAll();

		SimpleHash root = new SimpleHash();
		root.put("masterExecution", execution);
		root.put("environments", environments);

		template.process(root, writer);
	}
}

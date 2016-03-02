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
import br.com.restautomation.infra.CookieHelper;
import br.com.restautomation.script.Script;
import br.com.restautomation.script.ScriptService;
import br.com.restautomation.user.Session;
import br.com.restautomation.user.SessionService;

import com.mongodb.client.MongoDatabase;

import freemarker.template.SimpleHash;
import freemarker.template.TemplateException;

public class DashboardRoute extends LoggedRoute {

	private final ScriptService scriptService;

	private final MasterExecutionService masterExecutionService;

	private final EnvironmentService environmentService;

	private final SessionService sessionService;

	public DashboardRoute(MongoDatabase db) throws IOException {
		super("/", "dashboard.ftl", db);

		scriptService = new ScriptService(db);
		masterExecutionService = new MasterExecutionService(db);
		environmentService = new EnvironmentService(db);
		sessionService = new SessionService(db);
	}

	@Override
	public void doHandleLogged(Request request, Response response, Writer writer) throws IOException, TemplateException {

		List<Script> scripts = scriptService.getAll();
		List<MasterExecution> masterExecutions = masterExecutionService.getAll();
		List<Environment> environments = environmentService.getAll();

		String sessionId = CookieHelper.getSessionCookieValue(request);
		Session session = sessionService.getById(sessionId);

		SimpleHash root = new SimpleHash();
		root.put("scripts", scripts);
		root.put("masterExecutions", masterExecutions);
		root.put("environments", environments);
		root.put("username", session.getUsername());

		template.process(root, writer);
	}
}
